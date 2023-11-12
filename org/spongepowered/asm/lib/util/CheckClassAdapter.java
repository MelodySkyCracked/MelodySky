/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;
import org.spongepowered.asm.lib.util.CheckAnnotationAdapter;
import org.spongepowered.asm.lib.util.CheckFieldAdapter;
import org.spongepowered.asm.lib.util.CheckMethodAdapter;
import org.spongepowered.asm.lib.util.Textifier;
import org.spongepowered.asm.lib.util.TraceMethodVisitor;

public class CheckClassAdapter
extends ClassVisitor {
    private int version;
    private boolean start;
    private boolean source;
    private boolean outer;
    private boolean end;
    private Map labels = new HashMap();
    private boolean checkDataFlow;

    public static void main(String[] stringArray) throws Exception {
        if (stringArray.length != 1) {
            System.err.println("Verifies the given class.");
            System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
            return;
        }
        ClassReader classReader = stringArray[0].endsWith(".class") ? new ClassReader(new FileInputStream(stringArray[0])) : new ClassReader(stringArray[0]);
        CheckClassAdapter.verify(classReader, false, new PrintWriter(System.err));
    }

    public static void verify(ClassReader classReader, ClassLoader classLoader, boolean bl, PrintWriter printWriter) {
        ClassNode classNode = new ClassNode();
        classReader.accept(new CheckClassAdapter(classNode, false), 2);
        Type type = classNode.superName == null ? null : Type.getObjectType(classNode.superName);
        List list = classNode.methods;
        ArrayList<Type> arrayList = new ArrayList<Type>();
        Iterator iterator = classNode.interfaces.iterator();
        while (iterator.hasNext()) {
            arrayList.add(Type.getObjectType((String)iterator.next()));
        }
        for (int i = 0; i < list.size(); ++i) {
            MethodNode methodNode = (MethodNode)list.get(i);
            SimpleVerifier simpleVerifier = new SimpleVerifier(Type.getObjectType(classNode.name), type, arrayList, (classNode.access & 0x200) != 0);
            Analyzer analyzer = new Analyzer(simpleVerifier);
            if (classLoader != null) {
                simpleVerifier.setClassLoader(classLoader);
            }
            try {
                analyzer.analyze(classNode.name, methodNode);
                if (!bl) {
                    continue;
                }
            }
            catch (Exception exception) {
                exception.printStackTrace(printWriter);
            }
            CheckClassAdapter.printAnalyzerResult(methodNode, analyzer, printWriter);
        }
        printWriter.flush();
    }

    public static void verify(ClassReader classReader, boolean bl, PrintWriter printWriter) {
        CheckClassAdapter.verify(classReader, null, bl, printWriter);
    }

    static void printAnalyzerResult(MethodNode methodNode, Analyzer analyzer, PrintWriter printWriter) {
        int n;
        Frame[] frameArray = analyzer.getFrames();
        Textifier textifier = new Textifier();
        TraceMethodVisitor traceMethodVisitor = new TraceMethodVisitor(textifier);
        printWriter.println(methodNode.name + methodNode.desc);
        for (n = 0; n < methodNode.instructions.size(); ++n) {
            methodNode.instructions.get(n).accept(traceMethodVisitor);
            StringBuilder stringBuilder = new StringBuilder();
            Frame frame = frameArray[n];
            if (frame == null) {
                stringBuilder.append('?');
            } else {
                int n2;
                for (n2 = 0; n2 < frame.getLocals(); ++n2) {
                    stringBuilder.append(CheckClassAdapter.getShortName(((BasicValue)frame.getLocal(n2)).toString())).append(' ');
                }
                stringBuilder.append(" : ");
                for (n2 = 0; n2 < frame.getStackSize(); ++n2) {
                    stringBuilder.append(CheckClassAdapter.getShortName(((BasicValue)frame.getStack(n2)).toString())).append(' ');
                }
            }
            while (stringBuilder.length() < methodNode.maxStack + methodNode.maxLocals + 1) {
                stringBuilder.append(' ');
            }
            printWriter.print(Integer.toString(n + 100000).substring(1));
            printWriter.print(" " + stringBuilder + " : " + textifier.text.get(textifier.text.size() - 1));
        }
        for (n = 0; n < methodNode.tryCatchBlocks.size(); ++n) {
            ((TryCatchBlockNode)methodNode.tryCatchBlocks.get(n)).accept(traceMethodVisitor);
            printWriter.print(" " + textifier.text.get(textifier.text.size() - 1));
        }
        printWriter.println();
    }

    private static String getShortName(String string) {
        int n = string.lastIndexOf(47);
        int n2 = string.length();
        if (string.charAt(n2 - 1) == ';') {
            --n2;
        }
        return n == -1 ? string : string.substring(n + 1, n2);
    }

    public CheckClassAdapter(ClassVisitor classVisitor) {
        this(classVisitor, true);
    }

    public CheckClassAdapter(ClassVisitor classVisitor, boolean bl) {
        this(327680, classVisitor, bl);
        if (this.getClass() != CheckClassAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckClassAdapter(int n, ClassVisitor classVisitor, boolean bl) {
        super(n, classVisitor);
        this.checkDataFlow = bl;
    }

    public void visit(int n, int n2, String string, String string2, String string3, String[] stringArray) {
        if (this.start) {
            throw new IllegalStateException("visit must be called only once");
        }
        this.start = true;
        this.checkState();
        CheckClassAdapter.checkAccess(n2, 423473);
        if (string == null || !string.endsWith("package-info")) {
            CheckMethodAdapter.checkInternalName(string, "class name");
        }
        if ("java/lang/Object".equals(string)) {
            if (string3 != null) {
                throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
            }
        } else {
            CheckMethodAdapter.checkInternalName(string3, "super class name");
        }
        if (string2 != null) {
            CheckClassAdapter.checkClassSignature(string2);
        }
        if ((n2 & 0x200) != 0 && !"java/lang/Object".equals(string3)) {
            throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
        }
        if (stringArray != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                CheckMethodAdapter.checkInternalName(stringArray[i], "interface name at index " + i);
            }
        }
        this.version = n;
        super.visit(n, n2, string, string2, string3, stringArray);
    }

    public void visitSource(String string, String string2) {
        this.checkState();
        if (this.source) {
            throw new IllegalStateException("visitSource can be called only once.");
        }
        this.source = true;
        super.visitSource(string, string2);
    }

    public void visitOuterClass(String string, String string2, String string3) {
        this.checkState();
        if (this.outer) {
            throw new IllegalStateException("visitOuterClass can be called only once.");
        }
        this.outer = true;
        if (string == null) {
            throw new IllegalArgumentException("Illegal outer class owner");
        }
        if (string3 != null) {
            CheckMethodAdapter.checkMethodDesc(string3);
        }
        super.visitOuterClass(string, string2, string3);
    }

    public void visitInnerClass(String string, String string2, String string3, int n) {
        this.checkState();
        CheckMethodAdapter.checkInternalName(string, "class name");
        if (string2 != null) {
            CheckMethodAdapter.checkInternalName(string2, "outer class name");
        }
        if (string3 != null) {
            int n2;
            for (n2 = 0; n2 < string3.length() && Character.isDigit(string3.charAt(n2)); ++n2) {
            }
            if (n2 == 0 || n2 < string3.length()) {
                CheckMethodAdapter.checkIdentifier(string3, n2, -1, "inner class name");
            }
        }
        CheckClassAdapter.checkAccess(n, 30239);
        super.visitInnerClass(string, string2, string3, n);
    }

    public FieldVisitor visitField(int n, String string, String string2, String string3, Object object) {
        this.checkState();
        CheckClassAdapter.checkAccess(n, 413919);
        CheckMethodAdapter.checkUnqualifiedName(this.version, string, "field name");
        CheckMethodAdapter.checkDesc(string2, false);
        if (string3 != null) {
            CheckClassAdapter.checkFieldSignature(string3);
        }
        if (object != null) {
            CheckMethodAdapter.checkConstant(object);
        }
        FieldVisitor fieldVisitor = super.visitField(n, string, string2, string3, object);
        return new CheckFieldAdapter(fieldVisitor);
    }

    public MethodVisitor visitMethod(int n, String string, String string2, String string3, String[] stringArray) {
        this.checkState();
        CheckClassAdapter.checkAccess(n, 400895);
        if (!"<init>".equals(string) && !"<clinit>".equals(string)) {
            CheckMethodAdapter.checkMethodIdentifier(this.version, string, "method name");
        }
        CheckMethodAdapter.checkMethodDesc(string2);
        if (string3 != null) {
            CheckClassAdapter.checkMethodSignature(string3);
        }
        if (stringArray != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                CheckMethodAdapter.checkInternalName(stringArray[i], "exception name at index " + i);
            }
        }
        CheckMethodAdapter checkMethodAdapter = this.checkDataFlow ? new CheckMethodAdapter(n, string, string2, super.visitMethod(n, string, string2, string3, stringArray), this.labels) : new CheckMethodAdapter(super.visitMethod(n, string, string2, string3, stringArray), this.labels);
        checkMethodAdapter.version = this.version;
        return checkMethodAdapter;
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        this.checkState();
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(string, bl));
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        this.checkState();
        int n2 = n >>> 24;
        if (n2 != 0 && n2 != 17 && n2 != 16) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(n, typePath, string, bl));
    }

    public void visitAttribute(Attribute attribute) {
        this.checkState();
        if (attribute == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attribute);
    }

    public void visitEnd() {
        this.checkState();
        this.end = true;
        super.visitEnd();
    }

    private void checkState() {
        if (!this.start) {
            throw new IllegalStateException("Cannot visit member before visit has been called.");
        }
        if (this.end) {
            throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
        }
    }

    static void checkAccess(int n, int n2) {
        int n3;
        int n4;
        if ((n & ~n2) != 0) {
            throw new IllegalArgumentException("Invalid access flags: " + n);
        }
        int n5 = (n & 1) == 0 ? 0 : 1;
        int n6 = (n & 2) == 0 ? 0 : 1;
        int n7 = n4 = (n & 4) == 0 ? 0 : 1;
        if (n5 + n6 + n4 > 1) {
            throw new IllegalArgumentException("public private and protected are mutually exclusive: " + n);
        }
        int n8 = (n & 0x10) == 0 ? 0 : 1;
        int n9 = n3 = (n & 0x400) == 0 ? 0 : 1;
        if (n8 + n3 > 1) {
            throw new IllegalArgumentException("final and abstract are mutually exclusive: " + n);
        }
    }

    public static void checkClassSignature(String string) {
        int n = 0;
        if (CheckClassAdapter.getChar(string, 0) == '<') {
            n = CheckClassAdapter.checkFormalTypeParameters(string, n);
        }
        n = CheckClassAdapter.checkClassTypeSignature(string, n);
        while (CheckClassAdapter.getChar(string, n) == 'L') {
            n = CheckClassAdapter.checkClassTypeSignature(string, n);
        }
        if (n != string.length()) {
            throw new IllegalArgumentException(string + ": error at index " + n);
        }
    }

    public static void checkMethodSignature(String string) {
        int n = 0;
        if (CheckClassAdapter.getChar(string, 0) == '<') {
            n = CheckClassAdapter.checkFormalTypeParameters(string, n);
        }
        n = CheckClassAdapter.checkChar('(', string, n);
        while ("ZCBSIFJDL[T".indexOf(CheckClassAdapter.getChar(string, n)) != -1) {
            n = CheckClassAdapter.checkTypeSignature(string, n);
        }
        n = CheckClassAdapter.getChar(string, n = CheckClassAdapter.checkChar(')', string, n)) == 'V' ? ++n : CheckClassAdapter.checkTypeSignature(string, n);
        while (CheckClassAdapter.getChar(string, n) == '^') {
            if (CheckClassAdapter.getChar(string, ++n) == 'L') {
                n = CheckClassAdapter.checkClassTypeSignature(string, n);
                continue;
            }
            n = CheckClassAdapter.checkTypeVariableSignature(string, n);
        }
        if (n != string.length()) {
            throw new IllegalArgumentException(string + ": error at index " + n);
        }
    }

    public static void checkFieldSignature(String string) {
        int n = CheckClassAdapter.checkFieldTypeSignature(string, 0);
        if (n != string.length()) {
            throw new IllegalArgumentException(string + ": error at index " + n);
        }
    }

    static void checkTypeRefAndPath(int n, TypePath typePath) {
        int n2 = 0;
        switch (n >>> 24) {
            case 0: 
            case 1: 
            case 22: {
                n2 = -65536;
                break;
            }
            case 19: 
            case 20: 
            case 21: 
            case 64: 
            case 65: 
            case 67: 
            case 68: 
            case 69: 
            case 70: {
                n2 = -16777216;
                break;
            }
            case 16: 
            case 17: 
            case 18: 
            case 23: 
            case 66: {
                n2 = -256;
                break;
            }
            case 71: 
            case 72: 
            case 73: 
            case 74: 
            case 75: {
                n2 = -16776961;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n >>> 24));
            }
        }
        if ((n & ~n2) != 0) {
            throw new IllegalArgumentException("Invalid type reference 0x" + Integer.toHexString(n));
        }
        if (typePath != null) {
            for (int i = 0; i < typePath.getLength(); ++i) {
                int n3 = typePath.getStep(i);
                if (n3 != 0 && n3 != 1 && n3 != 3 && n3 != 2) {
                    throw new IllegalArgumentException("Invalid type path step " + i + " in " + typePath);
                }
                if (n3 == 3 || typePath.getStepArgument(i) == 0) continue;
                throw new IllegalArgumentException("Invalid type path step argument for step " + i + " in " + typePath);
            }
        }
    }

    private static int checkFormalTypeParameters(String string, int n) {
        n = CheckClassAdapter.checkChar('<', string, n);
        n = CheckClassAdapter.checkFormalTypeParameter(string, n);
        while (CheckClassAdapter.getChar(string, n) != '>') {
            n = CheckClassAdapter.checkFormalTypeParameter(string, n);
        }
        return n + 1;
    }

    private static int checkFormalTypeParameter(String string, int n) {
        n = CheckClassAdapter.checkIdentifier(string, n);
        if ("L[T".indexOf(CheckClassAdapter.getChar(string, n = CheckClassAdapter.checkChar(':', string, n))) != -1) {
            n = CheckClassAdapter.checkFieldTypeSignature(string, n);
        }
        while (CheckClassAdapter.getChar(string, n) == ':') {
            n = CheckClassAdapter.checkFieldTypeSignature(string, n + 1);
        }
        return n;
    }

    private static int checkFieldTypeSignature(String string, int n) {
        switch (CheckClassAdapter.getChar(string, n)) {
            case 'L': {
                return CheckClassAdapter.checkClassTypeSignature(string, n);
            }
            case '[': {
                return CheckClassAdapter.checkTypeSignature(string, n + 1);
            }
        }
        return CheckClassAdapter.checkTypeVariableSignature(string, n);
    }

    private static int checkClassTypeSignature(String string, int n) {
        n = CheckClassAdapter.checkChar('L', string, n);
        n = CheckClassAdapter.checkIdentifier(string, n);
        while (CheckClassAdapter.getChar(string, n) == '/') {
            n = CheckClassAdapter.checkIdentifier(string, n + 1);
        }
        if (CheckClassAdapter.getChar(string, n) == '<') {
            n = CheckClassAdapter.checkTypeArguments(string, n);
        }
        while (CheckClassAdapter.getChar(string, n) == '.') {
            if (CheckClassAdapter.getChar(string, n = CheckClassAdapter.checkIdentifier(string, n + 1)) != '<') continue;
            n = CheckClassAdapter.checkTypeArguments(string, n);
        }
        return CheckClassAdapter.checkChar(';', string, n);
    }

    private static int checkTypeArguments(String string, int n) {
        n = CheckClassAdapter.checkChar('<', string, n);
        n = CheckClassAdapter.checkTypeArgument(string, n);
        while (CheckClassAdapter.getChar(string, n) != '>') {
            n = CheckClassAdapter.checkTypeArgument(string, n);
        }
        return n + 1;
    }

    private static int checkTypeArgument(String string, int n) {
        char c = CheckClassAdapter.getChar(string, n);
        if (c == '*') {
            return n + 1;
        }
        if (c == '+' || c == '-') {
            ++n;
        }
        return CheckClassAdapter.checkFieldTypeSignature(string, n);
    }

    private static int checkTypeVariableSignature(String string, int n) {
        n = CheckClassAdapter.checkChar('T', string, n);
        n = CheckClassAdapter.checkIdentifier(string, n);
        return CheckClassAdapter.checkChar(';', string, n);
    }

    private static int checkTypeSignature(String string, int n) {
        switch (CheckClassAdapter.getChar(string, n)) {
            case 'B': 
            case 'C': 
            case 'D': 
            case 'F': 
            case 'I': 
            case 'J': 
            case 'S': 
            case 'Z': {
                return n + 1;
            }
        }
        return CheckClassAdapter.checkFieldTypeSignature(string, n);
    }

    private static int checkIdentifier(String string, int n) {
        if (!Character.isJavaIdentifierStart(CheckClassAdapter.getChar(string, n))) {
            throw new IllegalArgumentException(string + ": identifier expected at index " + n);
        }
        ++n;
        while (Character.isJavaIdentifierPart(CheckClassAdapter.getChar(string, n))) {
            ++n;
        }
        return n;
    }

    private static int checkChar(char c, String string, int n) {
        if (CheckClassAdapter.getChar(string, n) == c) {
            return n + 1;
        }
        throw new IllegalArgumentException(string + ": '" + c + "' expected at index " + n);
    }

    private static char getChar(String string, int n) {
        return n < string.length() ? string.charAt(n) : (char)'\u0000';
    }
}

