/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.util.CheckAnnotationAdapter;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.lib.util.I;

public class CheckMethodAdapter
extends MethodVisitor {
    public int version;
    private int access;
    private boolean startCode;
    private boolean endCode;
    private boolean endMethod;
    private int insnCount;
    private final Map labels;
    private Set usedLabels;
    private int expandedFrames;
    private int compressedFrames;
    private int lastFrame = -1;
    private List handlers;
    private static final int[] TYPE;
    private static Field labelStatusField;

    public CheckMethodAdapter(MethodVisitor methodVisitor) {
        this(methodVisitor, new HashMap());
    }

    public CheckMethodAdapter(MethodVisitor methodVisitor, Map map) {
        this(327680, methodVisitor, map);
        if (this.getClass() != CheckMethodAdapter.class) {
            throw new IllegalStateException();
        }
    }

    protected CheckMethodAdapter(int n, MethodVisitor methodVisitor, Map map) {
        super(n, methodVisitor);
        this.labels = map;
        this.usedLabels = new HashSet();
        this.handlers = new ArrayList();
    }

    public CheckMethodAdapter(int n, String string, String string2, MethodVisitor methodVisitor, Map map) {
        this(new I(327680, n, string, string2, null, null, methodVisitor), map);
        this.access = n;
    }

    public void visitParameter(String string, int n) {
        if (string != null) {
            CheckMethodAdapter.checkUnqualifiedName(this.version, string, "name");
        }
        CheckClassAdapter.checkAccess(n, 36880);
        super.visitParameter(string, n);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        this.checkEndMethod();
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(string, bl));
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        this.checkEndMethod();
        int n2 = n >>> 24;
        if (n2 != 1 && n2 != 18 && n2 != 20 && n2 != 21 && n2 != 22 && n2 != 23) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(n, typePath, string, bl));
    }

    public AnnotationVisitor visitAnnotationDefault() {
        this.checkEndMethod();
        return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
    }

    public AnnotationVisitor visitParameterAnnotation(int n, String string, boolean bl) {
        this.checkEndMethod();
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitParameterAnnotation(n, string, bl));
    }

    public void visitAttribute(Attribute attribute) {
        this.checkEndMethod();
        if (attribute == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(attribute);
    }

    public void visitCode() {
        if ((this.access & 0x400) != 0) {
            throw new RuntimeException("Abstract methods cannot have code");
        }
        this.startCode = true;
        super.visitCode();
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        int n4;
        int n5;
        int n6;
        if (this.insnCount == this.lastFrame) {
            throw new IllegalStateException("At most one frame can be visited at a given code location.");
        }
        this.lastFrame = this.insnCount;
        switch (n) {
            case -1: 
            case 0: {
                n6 = Integer.MAX_VALUE;
                n5 = Integer.MAX_VALUE;
                break;
            }
            case 3: {
                n6 = 0;
                n5 = 0;
                break;
            }
            case 4: {
                n6 = 0;
                n5 = 1;
                break;
            }
            case 1: 
            case 2: {
                n6 = 3;
                n5 = 0;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid frame type " + n);
            }
        }
        if (n2 > n6) {
            throw new IllegalArgumentException("Invalid nLocal=" + n2 + " for frame type " + n);
        }
        if (n3 > n5) {
            throw new IllegalArgumentException("Invalid nStack=" + n3 + " for frame type " + n);
        }
        if (n != 2) {
            if (n2 > 0 && (objectArray == null || objectArray.length < n2)) {
                throw new IllegalArgumentException("Array local[] is shorter than nLocal");
            }
            for (n4 = 0; n4 < n2; ++n4) {
                this.checkFrameValue(objectArray[n4]);
            }
        }
        if (n3 > 0 && (objectArray2 == null || objectArray2.length < n3)) {
            throw new IllegalArgumentException("Array stack[] is shorter than nStack");
        }
        for (n4 = 0; n4 < n3; ++n4) {
            this.checkFrameValue(objectArray2[n4]);
        }
        if (n == -1) {
            ++this.expandedFrames;
        } else {
            ++this.compressedFrames;
        }
        if (this.expandedFrames > 0 && this.compressedFrames > 0) {
            throw new RuntimeException("Expanded and compressed frames must not be mixed.");
        }
        super.visitFrame(n, n2, objectArray, n3, objectArray2);
    }

    public void visitInsn(int n) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkOpcode(n, 0);
        super.visitInsn(n);
        ++this.insnCount;
    }

    public void visitIntInsn(int n, int n2) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkOpcode(n, 1);
        switch (n) {
            case 16: {
                CheckMethodAdapter.checkSignedByte(n2, "Invalid operand");
                break;
            }
            case 17: {
                CheckMethodAdapter.checkSignedShort(n2, "Invalid operand");
                break;
            }
            default: {
                if (n2 >= 4 && n2 <= 11) break;
                throw new IllegalArgumentException("Invalid operand (must be an array type code T_...): " + n2);
            }
        }
        super.visitIntInsn(n, n2);
        ++this.insnCount;
    }

    public void visitVarInsn(int n, int n2) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkOpcode(n, 2);
        CheckMethodAdapter.checkUnsignedShort(n2, "Invalid variable index");
        super.visitVarInsn(n, n2);
        ++this.insnCount;
    }

    public void visitTypeInsn(int n, String string) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkOpcode(n, 3);
        CheckMethodAdapter.checkInternalName(string, "type");
        if (n == 187 && string.charAt(0) == '[') {
            throw new IllegalArgumentException("NEW cannot be used to create arrays: " + string);
        }
        super.visitTypeInsn(n, string);
        ++this.insnCount;
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkOpcode(n, 4);
        CheckMethodAdapter.checkInternalName(string, "owner");
        CheckMethodAdapter.checkUnqualifiedName(this.version, string2, "name");
        CheckMethodAdapter.checkDesc(string3, false);
        super.visitFieldInsn(n, string, string2, string3);
        ++this.insnCount;
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
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkOpcode(n, 5);
        if (n != 183 || !"<init>".equals(string2)) {
            CheckMethodAdapter.checkMethodIdentifier(this.version, string2, "name");
        }
        CheckMethodAdapter.checkInternalName(string, "owner");
        CheckMethodAdapter.checkMethodDesc(string3);
        if (n == 182 && bl) {
            throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
        }
        if (n == 185 && !bl) {
            throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
        }
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, string, string2, string3, bl);
        }
        ++this.insnCount;
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkMethodIdentifier(this.version, string, "name");
        CheckMethodAdapter.checkMethodDesc(string2);
        if (handle.getTag() != 6 && handle.getTag() != 8) {
            throw new IllegalArgumentException("invalid handle tag " + handle.getTag());
        }
        for (int i = 0; i < objectArray.length; ++i) {
            this.checkLDCConstant(objectArray[i]);
        }
        super.visitInvokeDynamicInsn(string, string2, handle, objectArray);
        ++this.insnCount;
    }

    public void visitJumpInsn(int n, Label label) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkOpcode(n, 6);
        this.checkLabel(label, false, "label");
        CheckMethodAdapter.checkNonDebugLabel(label);
        super.visitJumpInsn(n, label);
        this.usedLabels.add(label);
        ++this.insnCount;
    }

    public void visitLabel(Label label) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLabel(label, false, "label");
        if (this.labels.get(label) != null) {
            throw new IllegalArgumentException("Already visited label");
        }
        this.labels.put(label, new Integer(this.insnCount));
        super.visitLabel(label);
    }

    public void visitLdcInsn(Object object) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLDCConstant(object);
        super.visitLdcInsn(object);
        ++this.insnCount;
    }

    public void visitIincInsn(int n, int n2) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkUnsignedShort(n, "Invalid variable index");
        CheckMethodAdapter.checkSignedShort(n2, "Invalid increment");
        super.visitIincInsn(n, n2);
        ++this.insnCount;
    }

    public void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        int n3;
        this.checkStartCode();
        this.checkEndCode();
        if (n2 < n) {
            throw new IllegalArgumentException("Max = " + n2 + " must be greater than or equal to min = " + n);
        }
        this.checkLabel(label, false, "default label");
        CheckMethodAdapter.checkNonDebugLabel(label);
        if (labelArray == null || labelArray.length != n2 - n + 1) {
            throw new IllegalArgumentException("There must be max - min + 1 labels");
        }
        for (n3 = 0; n3 < labelArray.length; ++n3) {
            this.checkLabel(labelArray[n3], false, "label at index " + n3);
            CheckMethodAdapter.checkNonDebugLabel(labelArray[n3]);
        }
        super.visitTableSwitchInsn(n, n2, label, labelArray);
        for (n3 = 0; n3 < labelArray.length; ++n3) {
            this.usedLabels.add(labelArray[n3]);
        }
        ++this.insnCount;
    }

    public void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        int n;
        this.checkEndCode();
        this.checkStartCode();
        this.checkLabel(label, false, "default label");
        CheckMethodAdapter.checkNonDebugLabel(label);
        if (nArray == null || labelArray == null || nArray.length != labelArray.length) {
            throw new IllegalArgumentException("There must be the same number of keys and labels");
        }
        for (n = 0; n < labelArray.length; ++n) {
            this.checkLabel(labelArray[n], false, "label at index " + n);
            CheckMethodAdapter.checkNonDebugLabel(labelArray[n]);
        }
        super.visitLookupSwitchInsn(label, nArray, labelArray);
        this.usedLabels.add(label);
        for (n = 0; n < labelArray.length; ++n) {
            this.usedLabels.add(labelArray[n]);
        }
        ++this.insnCount;
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkDesc(string, false);
        if (string.charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): " + string);
        }
        if (n < 1) {
            throw new IllegalArgumentException("Invalid dimensions (must be greater than 0): " + n);
        }
        if (n > string.lastIndexOf(91) + 1) {
            throw new IllegalArgumentException("Invalid dimensions (must not be greater than dims(desc)): " + n);
        }
        super.visitMultiANewArrayInsn(string, n);
        ++this.insnCount;
    }

    public AnnotationVisitor visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        this.checkStartCode();
        this.checkEndCode();
        int n2 = n >>> 24;
        if (n2 != 67 && n2 != 68 && n2 != 69 && n2 != 70 && n2 != 71 && n2 != 72 && n2 != 73 && n2 != 74 && n2 != 75) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitInsnAnnotation(n, typePath, string, bl));
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLabel(label, false, "start label");
        this.checkLabel(label2, false, "end label");
        this.checkLabel(label3, false, "handler label");
        CheckMethodAdapter.checkNonDebugLabel(label);
        CheckMethodAdapter.checkNonDebugLabel(label2);
        CheckMethodAdapter.checkNonDebugLabel(label3);
        if (this.labels.get(label) != null || this.labels.get(label2) != null || this.labels.get(label3) != null) {
            throw new IllegalStateException("Try catch blocks must be visited before their labels");
        }
        if (string != null) {
            CheckMethodAdapter.checkInternalName(string, "type");
        }
        super.visitTryCatchBlock(label, label2, label3, string);
        this.handlers.add(label);
        this.handlers.add(label2);
    }

    public AnnotationVisitor visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        this.checkStartCode();
        this.checkEndCode();
        int n2 = n >>> 24;
        if (n2 != 66) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        CheckMethodAdapter.checkDesc(string, false);
        return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(n, typePath, string, bl));
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkUnqualifiedName(this.version, string, "name");
        CheckMethodAdapter.checkDesc(string2, false);
        this.checkLabel(label, true, "start label");
        this.checkLabel(label2, true, "end label");
        CheckMethodAdapter.checkUnsignedShort(n, "Invalid variable index");
        int n2 = (Integer)this.labels.get(label);
        int n3 = (Integer)this.labels.get(label2);
        if (n3 < n2) {
            throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
        }
        super.visitLocalVariable(string, string2, string3, label, label2, n);
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        this.checkStartCode();
        this.checkEndCode();
        int n2 = n >>> 24;
        if (n2 != 64 && n2 != 65) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(n2));
        }
        CheckClassAdapter.checkTypeRefAndPath(n, typePath);
        CheckMethodAdapter.checkDesc(string, false);
        if (labelArray == null || labelArray2 == null || nArray == null || labelArray2.length != labelArray.length || nArray.length != labelArray.length) {
            throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
        }
        for (int i = 0; i < labelArray.length; ++i) {
            this.checkLabel(labelArray[i], true, "start label");
            this.checkLabel(labelArray2[i], true, "end label");
            CheckMethodAdapter.checkUnsignedShort(nArray[i], "Invalid variable index");
            int n3 = (Integer)this.labels.get(labelArray[i]);
            int n4 = (Integer)this.labels.get(labelArray2[i]);
            if (n4 >= n3) continue;
            throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
        }
        return super.visitLocalVariableAnnotation(n, typePath, labelArray, labelArray2, nArray, string, bl);
    }

    public void visitLineNumber(int n, Label label) {
        this.checkStartCode();
        this.checkEndCode();
        CheckMethodAdapter.checkUnsignedShort(n, "Invalid line number");
        this.checkLabel(label, true, "start label");
        super.visitLineNumber(n, label);
    }

    public void visitMaxs(int n, int n2) {
        this.checkStartCode();
        this.checkEndCode();
        this.endCode = true;
        for (Object object : this.usedLabels) {
            if (this.labels.get(object) != null) continue;
            throw new IllegalStateException("Undefined label used");
        }
        int n3 = 0;
        while (n3 < this.handlers.size()) {
            Object object;
            object = (Integer)this.labels.get(this.handlers.get(n3++));
            Integer n4 = (Integer)this.labels.get(this.handlers.get(n3++));
            if (object == null || n4 == null) {
                throw new IllegalStateException("Undefined try catch block labels");
            }
            if (n4 > (Integer)object) continue;
            throw new IllegalStateException("Emty try catch block handler range");
        }
        CheckMethodAdapter.checkUnsignedShort(n, "Invalid max stack");
        CheckMethodAdapter.checkUnsignedShort(n2, "Invalid max locals");
        super.visitMaxs(n, n2);
    }

    public void visitEnd() {
        this.checkEndMethod();
        this.endMethod = true;
        super.visitEnd();
    }

    void checkStartCode() {
        if (!this.startCode) {
            throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
        }
    }

    void checkEndCode() {
        if (this.endCode) {
            throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
        }
    }

    void checkEndMethod() {
        if (this.endMethod) {
            throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
        }
    }

    void checkFrameValue(Object object) {
        if (object == Opcodes.TOP || object == Opcodes.INTEGER || object == Opcodes.FLOAT || object == Opcodes.LONG || object == Opcodes.DOUBLE || object == Opcodes.NULL || object == Opcodes.UNINITIALIZED_THIS) {
            return;
        }
        if (object instanceof String) {
            CheckMethodAdapter.checkInternalName((String)object, "Invalid stack frame value");
            return;
        }
        if (!(object instanceof Label)) {
            throw new IllegalArgumentException("Invalid stack frame value: " + object);
        }
        this.usedLabels.add((Label)object);
    }

    static void checkOpcode(int n, int n2) {
        if (n < 0 || n > 199 || TYPE[n] != n2) {
            throw new IllegalArgumentException("Invalid opcode: " + n);
        }
    }

    static void checkSignedByte(int n, String string) {
        if (n < -128 || n > 127) {
            throw new IllegalArgumentException(string + " (must be a signed byte): " + n);
        }
    }

    static void checkSignedShort(int n, String string) {
        if (n < Short.MIN_VALUE || n > Short.MAX_VALUE) {
            throw new IllegalArgumentException(string + " (must be a signed short): " + n);
        }
    }

    static void checkUnsignedShort(int n, String string) {
        if (n < 0 || n > 65535) {
            throw new IllegalArgumentException(string + " (must be an unsigned short): " + n);
        }
    }

    static void checkConstant(Object object) {
        if (!(object instanceof Integer || object instanceof Float || object instanceof Long || object instanceof Double || object instanceof String)) {
            throw new IllegalArgumentException("Invalid constant: " + object);
        }
    }

    void checkLDCConstant(Object object) {
        if (object instanceof Type) {
            int n = ((Type)object).getSort();
            if (n != 10 && n != 9 && n != 11) {
                throw new IllegalArgumentException("Illegal LDC constant value");
            }
            if (n != 11 && (this.version & 0xFFFF) < 49) {
                throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
            }
            if (n == 11 && (this.version & 0xFFFF) < 51) {
                throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
            }
        } else if (object instanceof Handle) {
            if ((this.version & 0xFFFF) < 51) {
                throw new IllegalArgumentException("ldc of a handle requires at least version 1.7");
            }
            int n = ((Handle)object).getTag();
            if (n < 1 || n > 9) {
                throw new IllegalArgumentException("invalid handle tag " + n);
            }
        } else {
            CheckMethodAdapter.checkConstant(object);
        }
    }

    static void checkUnqualifiedName(int n, String string, String string2) {
        if ((n & 0xFFFF) < 49) {
            CheckMethodAdapter.checkIdentifier(string, string2);
        } else {
            for (int i = 0; i < string.length(); ++i) {
                if (".;[/".indexOf(string.charAt(i)) == -1) continue;
                throw new IllegalArgumentException("Invalid " + string2 + " (must be a valid unqualified name): " + string);
            }
        }
    }

    static void checkIdentifier(String string, String string2) {
        CheckMethodAdapter.checkIdentifier(string, 0, -1, string2);
    }

    static void checkIdentifier(String string, int n, int n2, String string2) {
        if (string == null || (n2 == -1 ? string.length() <= n : n2 <= n)) {
            throw new IllegalArgumentException("Invalid " + string2 + " (must not be null or empty)");
        }
        if (!Character.isJavaIdentifierStart(string.charAt(n))) {
            throw new IllegalArgumentException("Invalid " + string2 + " (must be a valid Java identifier): " + string);
        }
        int n3 = n2 == -1 ? string.length() : n2;
        for (int i = n + 1; i < n3; ++i) {
            if (Character.isJavaIdentifierPart(string.charAt(i))) continue;
            throw new IllegalArgumentException("Invalid " + string2 + " (must be a valid Java identifier): " + string);
        }
    }

    static void checkMethodIdentifier(int n, String string, String string2) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("Invalid " + string2 + " (must not be null or empty)");
        }
        if ((n & 0xFFFF) >= 49) {
            for (int i = 0; i < string.length(); ++i) {
                if (".;[/<>".indexOf(string.charAt(i)) == -1) continue;
                throw new IllegalArgumentException("Invalid " + string2 + " (must be a valid unqualified name): " + string);
            }
            return;
        }
        if (!Character.isJavaIdentifierStart(string.charAt(0))) {
            throw new IllegalArgumentException("Invalid " + string2 + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + string);
        }
        for (int i = 1; i < string.length(); ++i) {
            if (Character.isJavaIdentifierPart(string.charAt(i))) continue;
            throw new IllegalArgumentException("Invalid " + string2 + " (must be '<init>' or '<clinit>' or a valid Java identifier): " + string);
        }
    }

    static void checkInternalName(String string, String string2) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("Invalid " + string2 + " (must not be null or empty)");
        }
        if (string.charAt(0) == '[') {
            CheckMethodAdapter.checkDesc(string, false);
        } else {
            CheckMethodAdapter.checkInternalName(string, 0, -1, string2);
        }
    }

    static void checkInternalName(String string, int n, int n2, String string2) {
        int n3 = n2 == -1 ? string.length() : n2;
        try {
            int n4;
            int n5 = n;
            do {
                if ((n4 = string.indexOf(47, n5 + 1)) == -1 || n4 > n3) {
                    n4 = n3;
                }
                CheckMethodAdapter.checkIdentifier(string, n5, n4, null);
                n5 = n4 + 1;
            } while (n4 != n3);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Invalid " + string2 + " (must be a fully qualified class name in internal form): " + string);
        }
    }

    static void checkDesc(String string, boolean bl) {
        int n = CheckMethodAdapter.checkDesc(string, 0, bl);
        if (n != string.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + string);
        }
    }

    static int checkDesc(String string, int n, boolean bl) {
        if (string == null || n >= string.length()) {
            throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
        }
        switch (string.charAt(n)) {
            case 'V': {
                if (bl) {
                    return n + 1;
                }
                throw new IllegalArgumentException("Invalid descriptor: " + string);
            }
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
            case '[': {
                int n2;
                for (n2 = n + 1; n2 < string.length() && string.charAt(n2) == '['; ++n2) {
                }
                if (n2 < string.length()) {
                    return CheckMethodAdapter.checkDesc(string, n2, false);
                }
                throw new IllegalArgumentException("Invalid descriptor: " + string);
            }
            case 'L': {
                int n3 = string.indexOf(59, n);
                if (n3 == -1 || n3 - n < 2) {
                    throw new IllegalArgumentException("Invalid descriptor: " + string);
                }
                try {
                    CheckMethodAdapter.checkInternalName(string, n + 1, n3, null);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new IllegalArgumentException("Invalid descriptor: " + string);
                }
                return n3 + 1;
            }
        }
        throw new IllegalArgumentException("Invalid descriptor: " + string);
    }

    static void checkMethodDesc(String string) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
        }
        if (string.charAt(0) != '(' || string.length() < 3) {
            throw new IllegalArgumentException("Invalid descriptor: " + string);
        }
        int n = 1;
        if (string.charAt(n) != ')') {
            do {
                if (string.charAt(n) != 'V') continue;
                throw new IllegalArgumentException("Invalid descriptor: " + string);
            } while ((n = CheckMethodAdapter.checkDesc(string, n, false)) < string.length() && string.charAt(n) != ')');
        }
        if ((n = CheckMethodAdapter.checkDesc(string, n + 1, true)) != string.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + string);
        }
    }

    void checkLabel(Label label, boolean bl, String string) {
        if (label == null) {
            throw new IllegalArgumentException("Invalid " + string + " (must not be null)");
        }
        if (bl && this.labels.get(label) == null) {
            throw new IllegalArgumentException("Invalid " + string + " (must be visited first)");
        }
    }

    private static void checkNonDebugLabel(Label label) {
        Field field = CheckMethodAdapter.getLabelStatusField();
        int n = 0;
        try {
            n = field == null ? 0 : (Integer)field.get(label);
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new Error("Internal error");
        }
        if ((n & 1) != 0) {
            throw new IllegalArgumentException("Labels used for debug info cannot be reused for control flow");
        }
    }

    private static Field getLabelStatusField() {
        if (labelStatusField == null && (labelStatusField = CheckMethodAdapter.getLabelField("a")) == null) {
            labelStatusField = CheckMethodAdapter.getLabelField("status");
        }
        return labelStatusField;
    }

    private static Field getLabelField(String string) {
        try {
            Field field = Label.class.getDeclaredField(string);
            field.setAccessible(true);
            return field;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return null;
        }
    }

    static {
        String string = "BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA";
        TYPE = new int[string.length()];
        for (int i = 0; i < TYPE.length; ++i) {
            CheckMethodAdapter.TYPE[i] = string.charAt(i) - 65 - 1;
        }
    }
}

