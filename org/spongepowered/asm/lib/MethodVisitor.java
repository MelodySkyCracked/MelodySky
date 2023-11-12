/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.TypePath;

public abstract class MethodVisitor {
    protected final int api;
    protected MethodVisitor mv;

    public MethodVisitor(int n) {
        this(n, null);
    }

    public MethodVisitor(int n, MethodVisitor methodVisitor) {
        if (n != 262144 && n != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = n;
        this.mv = methodVisitor;
    }

    public void visitParameter(String string, int n) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            this.mv.visitParameter(string, n);
        }
    }

    public AnnotationVisitor visitAnnotationDefault() {
        if (this.mv != null) {
            return this.mv.visitAnnotationDefault();
        }
        return null;
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        if (this.mv != null) {
            return this.mv.visitAnnotation(string, bl);
        }
        return null;
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitTypeAnnotation(n, typePath, string, bl);
        }
        return null;
    }

    public AnnotationVisitor visitParameterAnnotation(int n, String string, boolean bl) {
        if (this.mv != null) {
            return this.mv.visitParameterAnnotation(n, string, bl);
        }
        return null;
    }

    public void visitAttribute(Attribute attribute) {
        if (this.mv != null) {
            this.mv.visitAttribute(attribute);
        }
    }

    public void visitCode() {
        if (this.mv != null) {
            this.mv.visitCode();
        }
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        if (this.mv != null) {
            this.mv.visitFrame(n, n2, objectArray, n3, objectArray2);
        }
    }

    public void visitInsn(int n) {
        if (this.mv != null) {
            this.mv.visitInsn(n);
        }
    }

    public void visitIntInsn(int n, int n2) {
        if (this.mv != null) {
            this.mv.visitIntInsn(n, n2);
        }
    }

    public void visitVarInsn(int n, int n2) {
        if (this.mv != null) {
            this.mv.visitVarInsn(n, n2);
        }
    }

    public void visitTypeInsn(int n, String string) {
        if (this.mv != null) {
            this.mv.visitTypeInsn(n, string);
        }
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        if (this.mv != null) {
            this.mv.visitFieldInsn(n, string, string2, string3);
        }
    }

    @Deprecated
    public void visitMethodInsn(int n, String string, String string2, String string3) {
        if (this.api >= 327680) {
            boolean bl = n == 185;
            this.visitMethodInsn(n, string, string2, string3, bl);
            return;
        }
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, string, string2, string3);
        }
    }

    public void visitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        if (this.api < 327680) {
            if (bl != (n == 185)) {
                throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
            }
            this.visitMethodInsn(n, string, string2, string3);
            return;
        }
        if (this.mv != null) {
            this.mv.visitMethodInsn(n, string, string2, string3, bl);
        }
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        if (this.mv != null) {
            this.mv.visitInvokeDynamicInsn(string, string2, handle, objectArray);
        }
    }

    public void visitJumpInsn(int n, Label label) {
        if (this.mv != null) {
            this.mv.visitJumpInsn(n, label);
        }
    }

    public void visitLabel(Label label) {
        if (this.mv != null) {
            this.mv.visitLabel(label);
        }
    }

    public void visitLdcInsn(Object object) {
        if (this.mv != null) {
            this.mv.visitLdcInsn(object);
        }
    }

    public void visitIincInsn(int n, int n2) {
        if (this.mv != null) {
            this.mv.visitIincInsn(n, n2);
        }
    }

    public void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        if (this.mv != null) {
            this.mv.visitTableSwitchInsn(n, n2, label, labelArray);
        }
    }

    public void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        if (this.mv != null) {
            this.mv.visitLookupSwitchInsn(label, nArray, labelArray);
        }
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        if (this.mv != null) {
            this.mv.visitMultiANewArrayInsn(string, n);
        }
    }

    public AnnotationVisitor visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitInsnAnnotation(n, typePath, string, bl);
        }
        return null;
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        if (this.mv != null) {
            this.mv.visitTryCatchBlock(label, label2, label3, string);
        }
    }

    public AnnotationVisitor visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitTryCatchAnnotation(n, typePath, string, bl);
        }
        return null;
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        if (this.mv != null) {
            this.mv.visitLocalVariable(string, string2, string3, label, label2, n);
        }
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitLocalVariableAnnotation(n, typePath, labelArray, labelArray2, nArray, string, bl);
        }
        return null;
    }

    public void visitLineNumber(int n, Label label) {
        if (this.mv != null) {
            this.mv.visitLineNumber(n, label);
        }
    }

    public void visitMaxs(int n, int n2) {
        if (this.mv != null) {
            this.mv.visitMaxs(n, n2);
        }
    }

    public void visitEnd() {
        if (this.mv != null) {
            this.mv.visitEnd();
        }
    }
}

