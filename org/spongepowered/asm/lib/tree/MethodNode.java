/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.I;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.InsnNode;
import org.spongepowered.asm.lib.tree.IntInsnNode;
import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.LocalVariableAnnotationNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.LookupSwitchInsnNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MultiANewArrayInsnNode;
import org.spongepowered.asm.lib.tree.ParameterNode;
import org.spongepowered.asm.lib.tree.TableSwitchInsnNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.TypeAnnotationNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class MethodNode
extends MethodVisitor {
    public int access;
    public String name;
    public String desc;
    public String signature;
    public List exceptions;
    public List parameters;
    public List visibleAnnotations;
    public List invisibleAnnotations;
    public List visibleTypeAnnotations;
    public List invisibleTypeAnnotations;
    public List attrs;
    public Object annotationDefault;
    public List[] visibleParameterAnnotations;
    public List[] invisibleParameterAnnotations;
    public InsnList instructions;
    public List tryCatchBlocks;
    public int maxStack;
    public int maxLocals;
    public List localVariables;
    public List visibleLocalVariableAnnotations;
    public List invisibleLocalVariableAnnotations;
    private boolean visited;

    public MethodNode() {
        this(327680);
        if (this.getClass() != MethodNode.class) {
            throw new IllegalStateException();
        }
    }

    public MethodNode(int n) {
        super(n);
        this.instructions = new InsnList();
    }

    public MethodNode(int n, String string, String string2, String string3, String[] stringArray) {
        this(327680, n, string, string2, string3, stringArray);
        if (this.getClass() != MethodNode.class) {
            throw new IllegalStateException();
        }
    }

    public MethodNode(int n, int n2, String string, String string2, String string3, String[] stringArray) {
        super(n);
        boolean bl;
        this.access = n2;
        this.name = string;
        this.desc = string2;
        this.signature = string3;
        this.exceptions = new ArrayList(stringArray == null ? 0 : stringArray.length);
        boolean bl2 = bl = (n2 & 0x400) != 0;
        if (!bl) {
            this.localVariables = new ArrayList(5);
        }
        this.tryCatchBlocks = new ArrayList();
        if (stringArray != null) {
            this.exceptions.addAll(Arrays.asList(stringArray));
        }
        this.instructions = new InsnList();
    }

    public void visitParameter(String string, int n) {
        if (this.parameters == null) {
            this.parameters = new ArrayList(5);
        }
        this.parameters.add(new ParameterNode(string, n));
    }

    public AnnotationVisitor visitAnnotationDefault() {
        return new AnnotationNode(new I(this, 0));
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        AnnotationNode annotationNode = new AnnotationNode(string);
        if (bl) {
            if (this.visibleAnnotations == null) {
                this.visibleAnnotations = new ArrayList(1);
            }
            this.visibleAnnotations.add(annotationNode);
        } else {
            if (this.invisibleAnnotations == null) {
                this.invisibleAnnotations = new ArrayList(1);
            }
            this.invisibleAnnotations.add(annotationNode);
        }
        return annotationNode;
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(n, typePath, string);
        if (bl) {
            if (this.visibleTypeAnnotations == null) {
                this.visibleTypeAnnotations = new ArrayList(1);
            }
            this.visibleTypeAnnotations.add(typeAnnotationNode);
        } else {
            if (this.invisibleTypeAnnotations == null) {
                this.invisibleTypeAnnotations = new ArrayList(1);
            }
            this.invisibleTypeAnnotations.add(typeAnnotationNode);
        }
        return typeAnnotationNode;
    }

    public AnnotationVisitor visitParameterAnnotation(int n, String string, boolean bl) {
        AnnotationNode annotationNode = new AnnotationNode(string);
        if (bl) {
            if (this.visibleParameterAnnotations == null) {
                int n2 = Type.getArgumentTypes(this.desc).length;
                this.visibleParameterAnnotations = new List[n2];
            }
            if (this.visibleParameterAnnotations[n] == null) {
                this.visibleParameterAnnotations[n] = new ArrayList(1);
            }
            this.visibleParameterAnnotations[n].add(annotationNode);
        } else {
            if (this.invisibleParameterAnnotations == null) {
                int n3 = Type.getArgumentTypes(this.desc).length;
                this.invisibleParameterAnnotations = new List[n3];
            }
            if (this.invisibleParameterAnnotations[n] == null) {
                this.invisibleParameterAnnotations[n] = new ArrayList(1);
            }
            this.invisibleParameterAnnotations[n].add(annotationNode);
        }
        return annotationNode;
    }

    public void visitAttribute(Attribute attribute) {
        if (this.attrs == null) {
            this.attrs = new ArrayList(1);
        }
        this.attrs.add(attribute);
    }

    public void visitCode() {
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        this.instructions.add(new FrameNode(n, n2, objectArray == null ? null : this.getLabelNodes(objectArray), n3, objectArray2 == null ? null : this.getLabelNodes(objectArray2)));
    }

    public void visitInsn(int n) {
        this.instructions.add(new InsnNode(n));
    }

    public void visitIntInsn(int n, int n2) {
        this.instructions.add(new IntInsnNode(n, n2));
    }

    public void visitVarInsn(int n, int n2) {
        this.instructions.add(new VarInsnNode(n, n2));
    }

    public void visitTypeInsn(int n, String string) {
        this.instructions.add(new TypeInsnNode(n, string));
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        this.instructions.add(new FieldInsnNode(n, string, string2, string3));
    }

    @Deprecated
    public void visitMethodInsn(int n, String string, String string2, String string3) {
        if (this.api >= 327680) {
            super.visitMethodInsn(n, string, string2, string3);
            return;
        }
        this.instructions.add(new MethodInsnNode(n, string, string2, string3));
    }

    public void visitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        if (this.api < 327680) {
            super.visitMethodInsn(n, string, string2, string3, bl);
            return;
        }
        this.instructions.add(new MethodInsnNode(n, string, string2, string3, bl));
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        this.instructions.add(new InvokeDynamicInsnNode(string, string2, handle, objectArray));
    }

    public void visitJumpInsn(int n, Label label) {
        this.instructions.add(new JumpInsnNode(n, this.getLabelNode(label)));
    }

    public void visitLabel(Label label) {
        this.instructions.add(this.getLabelNode(label));
    }

    public void visitLdcInsn(Object object) {
        this.instructions.add(new LdcInsnNode(object));
    }

    public void visitIincInsn(int n, int n2) {
        this.instructions.add(new IincInsnNode(n, n2));
    }

    public void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        this.instructions.add(new TableSwitchInsnNode(n, n2, this.getLabelNode(label), this.getLabelNodes(labelArray)));
    }

    public void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        this.instructions.add(new LookupSwitchInsnNode(this.getLabelNode(label), nArray, this.getLabelNodes(labelArray)));
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        this.instructions.add(new MultiANewArrayInsnNode(string, n));
    }

    public AnnotationVisitor visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        AbstractInsnNode abstractInsnNode = this.instructions.getLast();
        while (abstractInsnNode.getOpcode() == -1) {
            abstractInsnNode = abstractInsnNode.getPrevious();
        }
        TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(n, typePath, string);
        if (bl) {
            if (abstractInsnNode.visibleTypeAnnotations == null) {
                abstractInsnNode.visibleTypeAnnotations = new ArrayList(1);
            }
            abstractInsnNode.visibleTypeAnnotations.add(typeAnnotationNode);
        } else {
            if (abstractInsnNode.invisibleTypeAnnotations == null) {
                abstractInsnNode.invisibleTypeAnnotations = new ArrayList(1);
            }
            abstractInsnNode.invisibleTypeAnnotations.add(typeAnnotationNode);
        }
        return typeAnnotationNode;
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        this.tryCatchBlocks.add(new TryCatchBlockNode(this.getLabelNode(label), this.getLabelNode(label2), this.getLabelNode(label3), string));
    }

    public AnnotationVisitor visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        TryCatchBlockNode tryCatchBlockNode = (TryCatchBlockNode)this.tryCatchBlocks.get((n & 0xFFFF00) >> 8);
        TypeAnnotationNode typeAnnotationNode = new TypeAnnotationNode(n, typePath, string);
        if (bl) {
            if (tryCatchBlockNode.visibleTypeAnnotations == null) {
                tryCatchBlockNode.visibleTypeAnnotations = new ArrayList(1);
            }
            tryCatchBlockNode.visibleTypeAnnotations.add(typeAnnotationNode);
        } else {
            if (tryCatchBlockNode.invisibleTypeAnnotations == null) {
                tryCatchBlockNode.invisibleTypeAnnotations = new ArrayList(1);
            }
            tryCatchBlockNode.invisibleTypeAnnotations.add(typeAnnotationNode);
        }
        return typeAnnotationNode;
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        this.localVariables.add(new LocalVariableNode(string, string2, string3, this.getLabelNode(label), this.getLabelNode(label2), n));
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        LocalVariableAnnotationNode localVariableAnnotationNode = new LocalVariableAnnotationNode(n, typePath, this.getLabelNodes(labelArray), this.getLabelNodes(labelArray2), nArray, string);
        if (bl) {
            if (this.visibleLocalVariableAnnotations == null) {
                this.visibleLocalVariableAnnotations = new ArrayList(1);
            }
            this.visibleLocalVariableAnnotations.add(localVariableAnnotationNode);
        } else {
            if (this.invisibleLocalVariableAnnotations == null) {
                this.invisibleLocalVariableAnnotations = new ArrayList(1);
            }
            this.invisibleLocalVariableAnnotations.add(localVariableAnnotationNode);
        }
        return localVariableAnnotationNode;
    }

    public void visitLineNumber(int n, Label label) {
        this.instructions.add(new LineNumberNode(n, this.getLabelNode(label)));
    }

    public void visitMaxs(int n, int n2) {
        this.maxStack = n;
        this.maxLocals = n2;
    }

    public void visitEnd() {
    }

    protected LabelNode getLabelNode(Label label) {
        if (!(label.info instanceof LabelNode)) {
            label.info = new LabelNode();
        }
        return (LabelNode)label.info;
    }

    private LabelNode[] getLabelNodes(Label[] labelArray) {
        LabelNode[] labelNodeArray = new LabelNode[labelArray.length];
        for (int i = 0; i < labelArray.length; ++i) {
            labelNodeArray[i] = this.getLabelNode(labelArray[i]);
        }
        return labelNodeArray;
    }

    private Object[] getLabelNodes(Object[] objectArray) {
        Object[] objectArray2 = new Object[objectArray.length];
        for (int i = 0; i < objectArray.length; ++i) {
            Object object = objectArray[i];
            if (object instanceof Label) {
                object = this.getLabelNode((Label)object);
            }
            objectArray2[i] = object;
        }
        return objectArray2;
    }

    public void check(int n) {
        if (n == 262144) {
            Object object;
            int n2;
            if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            int n3 = this.tryCatchBlocks == null ? 0 : this.tryCatchBlocks.size();
            for (n2 = 0; n2 < n3; ++n2) {
                object = (TryCatchBlockNode)this.tryCatchBlocks.get(n2);
                if (((TryCatchBlockNode)object).visibleTypeAnnotations != null && ((TryCatchBlockNode)object).visibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (((TryCatchBlockNode)object).invisibleTypeAnnotations == null || ((TryCatchBlockNode)object).invisibleTypeAnnotations.size() <= 0) continue;
                throw new RuntimeException();
            }
            for (n2 = 0; n2 < this.instructions.size(); ++n2) {
                boolean bl;
                object = this.instructions.get(n2);
                if (((AbstractInsnNode)object).visibleTypeAnnotations != null && ((AbstractInsnNode)object).visibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (((AbstractInsnNode)object).invisibleTypeAnnotations != null && ((AbstractInsnNode)object).invisibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (!(object instanceof MethodInsnNode) || (bl = ((MethodInsnNode)object).itf) == (((AbstractInsnNode)object).opcode == 185)) continue;
                throw new RuntimeException();
            }
            if (this.visibleLocalVariableAnnotations != null && this.visibleLocalVariableAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleLocalVariableAnnotations != null && this.invisibleLocalVariableAnnotations.size() > 0) {
                throw new RuntimeException();
            }
        }
    }

    public void accept(ClassVisitor classVisitor) {
        String[] stringArray = new String[this.exceptions.size()];
        this.exceptions.toArray(stringArray);
        MethodVisitor methodVisitor = classVisitor.visitMethod(this.access, this.name, this.desc, this.signature, stringArray);
        if (methodVisitor != null) {
            this.accept(methodVisitor);
        }
    }

    public void accept(MethodVisitor methodVisitor) {
        AnnotationNode annotationNode;
        int n;
        Object object;
        int n2;
        int n3 = this.parameters == null ? 0 : this.parameters.size();
        for (n2 = 0; n2 < n3; ++n2) {
            object = (ParameterNode)this.parameters.get(n2);
            methodVisitor.visitParameter(((ParameterNode)object).name, ((ParameterNode)object).access);
        }
        if (this.annotationDefault != null) {
            object = methodVisitor.visitAnnotationDefault();
            AnnotationNode.accept((AnnotationVisitor)object, null, this.annotationDefault);
            if (object != null) {
                ((AnnotationVisitor)object).visitEnd();
            }
        }
        n3 = this.visibleAnnotations == null ? 0 : this.visibleAnnotations.size();
        for (n2 = 0; n2 < n3; ++n2) {
            object = (AnnotationNode)this.visibleAnnotations.get(n2);
            ((AnnotationNode)object).accept(methodVisitor.visitAnnotation(((AnnotationNode)object).desc, true));
        }
        n3 = this.invisibleAnnotations == null ? 0 : this.invisibleAnnotations.size();
        for (n2 = 0; n2 < n3; ++n2) {
            object = (AnnotationNode)this.invisibleAnnotations.get(n2);
            ((AnnotationNode)object).accept(methodVisitor.visitAnnotation(((AnnotationNode)object).desc, false));
        }
        n3 = this.visibleTypeAnnotations == null ? 0 : this.visibleTypeAnnotations.size();
        for (n2 = 0; n2 < n3; ++n2) {
            object = (TypeAnnotationNode)this.visibleTypeAnnotations.get(n2);
            ((AnnotationNode)object).accept(methodVisitor.visitTypeAnnotation(((TypeAnnotationNode)object).typeRef, ((TypeAnnotationNode)object).typePath, ((TypeAnnotationNode)object).desc, true));
        }
        n3 = this.invisibleTypeAnnotations == null ? 0 : this.invisibleTypeAnnotations.size();
        for (n2 = 0; n2 < n3; ++n2) {
            object = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(n2);
            ((AnnotationNode)object).accept(methodVisitor.visitTypeAnnotation(((TypeAnnotationNode)object).typeRef, ((TypeAnnotationNode)object).typePath, ((TypeAnnotationNode)object).desc, false));
        }
        n3 = this.visibleParameterAnnotations == null ? 0 : this.visibleParameterAnnotations.length;
        for (n2 = 0; n2 < n3; ++n2) {
            object = this.visibleParameterAnnotations[n2];
            if (object == null) continue;
            for (n = 0; n < object.size(); ++n) {
                annotationNode = (AnnotationNode)object.get(n);
                annotationNode.accept(methodVisitor.visitParameterAnnotation(n2, annotationNode.desc, true));
            }
        }
        n3 = this.invisibleParameterAnnotations == null ? 0 : this.invisibleParameterAnnotations.length;
        for (n2 = 0; n2 < n3; ++n2) {
            object = this.invisibleParameterAnnotations[n2];
            if (object == null) continue;
            for (n = 0; n < object.size(); ++n) {
                annotationNode = (AnnotationNode)object.get(n);
                annotationNode.accept(methodVisitor.visitParameterAnnotation(n2, annotationNode.desc, false));
            }
        }
        if (this.visited) {
            this.instructions.resetLabels();
        }
        n3 = this.attrs == null ? 0 : this.attrs.size();
        for (n2 = 0; n2 < n3; ++n2) {
            methodVisitor.visitAttribute((Attribute)this.attrs.get(n2));
        }
        if (this.instructions.size() > 0) {
            methodVisitor.visitCode();
            n3 = this.tryCatchBlocks == null ? 0 : this.tryCatchBlocks.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((TryCatchBlockNode)this.tryCatchBlocks.get(n2)).updateIndex(n2);
                ((TryCatchBlockNode)this.tryCatchBlocks.get(n2)).accept(methodVisitor);
            }
            this.instructions.accept(methodVisitor);
            n3 = this.localVariables == null ? 0 : this.localVariables.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((LocalVariableNode)this.localVariables.get(n2)).accept(methodVisitor);
            }
            n3 = this.visibleLocalVariableAnnotations == null ? 0 : this.visibleLocalVariableAnnotations.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((LocalVariableAnnotationNode)this.visibleLocalVariableAnnotations.get(n2)).accept(methodVisitor, true);
            }
            n3 = this.invisibleLocalVariableAnnotations == null ? 0 : this.invisibleLocalVariableAnnotations.size();
            for (n2 = 0; n2 < n3; ++n2) {
                ((LocalVariableAnnotationNode)this.invisibleLocalVariableAnnotations.get(n2)).accept(methodVisitor, false);
            }
            methodVisitor.visitMaxs(this.maxStack, this.maxLocals);
            this.visited = true;
        }
        methodVisitor.visitEnd();
    }
}

