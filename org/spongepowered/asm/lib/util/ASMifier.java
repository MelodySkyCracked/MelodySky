/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.util.ASMifiable;
import org.spongepowered.asm.lib.util.Printer;
import org.spongepowered.asm.lib.util.TraceClassVisitor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ASMifier
extends Printer {
    protected final String name;
    protected final int id;
    protected Map labelNames;
    private static final int ACCESS_CLASS = 262144;
    private static final int ACCESS_FIELD = 524288;
    private static final int ACCESS_INNER = 0x100000;

    public ASMifier() {
        this(327680, "cw", 0);
        if (this.getClass() != ASMifier.class) {
            throw new IllegalStateException();
        }
    }

    protected ASMifier(int n, String string, int n2) {
        super(n);
        this.name = string;
        this.id = n2;
    }

    public static void main(String[] stringArray) throws Exception {
        int n = 0;
        int n2 = 2;
        boolean bl = true;
        if (stringArray.length < 1 || stringArray.length > 2) {
            bl = false;
        }
        if (bl && "-debug".equals(stringArray[0])) {
            n = 1;
            n2 = 0;
            if (stringArray.length != 2) {
                bl = false;
            }
        }
        if (!bl) {
            System.err.println("Prints the ASM code to generate the given class.");
            System.err.println("Usage: ASMifier [-debug] <fully qualified class name or class file name>");
            return;
        }
        ClassReader classReader = stringArray[n].endsWith(".class") || stringArray[n].indexOf(92) > -1 || stringArray[n].indexOf(47) > -1 ? new ClassReader(new FileInputStream(stringArray[n])) : new ClassReader(stringArray[n]);
        classReader.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), n2);
    }

    public void visit(int n, int n2, String string, String string2, String string3, String[] stringArray) {
        String string4;
        int n3 = string.lastIndexOf(47);
        if (n3 == -1) {
            string4 = string;
        } else {
            this.text.add("package asm." + string.substring(0, n3).replace('/', '.') + ";\n");
            string4 = string.substring(n3 + 1);
        }
        this.text.add("import java.util.*;\n");
        this.text.add("import org.objectweb.asm.*;\n");
        this.text.add("public class " + string4 + "Dump implements Opcodes {\n\n");
        this.text.add("public static byte[] dump () throws Exception {\n\n");
        this.text.add("ClassWriter cw = new ClassWriter(0);\n");
        this.text.add("FieldVisitor fv;\n");
        this.text.add("MethodVisitor mv;\n");
        this.text.add("AnnotationVisitor av0;\n\n");
        this.buf.setLength(0);
        this.buf.append("cw.visit(");
        switch (n) {
            case 196653: {
                this.buf.append("V1_1");
                break;
            }
            case 46: {
                this.buf.append("V1_2");
                break;
            }
            case 47: {
                this.buf.append("V1_3");
                break;
            }
            case 48: {
                this.buf.append("V1_4");
                break;
            }
            case 49: {
                this.buf.append("V1_5");
                break;
            }
            case 50: {
                this.buf.append("V1_6");
                break;
            }
            case 51: {
                this.buf.append("V1_7");
                break;
            }
            default: {
                this.buf.append(n);
            }
        }
        this.buf.append(", ");
        this.appendAccess(n2 | 0x40000);
        this.buf.append(", ");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(string3);
        this.buf.append(", ");
        if (stringArray != null && stringArray.length > 0) {
            this.buf.append("new String[] {");
            for (int i = 0; i < stringArray.length; ++i) {
                this.buf.append(i == 0 ? " " : ", ");
                this.appendConstant(stringArray[i]);
            }
            this.buf.append(" }");
        } else {
            this.buf.append("null");
        }
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }

    public void visitSource(String string, String string2) {
        this.buf.setLength(0);
        this.buf.append("cw.visitSource(");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }

    public void visitOuterClass(String string, String string2, String string3) {
        this.buf.setLength(0);
        this.buf.append("cw.visitOuterClass(");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(string3);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }

    public ASMifier visitClassAnnotation(String string, boolean bl) {
        return this.visitAnnotation(string, bl);
    }

    public ASMifier visitClassTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation(n, typePath, string, bl);
    }

    public void visitClassAttribute(Attribute attribute) {
        this.visitAttribute(attribute);
    }

    public void visitInnerClass(String string, String string2, String string3, int n) {
        this.buf.setLength(0);
        this.buf.append("cw.visitInnerClass(");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(string3);
        this.buf.append(", ");
        this.appendAccess(n | 0x100000);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }

    public ASMifier visitField(int n, String string, String string2, String string3, Object object) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("fv = cw.visitField(");
        this.appendAccess(n | 0x80000);
        this.buf.append(", ");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(string3);
        this.buf.append(", ");
        this.appendConstant(object);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("fv", 0);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public ASMifier visitMethod(int n, String string, String string2, String string3, String[] stringArray) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("mv = cw.visitMethod(");
        this.appendAccess(n);
        this.buf.append(", ");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(string3);
        this.buf.append(", ");
        if (stringArray != null && stringArray.length > 0) {
            this.buf.append("new String[] {");
            for (int i = 0; i < stringArray.length; ++i) {
                this.buf.append(i == 0 ? " " : ", ");
                this.appendConstant(stringArray[i]);
            }
            this.buf.append(" }");
        } else {
            this.buf.append("null");
        }
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("mv", 0);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public void visitClassEnd() {
        this.text.add("cw.visitEnd();\n\n");
        this.text.add("return cw.toByteArray();\n");
        this.text.add("}\n");
        this.text.add("}\n");
    }

    public void visit(String string, Object object) {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visit(");
        ASMifier.appendConstant(this.buf, string);
        this.buf.append(", ");
        ASMifier.appendConstant(this.buf, object);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitEnum(String string, String string2, String string3) {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visitEnum(");
        ASMifier.appendConstant(this.buf, string);
        this.buf.append(", ");
        ASMifier.appendConstant(this.buf, string2);
        this.buf.append(", ");
        ASMifier.appendConstant(this.buf, string3);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public ASMifier visitAnnotation(String string, String string2) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
        this.buf.append(this.id).append(".visitAnnotation(");
        ASMifier.appendConstant(this.buf, string);
        this.buf.append(", ");
        ASMifier.appendConstant(this.buf, string2);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("av", this.id + 1);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public ASMifier visitArray(String string) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
        this.buf.append(this.id).append(".visitArray(");
        ASMifier.appendConstant(this.buf, string);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("av", this.id + 1);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public void visitAnnotationEnd() {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }

    public ASMifier visitFieldAnnotation(String string, boolean bl) {
        return this.visitAnnotation(string, bl);
    }

    public ASMifier visitFieldTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation(n, typePath, string, bl);
    }

    public void visitFieldAttribute(Attribute attribute) {
        this.visitAttribute(attribute);
    }

    public void visitFieldEnd() {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }

    public void visitParameter(String string, int n) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitParameter(");
        ASMifier.appendString(this.buf, string);
        this.buf.append(", ");
        this.appendAccess(n);
        this.text.add(this.buf.append(");\n").toString());
    }

    public ASMifier visitAnnotationDefault() {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotationDefault();\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("av", 0);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public ASMifier visitMethodAnnotation(String string, boolean bl) {
        return this.visitAnnotation(string, bl);
    }

    public ASMifier visitMethodTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation(n, typePath, string, bl);
    }

    public ASMifier visitParameterAnnotation(int n, String string, boolean bl) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitParameterAnnotation(").append(n).append(", ");
        this.appendConstant(string);
        this.buf.append(", ").append(bl).append(");\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("av", 0);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public void visitMethodAttribute(Attribute attribute) {
        this.visitAttribute(attribute);
    }

    public void visitCode() {
        this.text.add(this.name + ".visitCode();\n");
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        this.buf.setLength(0);
        switch (n) {
            case -1: 
            case 0: {
                this.declareFrameTypes(n2, objectArray);
                this.declareFrameTypes(n3, objectArray2);
                if (n == -1) {
                    this.buf.append(this.name).append(".visitFrame(Opcodes.F_NEW, ");
                } else {
                    this.buf.append(this.name).append(".visitFrame(Opcodes.F_FULL, ");
                }
                this.buf.append(n2).append(", new Object[] {");
                this.appendFrameTypes(n2, objectArray);
                this.buf.append("}, ").append(n3).append(", new Object[] {");
                this.appendFrameTypes(n3, objectArray2);
                this.buf.append('}');
                break;
            }
            case 1: {
                this.declareFrameTypes(n2, objectArray);
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_APPEND,").append(n2).append(", new Object[] {");
                this.appendFrameTypes(n2, objectArray);
                this.buf.append("}, 0, null");
                break;
            }
            case 2: {
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_CHOP,").append(n2).append(", null, 0, null");
                break;
            }
            case 3: {
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
                break;
            }
            case 4: {
                this.declareFrameTypes(1, objectArray2);
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
                this.appendFrameTypes(1, objectArray2);
                this.buf.append('}');
            }
        }
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitInsn(int n) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitInsn(").append(OPCODES[n]).append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitIntInsn(int n, int n2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitIntInsn(").append(OPCODES[n]).append(", ").append(n == 188 ? TYPES[n2] : Integer.toString(n2)).append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitVarInsn(int n, int n2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitVarInsn(").append(OPCODES[n]).append(", ").append(n2).append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitTypeInsn(int n, String string) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitTypeInsn(").append(OPCODES[n]).append(", ");
        this.appendConstant(string);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitFieldInsn(").append(OPCODES[n]).append(", ");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(string3);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    @Deprecated
    public void visitMethodInsn(int n, String string, String string2, String string3) {
        if (this.api >= 327680) {
            super.visitMethodInsn(n, string, string2, string3);
            return;
        }
        this.doVisitMethodInsn(n, string, string2, string3, n == 185);
    }

    public void visitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        if (this.api < 327680) {
            super.visitMethodInsn(n, string, string2, string3, bl);
            return;
        }
        this.doVisitMethodInsn(n, string, string2, string3, bl);
    }

    private void doVisitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMethodInsn(").append(OPCODES[n]).append(", ");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(string3);
        this.buf.append(", ");
        this.buf.append(bl ? "true" : "false");
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitInvokeDynamicInsn(");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(handle);
        this.buf.append(", new Object[]{");
        for (int i = 0; i < objectArray.length; ++i) {
            this.appendConstant(objectArray[i]);
            if (i == objectArray.length - 1) continue;
            this.buf.append(", ");
        }
        this.buf.append("});\n");
        this.text.add(this.buf.toString());
    }

    public void visitJumpInsn(int n, Label label) {
        this.buf.setLength(0);
        this.declareLabel(label);
        this.buf.append(this.name).append(".visitJumpInsn(").append(OPCODES[n]).append(", ");
        this.appendLabel(label);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitLabel(Label label) {
        this.buf.setLength(0);
        this.declareLabel(label);
        this.buf.append(this.name).append(".visitLabel(");
        this.appendLabel(label);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitLdcInsn(Object object) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLdcInsn(");
        this.appendConstant(object);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitIincInsn(int n, int n2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitIincInsn(").append(n).append(", ").append(n2).append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        int n3;
        this.buf.setLength(0);
        for (n3 = 0; n3 < labelArray.length; ++n3) {
            this.declareLabel(labelArray[n3]);
        }
        this.declareLabel(label);
        this.buf.append(this.name).append(".visitTableSwitchInsn(").append(n).append(", ").append(n2).append(", ");
        this.appendLabel(label);
        this.buf.append(", new Label[] {");
        for (n3 = 0; n3 < labelArray.length; ++n3) {
            this.buf.append(n3 == 0 ? " " : ", ");
            this.appendLabel(labelArray[n3]);
        }
        this.buf.append(" });\n");
        this.text.add(this.buf.toString());
    }

    public void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        int n;
        this.buf.setLength(0);
        for (n = 0; n < labelArray.length; ++n) {
            this.declareLabel(labelArray[n]);
        }
        this.declareLabel(label);
        this.buf.append(this.name).append(".visitLookupSwitchInsn(");
        this.appendLabel(label);
        this.buf.append(", new int[] {");
        for (n = 0; n < nArray.length; ++n) {
            this.buf.append(n == 0 ? " " : ", ").append(nArray[n]);
        }
        this.buf.append(" }, new Label[] {");
        for (n = 0; n < labelArray.length; ++n) {
            this.buf.append(n == 0 ? " " : ", ");
            this.appendLabel(labelArray[n]);
        }
        this.buf.append(" });\n");
        this.text.add(this.buf.toString());
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMultiANewArrayInsn(");
        this.appendConstant(string);
        this.buf.append(", ").append(n).append(");\n");
        this.text.add(this.buf.toString());
    }

    public ASMifier visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation("visitInsnAnnotation", n, typePath, string, bl);
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        this.buf.setLength(0);
        this.declareLabel(label);
        this.declareLabel(label2);
        this.declareLabel(label3);
        this.buf.append(this.name).append(".visitTryCatchBlock(");
        this.appendLabel(label);
        this.buf.append(", ");
        this.appendLabel(label2);
        this.buf.append(", ");
        this.appendLabel(label3);
        this.buf.append(", ");
        this.appendConstant(string);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public ASMifier visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation("visitTryCatchAnnotation", n, typePath, string, bl);
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLocalVariable(");
        this.appendConstant(string);
        this.buf.append(", ");
        this.appendConstant(string2);
        this.buf.append(", ");
        this.appendConstant(string3);
        this.buf.append(", ");
        this.appendLabel(label);
        this.buf.append(", ");
        this.appendLabel(label2);
        this.buf.append(", ").append(n).append(");\n");
        this.text.add(this.buf.toString());
    }

    public Printer visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        int n2;
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitLocalVariableAnnotation(");
        this.buf.append(n);
        this.buf.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
        this.buf.append("new Label[] {");
        for (n2 = 0; n2 < labelArray.length; ++n2) {
            this.buf.append(n2 == 0 ? " " : ", ");
            this.appendLabel(labelArray[n2]);
        }
        this.buf.append(" }, new Label[] {");
        for (n2 = 0; n2 < labelArray2.length; ++n2) {
            this.buf.append(n2 == 0 ? " " : ", ");
            this.appendLabel(labelArray2[n2]);
        }
        this.buf.append(" }, new int[] {");
        for (n2 = 0; n2 < nArray.length; ++n2) {
            this.buf.append(n2 == 0 ? " " : ", ").append(nArray[n2]);
        }
        this.buf.append(" }, ");
        this.appendConstant(string);
        this.buf.append(", ").append(bl).append(");\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("av", 0);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public void visitLineNumber(int n, Label label) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLineNumber(").append(n).append(", ");
        this.appendLabel(label);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitMaxs(int n, int n2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMaxs(").append(n).append(", ").append(n2).append(");\n");
        this.text.add(this.buf.toString());
    }

    public void visitMethodEnd() {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }

    public ASMifier visitAnnotation(String string, boolean bl) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotation(");
        this.appendConstant(string);
        this.buf.append(", ").append(bl).append(");\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("av", 0);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public ASMifier visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation("visitTypeAnnotation", n, typePath, string, bl);
    }

    public ASMifier visitTypeAnnotation(String string, int n, TypePath typePath, String string2, boolean bl) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".").append(string).append("(");
        this.buf.append(n);
        this.buf.append(", TypePath.fromString(\"").append(typePath).append("\"), ");
        this.appendConstant(string2);
        this.buf.append(", ").append(bl).append(");\n");
        this.text.add(this.buf.toString());
        ASMifier aSMifier = this.createASMifier("av", 0);
        this.text.add(aSMifier.getText());
        this.text.add("}\n");
        return aSMifier;
    }

    public void visitAttribute(Attribute attribute) {
        this.buf.setLength(0);
        this.buf.append("// ATTRIBUTE ").append(attribute.type).append('\n');
        if (attribute instanceof ASMifiable) {
            if (this.labelNames == null) {
                this.labelNames = new HashMap();
            }
            this.buf.append("{\n");
            ((ASMifiable)((Object)attribute)).asmify(this.buf, "attr", this.labelNames);
            this.buf.append(this.name).append(".visitAttribute(attr);\n");
            this.buf.append("}\n");
        }
        this.text.add(this.buf.toString());
    }

    protected ASMifier createASMifier(String string, int n) {
        return new ASMifier(327680, string, n);
    }

    void appendAccess(int n) {
        boolean bl = true;
        if ((n & 1) != 0) {
            this.buf.append("ACC_PUBLIC");
            bl = false;
        }
        if ((n & 2) != 0) {
            this.buf.append("ACC_PRIVATE");
            bl = false;
        }
        if ((n & 4) != 0) {
            this.buf.append("ACC_PROTECTED");
            bl = false;
        }
        if ((n & 0x10) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_FINAL");
            bl = false;
        }
        if ((n & 8) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_STATIC");
            bl = false;
        }
        if ((n & 0x20) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            if ((n & 0x40000) == 0) {
                this.buf.append("ACC_SYNCHRONIZED");
            } else {
                this.buf.append("ACC_SUPER");
            }
            bl = false;
        }
        if ((n & 0x40) != 0 && (n & 0x80000) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_VOLATILE");
            bl = false;
        }
        if ((n & 0x40) != 0 && (n & 0x40000) == 0 && (n & 0x80000) == 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_BRIDGE");
            bl = false;
        }
        if ((n & 0x80) != 0 && (n & 0x40000) == 0 && (n & 0x80000) == 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_VARARGS");
            bl = false;
        }
        if ((n & 0x80) != 0 && (n & 0x80000) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_TRANSIENT");
            bl = false;
        }
        if ((n & 0x100) != 0 && (n & 0x40000) == 0 && (n & 0x80000) == 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_NATIVE");
            bl = false;
        }
        if ((n & 0x4000) != 0 && ((n & 0x40000) != 0 || (n & 0x80000) != 0 || (n & 0x100000) != 0)) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ENUM");
            bl = false;
        }
        if ((n & 0x2000) != 0 && ((n & 0x40000) != 0 || (n & 0x100000) != 0)) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ANNOTATION");
            bl = false;
        }
        if ((n & 0x400) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ABSTRACT");
            bl = false;
        }
        if ((n & 0x200) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_INTERFACE");
            bl = false;
        }
        if ((n & 0x800) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_STRICT");
            bl = false;
        }
        if ((n & 0x1000) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_SYNTHETIC");
            bl = false;
        }
        if ((n & 0x20000) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_DEPRECATED");
            bl = false;
        }
        if ((n & 0x8000) != 0) {
            if (!bl) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_MANDATED");
            bl = false;
        }
        if (bl) {
            this.buf.append('0');
        }
    }

    protected void appendConstant(Object object) {
        ASMifier.appendConstant(this.buf, object);
    }

    static void appendConstant(StringBuffer stringBuffer, Object object) {
        if (object == null) {
            stringBuffer.append("null");
        } else if (object instanceof String) {
            ASMifier.appendString(stringBuffer, (String)object);
        } else if (object instanceof Type) {
            stringBuffer.append("Type.getType(\"");
            stringBuffer.append(((Type)object).getDescriptor());
            stringBuffer.append("\")");
        } else if (object instanceof Handle) {
            stringBuffer.append("new Handle(");
            Handle handle = (Handle)object;
            stringBuffer.append("Opcodes.").append(HANDLE_TAG[handle.getTag()]).append(", \"");
            stringBuffer.append(handle.getOwner()).append("\", \"");
            stringBuffer.append(handle.getName()).append("\", \"");
            stringBuffer.append(handle.getDesc()).append("\")");
        } else if (object instanceof Byte) {
            stringBuffer.append("new Byte((byte)").append(object).append(')');
        } else if (object instanceof Boolean) {
            stringBuffer.append((Boolean)object != false ? "Boolean.TRUE" : "Boolean.FALSE");
        } else if (object instanceof Short) {
            stringBuffer.append("new Short((short)").append(object).append(')');
        } else if (object instanceof Character) {
            char c = ((Character)object).charValue();
            stringBuffer.append("new Character((char)").append((int)c).append(')');
        } else if (object instanceof Integer) {
            stringBuffer.append("new Integer(").append(object).append(')');
        } else if (object instanceof Float) {
            stringBuffer.append("new Float(\"").append(object).append("\")");
        } else if (object instanceof Long) {
            stringBuffer.append("new Long(").append(object).append("L)");
        } else if (object instanceof Double) {
            stringBuffer.append("new Double(\"").append(object).append("\")");
        } else if (object instanceof byte[]) {
            byte[] byArray = (byte[])object;
            stringBuffer.append("new byte[] {");
            for (int i = 0; i < byArray.length; ++i) {
                stringBuffer.append(i == 0 ? "" : ",").append(byArray[i]);
            }
            stringBuffer.append('}');
        } else if (object instanceof boolean[]) {
            boolean[] blArray = (boolean[])object;
            stringBuffer.append("new boolean[] {");
            for (int i = 0; i < blArray.length; ++i) {
                stringBuffer.append(i == 0 ? "" : ",").append(blArray[i]);
            }
            stringBuffer.append('}');
        } else if (object instanceof short[]) {
            short[] sArray = (short[])object;
            stringBuffer.append("new short[] {");
            for (int i = 0; i < sArray.length; ++i) {
                stringBuffer.append(i == 0 ? "" : ",").append("(short)").append(sArray[i]);
            }
            stringBuffer.append('}');
        } else if (object instanceof char[]) {
            char[] cArray = (char[])object;
            stringBuffer.append("new char[] {");
            for (int i = 0; i < cArray.length; ++i) {
                stringBuffer.append(i == 0 ? "" : ",").append("(char)").append((int)cArray[i]);
            }
            stringBuffer.append('}');
        } else if (object instanceof int[]) {
            int[] nArray = (int[])object;
            stringBuffer.append("new int[] {");
            for (int i = 0; i < nArray.length; ++i) {
                stringBuffer.append(i == 0 ? "" : ",").append(nArray[i]);
            }
            stringBuffer.append('}');
        } else if (object instanceof long[]) {
            long[] lArray = (long[])object;
            stringBuffer.append("new long[] {");
            for (int i = 0; i < lArray.length; ++i) {
                stringBuffer.append(i == 0 ? "" : ",").append(lArray[i]).append('L');
            }
            stringBuffer.append('}');
        } else if (object instanceof float[]) {
            float[] fArray = (float[])object;
            stringBuffer.append("new float[] {");
            for (int i = 0; i < fArray.length; ++i) {
                stringBuffer.append(i == 0 ? "" : ",").append(fArray[i]).append('f');
            }
            stringBuffer.append('}');
        } else if (object instanceof double[]) {
            double[] dArray = (double[])object;
            stringBuffer.append("new double[] {");
            for (int i = 0; i < dArray.length; ++i) {
                stringBuffer.append(i == 0 ? "" : ",").append(dArray[i]).append('d');
            }
            stringBuffer.append('}');
        }
    }

    private void declareFrameTypes(int n, Object[] objectArray) {
        for (int i = 0; i < n; ++i) {
            if (!(objectArray[i] instanceof Label)) continue;
            this.declareLabel((Label)objectArray[i]);
        }
    }

    private void appendFrameTypes(int n, Object[] objectArray) {
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                this.buf.append(", ");
            }
            if (objectArray[i] instanceof String) {
                this.appendConstant(objectArray[i]);
                continue;
            }
            if (objectArray[i] instanceof Integer) {
                switch ((Integer)objectArray[i]) {
                    case 0: {
                        this.buf.append("Opcodes.TOP");
                        break;
                    }
                    case 1: {
                        this.buf.append("Opcodes.INTEGER");
                        break;
                    }
                    case 2: {
                        this.buf.append("Opcodes.FLOAT");
                        break;
                    }
                    case 3: {
                        this.buf.append("Opcodes.DOUBLE");
                        break;
                    }
                    case 4: {
                        this.buf.append("Opcodes.LONG");
                        break;
                    }
                    case 5: {
                        this.buf.append("Opcodes.NULL");
                        break;
                    }
                    case 6: {
                        this.buf.append("Opcodes.UNINITIALIZED_THIS");
                    }
                }
                continue;
            }
            this.appendLabel((Label)objectArray[i]);
        }
    }

    protected void declareLabel(Label label) {
        String string;
        if (this.labelNames == null) {
            this.labelNames = new HashMap();
        }
        if ((string = (String)this.labelNames.get(label)) == null) {
            string = "l" + this.labelNames.size();
            this.labelNames.put(label, string);
            this.buf.append("Label ").append(string).append(" = new Label();\n");
        }
    }

    protected void appendLabel(Label label) {
        this.buf.append((String)this.labelNames.get(label));
    }

    public Printer visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTryCatchAnnotation(n, typePath, string, bl);
    }

    public Printer visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitInsnAnnotation(n, typePath, string, bl);
    }

    public Printer visitParameterAnnotation(int n, String string, boolean bl) {
        return this.visitParameterAnnotation(n, string, bl);
    }

    public Printer visitMethodTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitMethodTypeAnnotation(n, typePath, string, bl);
    }

    public Printer visitMethodAnnotation(String string, boolean bl) {
        return this.visitMethodAnnotation(string, bl);
    }

    public Printer visitAnnotationDefault() {
        return this.visitAnnotationDefault();
    }

    public Printer visitFieldTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitFieldTypeAnnotation(n, typePath, string, bl);
    }

    public Printer visitFieldAnnotation(String string, boolean bl) {
        return this.visitFieldAnnotation(string, bl);
    }

    public Printer visitArray(String string) {
        return this.visitArray(string);
    }

    public Printer visitAnnotation(String string, String string2) {
        return this.visitAnnotation(string, string2);
    }

    public Printer visitMethod(int n, String string, String string2, String string3, String[] stringArray) {
        return this.visitMethod(n, string, string2, string3, stringArray);
    }

    public Printer visitField(int n, String string, String string2, String string3, Object object) {
        return this.visitField(n, string, string2, string3, object);
    }

    public Printer visitClassTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitClassTypeAnnotation(n, typePath, string, bl);
    }

    public Printer visitClassAnnotation(String string, boolean bl) {
        return this.visitClassAnnotation(string, bl);
    }
}

