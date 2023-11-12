/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.lib;

import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.AnnotationWriter;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ByteVector;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.Edge;
import org.spongepowered.asm.lib.Frame;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Handler;
import org.spongepowered.asm.lib.Item;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

class MethodWriter
extends MethodVisitor {
    static final int ACC_CONSTRUCTOR = 524288;
    static final int SAME_FRAME = 0;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
    static final int RESERVED = 128;
    static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
    static final int CHOP_FRAME = 248;
    static final int SAME_FRAME_EXTENDED = 251;
    static final int APPEND_FRAME = 252;
    static final int FULL_FRAME = 255;
    private static final int FRAMES = 0;
    private static final int MAXS = 1;
    private static final int NOTHING = 2;
    final ClassWriter cw;
    private int access;
    private final int name;
    private final int desc;
    private final String descriptor;
    String signature;
    int classReaderOffset;
    int classReaderLength;
    int exceptionCount;
    int[] exceptions;
    private ByteVector annd;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private AnnotationWriter[] panns;
    private AnnotationWriter[] ipanns;
    private int synthetics;
    private Attribute attrs;
    private ByteVector code = new ByteVector();
    private int maxStack;
    private int maxLocals;
    private int currentLocals;
    private int frameCount;
    private ByteVector stackMap;
    private int previousFrameOffset;
    private int[] previousFrame;
    private int[] frame;
    private int handlerCount;
    private Handler firstHandler;
    private Handler lastHandler;
    private int methodParametersCount;
    private ByteVector methodParameters;
    private int localVarCount;
    private ByteVector localVar;
    private int localVarTypeCount;
    private ByteVector localVarType;
    private int lineNumberCount;
    private ByteVector lineNumber;
    private int lastCodeOffset;
    private AnnotationWriter ctanns;
    private AnnotationWriter ictanns;
    private Attribute cattrs;
    private boolean resize;
    private int subroutines;
    private final int compute;
    private Label labels;
    private Label previousBlock;
    private Label currentBlock;
    private int stackSize;
    private int maxStackSize;

    MethodWriter(ClassWriter classWriter, int n, String string, String string2, String string3, String[] stringArray, boolean bl, boolean bl2) {
        super(327680);
        int n2;
        if (classWriter.firstMethod == null) {
            classWriter.firstMethod = this;
        } else {
            classWriter.lastMethod.mv = this;
        }
        classWriter.lastMethod = this;
        this.cw = classWriter;
        this.access = n;
        if ("<init>".equals(string)) {
            this.access |= 0x80000;
        }
        this.name = classWriter.newUTF8(string);
        this.desc = classWriter.newUTF8(string2);
        this.descriptor = string2;
        this.signature = string3;
        if (stringArray != null && stringArray.length > 0) {
            this.exceptionCount = stringArray.length;
            this.exceptions = new int[this.exceptionCount];
            for (n2 = 0; n2 < this.exceptionCount; ++n2) {
                this.exceptions[n2] = classWriter.newClass(stringArray[n2]);
            }
        }
        int n3 = bl2 ? 0 : (this.compute = bl ? 1 : 2);
        if (bl || bl2) {
            n2 = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
            if ((n & 8) != 0) {
                --n2;
            }
            this.maxLocals = n2;
            this.currentLocals = n2;
            this.labels = new Label();
            this.labels.status |= 8;
            this.visitLabel(this.labels);
        }
    }

    public void visitParameter(String string, int n) {
        if (this.methodParameters == null) {
            this.methodParameters = new ByteVector();
        }
        ++this.methodParametersCount;
        this.methodParameters.putShort(string == null ? 0 : this.cw.newUTF8(string)).putShort(n);
    }

    public AnnotationVisitor visitAnnotationDefault() {
        this.annd = new ByteVector();
        return new AnnotationWriter(this.cw, false, this.annd, null, 0);
    }

    public AnnotationVisitor visitAnnotation(String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.cw.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
        if (bl) {
            annotationWriter.next = this.anns;
            this.anns = annotationWriter;
        } else {
            annotationWriter.next = this.ianns;
            this.ianns = annotationWriter;
        }
        return annotationWriter;
    }

    public AnnotationVisitor visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.putTarget(n, typePath, byteVector);
        byteVector.putShort(this.cw.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (bl) {
            annotationWriter.next = this.tanns;
            this.tanns = annotationWriter;
        } else {
            annotationWriter.next = this.itanns;
            this.itanns = annotationWriter;
        }
        return annotationWriter;
    }

    public AnnotationVisitor visitParameterAnnotation(int n, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        if ("Ljava/lang/Synthetic;".equals(string)) {
            this.synthetics = Math.max(this.synthetics, n + 1);
            return new AnnotationWriter(this.cw, false, byteVector, null, 0);
        }
        byteVector.putShort(this.cw.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, 2);
        if (bl) {
            if (this.panns == null) {
                this.panns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            annotationWriter.next = this.panns[n];
            this.panns[n] = annotationWriter;
        } else {
            if (this.ipanns == null) {
                this.ipanns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }
            annotationWriter.next = this.ipanns[n];
            this.ipanns[n] = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitAttribute(Attribute attribute) {
        if (attribute.isCodeAttribute()) {
            attribute.next = this.cattrs;
            this.cattrs = attribute;
        } else {
            attribute.next = this.attrs;
            this.attrs = attribute;
        }
    }

    public void visitCode() {
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        if (this.compute == 0) {
            return;
        }
        if (n == -1) {
            int n4;
            if (this.previousFrame == null) {
                this.visitImplicitFirstFrame();
            }
            this.currentLocals = n2;
            int n5 = this.startFrame(this.code.length, n2, n3);
            for (n4 = 0; n4 < n2; ++n4) {
                this.frame[n5++] = objectArray[n4] instanceof String ? 0x1700000 | this.cw.addType((String)objectArray[n4]) : (objectArray[n4] instanceof Integer ? (Integer)objectArray[n4] : 0x1800000 | this.cw.addUninitializedType("", ((Label)objectArray[n4]).position));
            }
            for (n4 = 0; n4 < n3; ++n4) {
                this.frame[n5++] = objectArray2[n4] instanceof String ? 0x1700000 | this.cw.addType((String)objectArray2[n4]) : (objectArray2[n4] instanceof Integer ? (Integer)objectArray2[n4] : 0x1800000 | this.cw.addUninitializedType("", ((Label)objectArray2[n4]).position));
            }
            this.endFrame();
        } else {
            int n6;
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
                n6 = this.code.length;
            } else {
                n6 = this.code.length - this.previousFrameOffset - 1;
                if (n6 < 0) {
                    if (n == 3) {
                        return;
                    }
                    throw new IllegalStateException();
                }
            }
            switch (n) {
                case 0: {
                    int n7;
                    this.currentLocals = n2;
                    this.stackMap.putByte(255).putShort(n6).putShort(n2);
                    for (n7 = 0; n7 < n2; ++n7) {
                        this.writeFrameType(objectArray[n7]);
                    }
                    this.stackMap.putShort(n3);
                    for (n7 = 0; n7 < n3; ++n7) {
                        this.writeFrameType(objectArray2[n7]);
                    }
                    break;
                }
                case 1: {
                    this.currentLocals += n2;
                    this.stackMap.putByte(251 + n2).putShort(n6);
                    for (int i = 0; i < n2; ++i) {
                        this.writeFrameType(objectArray[i]);
                    }
                    break;
                }
                case 2: {
                    this.currentLocals -= n2;
                    this.stackMap.putByte(251 - n2).putShort(n6);
                    break;
                }
                case 3: {
                    if (n6 < 64) {
                        this.stackMap.putByte(n6);
                        break;
                    }
                    this.stackMap.putByte(251).putShort(n6);
                    break;
                }
                case 4: {
                    if (n6 < 64) {
                        this.stackMap.putByte(64 + n6);
                    } else {
                        this.stackMap.putByte(247).putShort(n6);
                    }
                    this.writeFrameType(objectArray2[0]);
                }
            }
            this.previousFrameOffset = this.code.length;
            ++this.frameCount;
        }
        this.maxStack = Math.max(this.maxStack, n3);
        this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
    }

    public void visitInsn(int n) {
        this.lastCodeOffset = this.code.length;
        this.code.putByte(n);
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(n, 0, null, null);
            } else {
                int n2 = this.stackSize + Frame.SIZE[n];
                if (n2 > this.maxStackSize) {
                    this.maxStackSize = n2;
                }
                this.stackSize = n2;
            }
            if (n >= 172 && n <= 177 || n == 191) {
                this.noSuccessor();
            }
        }
    }

    public void visitIntInsn(int n, int n2) {
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(n, n2, null, null);
            } else if (n != 188) {
                int n3 = this.stackSize + 1;
                if (n3 > this.maxStackSize) {
                    this.maxStackSize = n3;
                }
                this.stackSize = n3;
            }
        }
        if (n == 17) {
            this.code.put12(n, n2);
        } else {
            this.code.put11(n, n2);
        }
    }

    public void visitVarInsn(int n, int n2) {
        int n3;
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(n, n2, null, null);
            } else if (n == 169) {
                this.currentBlock.status |= 0x100;
                this.currentBlock.inputStackTop = this.stackSize;
                this.noSuccessor();
            } else {
                n3 = this.stackSize + Frame.SIZE[n];
                if (n3 > this.maxStackSize) {
                    this.maxStackSize = n3;
                }
                this.stackSize = n3;
            }
        }
        if (this.compute != 2 && (n3 = n == 22 || n == 24 || n == 55 || n == 57 ? n2 + 2 : n2 + 1) > this.maxLocals) {
            this.maxLocals = n3;
        }
        if (n2 < 4 && n != 169) {
            n3 = n < 54 ? 26 + (n - 21 << 2) + n2 : 59 + (n - 54 << 2) + n2;
            this.code.putByte(n3);
        } else if (n2 >= 256) {
            this.code.putByte(196).put12(n, n2);
        } else {
            this.code.put11(n, n2);
        }
        if (n >= 54 && this.compute == 0 && this.handlerCount > 0) {
            this.visitLabel(new Label());
        }
    }

    public void visitTypeInsn(int n, String string) {
        this.lastCodeOffset = this.code.length;
        Item item = this.cw.newClassItem(string);
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(n, this.code.length, this.cw, item);
            } else if (n == 187) {
                int n2 = this.stackSize + 1;
                if (n2 > this.maxStackSize) {
                    this.maxStackSize = n2;
                }
                this.stackSize = n2;
            }
        }
        this.code.put12(n, item.index);
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        this.lastCodeOffset = this.code.length;
        Item item = this.cw.newFieldItem(string, string2, string3);
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(n, 0, this.cw, item);
            } else {
                int n2;
                char c = string3.charAt(0);
                switch (n) {
                    case 178: {
                        n2 = this.stackSize + (c == 'D' || c == 'J' ? 2 : 1);
                        break;
                    }
                    case 179: {
                        n2 = this.stackSize + (c == 'D' || c == 'J' ? -2 : -1);
                        break;
                    }
                    case 180: {
                        n2 = this.stackSize + (c == 'D' || c == 'J' ? 1 : 0);
                        break;
                    }
                    default: {
                        n2 = this.stackSize + (c == 'D' || c == 'J' ? -3 : -2);
                    }
                }
                if (n2 > this.maxStackSize) {
                    this.maxStackSize = n2;
                }
                this.stackSize = n2;
            }
        }
        this.code.put12(n, item.index);
    }

    public void visitMethodInsn(int n, String string, String string2, String string3, boolean bl) {
        this.lastCodeOffset = this.code.length;
        Item item = this.cw.newMethodItem(string, string2, string3, bl);
        int n2 = item.intVal;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(n, 0, this.cw, item);
            } else {
                int n3;
                if (n2 == 0) {
                    item.intVal = n2 = Type.getArgumentsAndReturnSizes(string3);
                }
                if ((n3 = n == 184 ? this.stackSize - (n2 >> 2) + (n2 & 3) + 1 : this.stackSize - (n2 >> 2) + (n2 & 3)) > this.maxStackSize) {
                    this.maxStackSize = n3;
                }
                this.stackSize = n3;
            }
        }
        if (n == 185) {
            if (n2 == 0) {
                item.intVal = n2 = Type.getArgumentsAndReturnSizes(string3);
            }
            this.code.put12(185, item.index).put11(n2 >> 2, 0);
        } else {
            this.code.put12(n, item.index);
        }
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        this.lastCodeOffset = this.code.length;
        Item item = this.cw.newInvokeDynamicItem(string, string2, handle, objectArray);
        int n = item.intVal;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(186, 0, this.cw, item);
            } else {
                int n2;
                if (n == 0) {
                    item.intVal = n = Type.getArgumentsAndReturnSizes(string2);
                }
                if ((n2 = this.stackSize - (n >> 2) + (n & 3) + 1) > this.maxStackSize) {
                    this.maxStackSize = n2;
                }
                this.stackSize = n2;
            }
        }
        this.code.put12(186, item.index);
        this.code.putShort(0);
    }

    public void visitJumpInsn(int n, Label label) {
        this.lastCodeOffset = this.code.length;
        Label label2 = null;
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(n, 0, null, null);
                label.getFirst().status |= 0x10;
                this.addSuccessor(0, label);
                if (n != 167) {
                    label2 = new Label();
                }
            } else if (n == 168) {
                if ((label.status & 0x200) == 0) {
                    label.status |= 0x200;
                    ++this.subroutines;
                }
                this.currentBlock.status |= 0x80;
                this.addSuccessor(this.stackSize + 1, label);
                label2 = new Label();
            } else {
                this.stackSize += Frame.SIZE[n];
                this.addSuccessor(this.stackSize, label);
            }
        }
        if ((label.status & 2) != 0 && label.position - this.code.length < Short.MIN_VALUE) {
            if (n == 167) {
                this.code.putByte(200);
            } else if (n == 168) {
                this.code.putByte(201);
            } else {
                if (label2 != null) {
                    label2.status |= 0x10;
                }
                this.code.putByte(n <= 166 ? (n + 1 ^ 1) - 1 : n ^ 1);
                this.code.putShort(8);
                this.code.putByte(200);
            }
            label.put(this, this.code, this.code.length - 1, true);
        } else {
            this.code.putByte(n);
            label.put(this, this.code, this.code.length - 1, false);
        }
        if (this.currentBlock != null) {
            if (label2 != null) {
                this.visitLabel(label2);
            }
            if (n == 167) {
                this.noSuccessor();
            }
        }
    }

    public void visitLabel(Label label) {
        this.resize |= label.resolve(this, this.code.length, this.code.data);
        if ((label.status & 1) != 0) {
            return;
        }
        if (this.compute == 0) {
            if (this.currentBlock != null) {
                if (label.position == this.currentBlock.position) {
                    this.currentBlock.status |= label.status & 0x10;
                    label.frame = this.currentBlock.frame;
                    return;
                }
                this.addSuccessor(0, label);
            }
            this.currentBlock = label;
            if (label.frame == null) {
                label.frame = new Frame();
                label.frame.owner = label;
            }
            if (this.previousBlock != null) {
                if (label.position == this.previousBlock.position) {
                    this.previousBlock.status |= label.status & 0x10;
                    label.frame = this.previousBlock.frame;
                    this.currentBlock = this.previousBlock;
                    return;
                }
                this.previousBlock.successor = label;
            }
            this.previousBlock = label;
        } else if (this.compute == 1) {
            if (this.currentBlock != null) {
                this.currentBlock.outputStackMax = this.maxStackSize;
                this.addSuccessor(this.stackSize, label);
            }
            this.currentBlock = label;
            this.stackSize = 0;
            this.maxStackSize = 0;
            if (this.previousBlock != null) {
                this.previousBlock.successor = label;
            }
            this.previousBlock = label;
        }
    }

    public void visitLdcInsn(Object object) {
        int n;
        this.lastCodeOffset = this.code.length;
        Item item = this.cw.newConstItem(object);
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(18, 0, this.cw, item);
            } else {
                n = item.type == 5 || item.type == 6 ? this.stackSize + 2 : this.stackSize + 1;
                if (n > this.maxStackSize) {
                    this.maxStackSize = n;
                }
                this.stackSize = n;
            }
        }
        n = item.index;
        if (item.type == 5 || item.type == 6) {
            this.code.put12(20, n);
        } else if (n >= 256) {
            this.code.put12(19, n);
        } else {
            this.code.put11(18, n);
        }
    }

    public void visitIincInsn(int n, int n2) {
        int n3;
        this.lastCodeOffset = this.code.length;
        if (this.currentBlock != null && this.compute == 0) {
            this.currentBlock.frame.execute(132, n, null, null);
        }
        if (this.compute != 2 && (n3 = n + 1) > this.maxLocals) {
            this.maxLocals = n3;
        }
        if (n > 255 || n2 > 127 || n2 < -128) {
            this.code.putByte(196).put12(132, n).putShort(n2);
        } else {
            this.code.putByte(132).put11(n, n2);
        }
    }

    public void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        this.lastCodeOffset = this.code.length;
        int n3 = this.code.length;
        this.code.putByte(170);
        this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
        label.put(this, this.code, n3, true);
        this.code.putInt(n).putInt(n2);
        for (int i = 0; i < labelArray.length; ++i) {
            labelArray[i].put(this, this.code, n3, true);
        }
        this.visitSwitchInsn(label, labelArray);
    }

    public void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        this.lastCodeOffset = this.code.length;
        int n = this.code.length;
        this.code.putByte(171);
        this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
        label.put(this, this.code, n, true);
        this.code.putInt(labelArray.length);
        for (int i = 0; i < labelArray.length; ++i) {
            this.code.putInt(nArray[i]);
            labelArray[i].put(this, this.code, n, true);
        }
        this.visitSwitchInsn(label, labelArray);
    }

    private void visitSwitchInsn(Label label, Label[] labelArray) {
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(171, 0, null, null);
                this.addSuccessor(0, label);
                label.getFirst().status |= 0x10;
                for (int i = 0; i < labelArray.length; ++i) {
                    this.addSuccessor(0, labelArray[i]);
                    labelArray[i].getFirst().status |= 0x10;
                }
            } else {
                --this.stackSize;
                this.addSuccessor(this.stackSize, label);
                for (int i = 0; i < labelArray.length; ++i) {
                    this.addSuccessor(this.stackSize, labelArray[i]);
                }
            }
            this.noSuccessor();
        }
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        this.lastCodeOffset = this.code.length;
        Item item = this.cw.newClassItem(string);
        if (this.currentBlock != null) {
            if (this.compute == 0) {
                this.currentBlock.frame.execute(197, n, this.cw, item);
            } else {
                this.stackSize += 1 - n;
            }
        }
        this.code.put12(197, item.index).putByte(n);
    }

    public AnnotationVisitor visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        n = n & 0xFF0000FF | this.lastCodeOffset << 8;
        AnnotationWriter.putTarget(n, typePath, byteVector);
        byteVector.putShort(this.cw.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (bl) {
            annotationWriter.next = this.ctanns;
            this.ctanns = annotationWriter;
        } else {
            annotationWriter.next = this.ictanns;
            this.ictanns = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        ++this.handlerCount;
        Handler handler = new Handler();
        handler.start = label;
        handler.end = label2;
        handler.handler = label3;
        handler.desc = string;
        int n = handler.type = string != null ? this.cw.newClass(string) : 0;
        if (this.lastHandler == null) {
            this.firstHandler = handler;
        } else {
            this.lastHandler.next = handler;
        }
        this.lastHandler = handler;
    }

    public AnnotationVisitor visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        ByteVector byteVector = new ByteVector();
        AnnotationWriter.putTarget(n, typePath, byteVector);
        byteVector.putShort(this.cw.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (bl) {
            annotationWriter.next = this.ctanns;
            this.ctanns = annotationWriter;
        } else {
            annotationWriter.next = this.ictanns;
            this.ictanns = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        char c;
        int n2;
        if (string3 != null) {
            if (this.localVarType == null) {
                this.localVarType = new ByteVector();
            }
            ++this.localVarTypeCount;
            this.localVarType.putShort(label.position).putShort(label2.position - label.position).putShort(this.cw.newUTF8(string)).putShort(this.cw.newUTF8(string3)).putShort(n);
        }
        if (this.localVar == null) {
            this.localVar = new ByteVector();
        }
        ++this.localVarCount;
        this.localVar.putShort(label.position).putShort(label2.position - label.position).putShort(this.cw.newUTF8(string)).putShort(this.cw.newUTF8(string2)).putShort(n);
        if (this.compute != 2 && (n2 = n + ((c = string2.charAt(0)) == 'J' || c == 'D' ? 2 : 1)) > this.maxLocals) {
            this.maxLocals = n2;
        }
    }

    public AnnotationVisitor visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        int n2;
        ByteVector byteVector = new ByteVector();
        byteVector.putByte(n >>> 24).putShort(labelArray.length);
        for (n2 = 0; n2 < labelArray.length; ++n2) {
            byteVector.putShort(labelArray[n2].position).putShort(labelArray2[n2].position - labelArray[n2].position).putShort(nArray[n2]);
        }
        if (typePath == null) {
            byteVector.putByte(0);
        } else {
            n2 = typePath.b[typePath.offset] * 2 + 1;
            byteVector.putByteArray(typePath.b, typePath.offset, n2);
        }
        byteVector.putShort(this.cw.newUTF8(string)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.cw, true, byteVector, byteVector, byteVector.length - 2);
        if (bl) {
            annotationWriter.next = this.ctanns;
            this.ctanns = annotationWriter;
        } else {
            annotationWriter.next = this.ictanns;
            this.ictanns = annotationWriter;
        }
        return annotationWriter;
    }

    public void visitLineNumber(int n, Label label) {
        if (this.lineNumber == null) {
            this.lineNumber = new ByteVector();
        }
        ++this.lineNumberCount;
        this.lineNumber.putShort(label.position);
        this.lineNumber.putShort(n);
    }

    public void visitMaxs(int n, int n2) {
        if (this.resize) {
            this.resizeInstructions();
        }
        if (this.compute == 0) {
            int n3;
            Object object;
            Type[] typeArray;
            Object object2;
            Handler handler = this.firstHandler;
            while (handler != null) {
                object2 = handler.start.getFirst();
                typeArray = handler.handler.getFirst();
                Label label = handler.end.getFirst();
                object = handler.desc == null ? "java/lang/Throwable" : handler.desc;
                int n4 = 0x1700000 | this.cw.addType((String)object);
                typeArray.status |= 0x10;
                while (object2 != label) {
                    Edge edge = new Edge();
                    edge.info = n4;
                    edge.successor = typeArray;
                    edge.next = ((Label)object2).successors;
                    ((Label)object2).successors = edge;
                    object2 = ((Label)object2).successor;
                }
                handler = handler.next;
            }
            object2 = this.labels.frame;
            typeArray = Type.getArgumentTypes(this.descriptor);
            ((Frame)object2).initInputFrame(this.cw, this.access, typeArray, this.maxLocals);
            this.visitFrame((Frame)object2);
            int n5 = 0;
            object = this.labels;
            while (object != null) {
                Object object3 = object;
                object = ((Label)object).next;
                ((Label)object3).next = null;
                object2 = ((Label)object3).frame;
                if ((((Label)object3).status & 0x10) != 0) {
                    ((Label)object3).status |= 0x20;
                }
                ((Label)object3).status |= 0x40;
                int n6 = ((Frame)object2).inputStack.length + ((Label)object3).outputStackMax;
                if (n6 > n5) {
                    n5 = n6;
                }
                Edge edge = ((Label)object3).successors;
                while (edge != null) {
                    Label label = edge.successor.getFirst();
                    n3 = ((Frame)object2).merge(this.cw, label.frame, edge.info) ? 1 : 0;
                    if (n3 != 0 && label.next == null) {
                        label.next = object;
                        object = label;
                    }
                    edge = edge.next;
                }
            }
            Label label = this.labels;
            while (label != null) {
                int n7;
                Label label2;
                int n8;
                object2 = label.frame;
                if ((label.status & 0x20) != 0) {
                    this.visitFrame((Frame)object2);
                }
                if ((label.status & 0x40) == 0 && (n8 = ((label2 = label.successor) == null ? this.code.length : label2.position) - 1) >= (n7 = label.position)) {
                    n5 = Math.max(n5, 1);
                    for (n3 = n7; n3 < n8; ++n3) {
                        this.code.data[n3] = 0;
                    }
                    this.code.data[n8] = -65;
                    n3 = this.startFrame(n7, 0, 1);
                    this.frame[n3] = 0x1700000 | this.cw.addType("java/lang/Throwable");
                    this.endFrame();
                    this.firstHandler = Handler.remove(this.firstHandler, label, label2);
                }
                label = label.successor;
            }
            handler = this.firstHandler;
            this.handlerCount = 0;
            while (handler != null) {
                ++this.handlerCount;
                handler = handler.next;
            }
            this.maxStack = n5;
        } else if (this.compute == 1) {
            Object object;
            Label label;
            Label label3;
            Handler handler = this.firstHandler;
            while (handler != null) {
                Label label4 = handler.start;
                label3 = handler.handler;
                label = handler.end;
                while (label4 != label) {
                    object = new Edge();
                    ((Edge)object).info = Integer.MAX_VALUE;
                    ((Edge)object).successor = label3;
                    if ((label4.status & 0x80) == 0) {
                        ((Edge)object).next = label4.successors;
                        label4.successors = object;
                    } else {
                        ((Edge)object).next = label4.successors.next.next;
                        label4.successors.next.next = object;
                    }
                    label4 = label4.successor;
                }
                handler = handler.next;
            }
            if (this.subroutines > 0) {
                int n9 = 0;
                this.labels.visitSubroutine(null, 1L, this.subroutines);
                label3 = this.labels;
                while (label3 != null) {
                    if ((label3.status & 0x80) != 0) {
                        label = label3.successors.next.successor;
                        if ((label.status & 0x400) == 0) {
                            label.visitSubroutine(null, (long)(++n9) / 32L << 32 | 1L << n9 % 32, this.subroutines);
                        }
                    }
                    label3 = label3.successor;
                }
                label3 = this.labels;
                while (label3 != null) {
                    if ((label3.status & 0x80) != 0) {
                        label = this.labels;
                        while (label != null) {
                            label.status &= 0xFFFFF7FF;
                            label = label.successor;
                        }
                        object = label3.successors.next.successor;
                        ((Label)object).visitSubroutine(label3, 0L, this.subroutines);
                    }
                    label3 = label3.successor;
                }
            }
            int n10 = 0;
            label3 = this.labels;
            while (label3 != null) {
                label = label3;
                label3 = label3.next;
                int n11 = label.inputStackTop;
                int n12 = n11 + label.outputStackMax;
                if (n12 > n10) {
                    n10 = n12;
                }
                Edge edge = label.successors;
                if ((label.status & 0x80) != 0) {
                    edge = edge.next;
                }
                while (edge != null) {
                    label = edge.successor;
                    if ((label.status & 8) == 0) {
                        label.inputStackTop = edge.info == Integer.MAX_VALUE ? 1 : n11 + edge.info;
                        label.status |= 8;
                        label.next = label3;
                        label3 = label;
                    }
                    edge = edge.next;
                }
            }
            this.maxStack = Math.max(n, n10);
        } else {
            this.maxStack = n;
            this.maxLocals = n2;
        }
    }

    public void visitEnd() {
    }

    private void addSuccessor(int n, Label label) {
        Edge edge = new Edge();
        edge.info = n;
        edge.successor = label;
        edge.next = this.currentBlock.successors;
        this.currentBlock.successors = edge;
    }

    private void noSuccessor() {
        if (this.compute == 0) {
            Label label = new Label();
            label.frame = new Frame();
            label.frame.owner = label;
            label.resolve(this, this.code.length, this.code.data);
            this.previousBlock.successor = label;
            this.previousBlock = label;
        } else {
            this.currentBlock.outputStackMax = this.maxStackSize;
        }
        this.currentBlock = null;
    }

    private void visitFrame(Frame frame) {
        int n;
        int n2;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int[] nArray = frame.inputLocals;
        int[] nArray2 = frame.inputStack;
        for (n2 = 0; n2 < nArray.length; ++n2) {
            n = nArray[n2];
            if (n == 0x1000000) {
                ++n3;
            } else {
                n4 += n3 + 1;
                n3 = 0;
            }
            if (n != 0x1000004 && n != 0x1000003) continue;
            ++n2;
        }
        for (n2 = 0; n2 < nArray2.length; ++n2) {
            n = nArray2[n2];
            ++n5;
            if (n != 0x1000004 && n != 0x1000003) continue;
            ++n2;
        }
        int n6 = this.startFrame(frame.owner.position, n4, n5);
        n2 = 0;
        while (n4 > 0) {
            n = nArray[n2];
            this.frame[n6++] = n;
            if (n == 0x1000004 || n == 0x1000003) {
                ++n2;
            }
            ++n2;
            --n4;
        }
        for (n2 = 0; n2 < nArray2.length; ++n2) {
            n = nArray2[n2];
            this.frame[n6++] = n;
            if (n != 0x1000004 && n != 0x1000003) continue;
            ++n2;
        }
        this.endFrame();
    }

    private void visitImplicitFirstFrame() {
        int n = this.startFrame(0, this.descriptor.length() + 1, 0);
        if ((this.access & 8) == 0) {
            this.frame[n++] = (this.access & 0x80000) == 0 ? 0x1700000 | this.cw.addType(this.cw.thisName) : 6;
        }
        int n2 = 1;
        block8: while (true) {
            int n3 = n2;
            switch (this.descriptor.charAt(n2++)) {
                case 'B': 
                case 'C': 
                case 'I': 
                case 'S': 
                case 'Z': {
                    this.frame[n++] = 1;
                    continue block8;
                }
                case 'F': {
                    this.frame[n++] = 2;
                    continue block8;
                }
                case 'J': {
                    this.frame[n++] = 4;
                    continue block8;
                }
                case 'D': {
                    this.frame[n++] = 3;
                    continue block8;
                }
                case '[': {
                    while (this.descriptor.charAt(n2) == '[') {
                        ++n2;
                    }
                    if (this.descriptor.charAt(n2) == 'L') {
                        ++n2;
                        while (this.descriptor.charAt(n2) != ';') {
                            ++n2;
                        }
                    }
                    this.frame[n++] = 0x1700000 | this.cw.addType(this.descriptor.substring(n3, ++n2));
                    continue block8;
                }
                case 'L': {
                    while (this.descriptor.charAt(n2) != ';') {
                        ++n2;
                    }
                    this.frame[n++] = 0x1700000 | this.cw.addType(this.descriptor.substring(n3 + 1, n2++));
                    continue block8;
                }
            }
            break;
        }
        this.frame[1] = n - 3;
        this.endFrame();
    }

    private int startFrame(int n, int n2, int n3) {
        int n4 = 3 + n2 + n3;
        if (this.frame == null || this.frame.length < n4) {
            this.frame = new int[n4];
        }
        this.frame[0] = n;
        this.frame[1] = n2;
        this.frame[2] = n3;
        return 3;
    }

    private void endFrame() {
        if (this.previousFrame != null) {
            if (this.stackMap == null) {
                this.stackMap = new ByteVector();
            }
            this.writeFrame();
            ++this.frameCount;
        }
        this.previousFrame = this.frame;
        this.frame = null;
    }

    private void writeFrame() {
        int n = this.frame[1];
        int n2 = this.frame[2];
        if ((this.cw.version & 0xFFFF) < 50) {
            this.stackMap.putShort(this.frame[0]).putShort(n);
            this.writeFrameTypes(3, 3 + n);
            this.stackMap.putShort(n2);
            this.writeFrameTypes(3 + n, 3 + n + n2);
            return;
        }
        int n3 = this.previousFrame[1];
        int n4 = 255;
        int n5 = 0;
        int n6 = this.frameCount == 0 ? this.frame[0] : this.frame[0] - this.previousFrame[0] - 1;
        if (n2 == 0) {
            n5 = n - n3;
            switch (n5) {
                case -3: 
                case -2: 
                case -1: {
                    n4 = 248;
                    n3 = n;
                    break;
                }
                case 0: {
                    n4 = n6 < 64 ? 0 : 251;
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    n4 = 252;
                }
            }
        } else if (n == n3 && n2 == 1) {
            int n7 = n4 = n6 < 63 ? 64 : 247;
        }
        if (n4 != 255) {
            int n8 = 3;
            for (int i = 0; i < n3; ++i) {
                if (this.frame[n8] != this.previousFrame[n8]) {
                    n4 = 255;
                    break;
                }
                ++n8;
            }
        }
        switch (n4) {
            case 0: {
                this.stackMap.putByte(n6);
                break;
            }
            case 64: {
                this.stackMap.putByte(64 + n6);
                this.writeFrameTypes(3 + n, 4 + n);
                break;
            }
            case 247: {
                this.stackMap.putByte(247).putShort(n6);
                this.writeFrameTypes(3 + n, 4 + n);
                break;
            }
            case 251: {
                this.stackMap.putByte(251).putShort(n6);
                break;
            }
            case 248: {
                this.stackMap.putByte(251 + n5).putShort(n6);
                break;
            }
            case 252: {
                this.stackMap.putByte(251 + n5).putShort(n6);
                this.writeFrameTypes(3 + n3, 3 + n);
                break;
            }
            default: {
                this.stackMap.putByte(255).putShort(n6).putShort(n);
                this.writeFrameTypes(3, 3 + n);
                this.stackMap.putShort(n2);
                this.writeFrameTypes(3 + n, 3 + n + n2);
            }
        }
    }

    private void writeFrameTypes(int n, int n2) {
        for (int i = n; i < n2; ++i) {
            int n3 = this.frame[i];
            int n4 = n3 & 0xF0000000;
            if (n4 == 0) {
                int n5 = n3 & 0xFFFFF;
                switch (n3 & 0xFF00000) {
                    case 0x1700000: {
                        this.stackMap.putByte(7).putShort(this.cw.newClass(this.cw.typeTable[n5].strVal1));
                        break;
                    }
                    case 0x1800000: {
                        this.stackMap.putByte(8).putShort(this.cw.typeTable[n5].intVal);
                        break;
                    }
                    default: {
                        this.stackMap.putByte(n5);
                        break;
                    }
                }
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder();
            n4 >>= 28;
            while (n4-- > 0) {
                stringBuilder.append('[');
            }
            if ((n3 & 0xFF00000) == 0x1700000) {
                stringBuilder.append('L');
                stringBuilder.append(this.cw.typeTable[n3 & 0xFFFFF].strVal1);
                stringBuilder.append(';');
            } else {
                switch (n3 & 0xF) {
                    case 1: {
                        stringBuilder.append('I');
                        break;
                    }
                    case 2: {
                        stringBuilder.append('F');
                        break;
                    }
                    case 3: {
                        stringBuilder.append('D');
                        break;
                    }
                    case 9: {
                        stringBuilder.append('Z');
                        break;
                    }
                    case 10: {
                        stringBuilder.append('B');
                        break;
                    }
                    case 11: {
                        stringBuilder.append('C');
                        break;
                    }
                    case 12: {
                        stringBuilder.append('S');
                        break;
                    }
                    default: {
                        stringBuilder.append('J');
                    }
                }
            }
            this.stackMap.putByte(7).putShort(this.cw.newClass(stringBuilder.toString()));
        }
    }

    private void writeFrameType(Object object) {
        if (object instanceof String) {
            this.stackMap.putByte(7).putShort(this.cw.newClass((String)object));
        } else if (object instanceof Integer) {
            this.stackMap.putByte((Integer)object);
        } else {
            this.stackMap.putByte(8).putShort(((Label)object).position);
        }
    }

    final int getSize() {
        int n;
        if (this.classReaderOffset != 0) {
            return 6 + this.classReaderLength;
        }
        int n2 = 8;
        if (this.code.length > 0) {
            if (this.code.length > 65536) {
                throw new RuntimeException("Method code too large!");
            }
            this.cw.newUTF8("Code");
            n2 += 18 + this.code.length + 8 * this.handlerCount;
            if (this.localVar != null) {
                this.cw.newUTF8("LocalVariableTable");
                n2 += 8 + this.localVar.length;
            }
            if (this.localVarType != null) {
                this.cw.newUTF8("LocalVariableTypeTable");
                n2 += 8 + this.localVarType.length;
            }
            if (this.lineNumber != null) {
                this.cw.newUTF8("LineNumberTable");
                n2 += 8 + this.lineNumber.length;
            }
            if (this.stackMap != null) {
                n = (this.cw.version & 0xFFFF) >= 50 ? 1 : 0;
                this.cw.newUTF8(n != 0 ? "StackMapTable" : "StackMap");
                n2 += 8 + this.stackMap.length;
            }
            if (this.ctanns != null) {
                this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
                n2 += 8 + this.ctanns.getSize();
            }
            if (this.ictanns != null) {
                this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
                n2 += 8 + this.ictanns.getSize();
            }
            if (this.cattrs != null) {
                n2 += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
        }
        if (this.exceptionCount > 0) {
            this.cw.newUTF8("Exceptions");
            n2 += 8 + 2 * this.exceptionCount;
        }
        if ((this.access & 0x1000) != 0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
            this.cw.newUTF8("Synthetic");
            n2 += 6;
        }
        if ((this.access & 0x20000) != 0) {
            this.cw.newUTF8("Deprecated");
            n2 += 6;
        }
        if (this.signature != null) {
            this.cw.newUTF8("Signature");
            this.cw.newUTF8(this.signature);
            n2 += 8;
        }
        if (this.methodParameters != null) {
            this.cw.newUTF8("MethodParameters");
            n2 += 7 + this.methodParameters.length;
        }
        if (this.annd != null) {
            this.cw.newUTF8("AnnotationDefault");
            n2 += 6 + this.annd.length;
        }
        if (this.anns != null) {
            this.cw.newUTF8("RuntimeVisibleAnnotations");
            n2 += 8 + this.anns.getSize();
        }
        if (this.ianns != null) {
            this.cw.newUTF8("RuntimeInvisibleAnnotations");
            n2 += 8 + this.ianns.getSize();
        }
        if (this.tanns != null) {
            this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
            n2 += 8 + this.tanns.getSize();
        }
        if (this.itanns != null) {
            this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
            n2 += 8 + this.itanns.getSize();
        }
        if (this.panns != null) {
            this.cw.newUTF8("RuntimeVisibleParameterAnnotations");
            n2 += 7 + 2 * (this.panns.length - this.synthetics);
            for (n = this.panns.length - 1; n >= this.synthetics; --n) {
                n2 += this.panns[n] == null ? 0 : this.panns[n].getSize();
            }
        }
        if (this.ipanns != null) {
            this.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
            n2 += 7 + 2 * (this.ipanns.length - this.synthetics);
            for (n = this.ipanns.length - 1; n >= this.synthetics; --n) {
                n2 += this.ipanns[n] == null ? 0 : this.ipanns[n].getSize();
            }
        }
        if (this.attrs != null) {
            n2 += this.attrs.getSize(this.cw, null, 0, -1, -1);
        }
        return n2;
    }

    final void put(ByteVector byteVector) {
        int n;
        int n2 = 64;
        int n3 = 0xE0000 | (this.access & 0x40000) / 64;
        byteVector.putShort(this.access & ~n3).putShort(this.name).putShort(this.desc);
        if (this.classReaderOffset != 0) {
            byteVector.putByteArray(this.cw.cr.b, this.classReaderOffset, this.classReaderLength);
            return;
        }
        int n4 = 0;
        if (this.code.length > 0) {
            ++n4;
        }
        if (this.exceptionCount > 0) {
            ++n4;
        }
        if ((this.access & 0x1000) != 0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
            ++n4;
        }
        if ((this.access & 0x20000) != 0) {
            ++n4;
        }
        if (this.signature != null) {
            ++n4;
        }
        if (this.methodParameters != null) {
            ++n4;
        }
        if (this.annd != null) {
            ++n4;
        }
        if (this.anns != null) {
            ++n4;
        }
        if (this.ianns != null) {
            ++n4;
        }
        if (this.tanns != null) {
            ++n4;
        }
        if (this.itanns != null) {
            ++n4;
        }
        if (this.panns != null) {
            ++n4;
        }
        if (this.ipanns != null) {
            ++n4;
        }
        if (this.attrs != null) {
            n4 += this.attrs.getCount();
        }
        byteVector.putShort(n4);
        if (this.code.length > 0) {
            n = 12 + this.code.length + 8 * this.handlerCount;
            if (this.localVar != null) {
                n += 8 + this.localVar.length;
            }
            if (this.localVarType != null) {
                n += 8 + this.localVarType.length;
            }
            if (this.lineNumber != null) {
                n += 8 + this.lineNumber.length;
            }
            if (this.stackMap != null) {
                n += 8 + this.stackMap.length;
            }
            if (this.ctanns != null) {
                n += 8 + this.ctanns.getSize();
            }
            if (this.ictanns != null) {
                n += 8 + this.ictanns.getSize();
            }
            if (this.cattrs != null) {
                n += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
            byteVector.putShort(this.cw.newUTF8("Code")).putInt(n);
            byteVector.putShort(this.maxStack).putShort(this.maxLocals);
            byteVector.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
            byteVector.putShort(this.handlerCount);
            if (this.handlerCount > 0) {
                Handler handler = this.firstHandler;
                while (handler != null) {
                    byteVector.putShort(handler.start.position).putShort(handler.end.position).putShort(handler.handler.position).putShort(handler.type);
                    handler = handler.next;
                }
            }
            n4 = 0;
            if (this.localVar != null) {
                ++n4;
            }
            if (this.localVarType != null) {
                ++n4;
            }
            if (this.lineNumber != null) {
                ++n4;
            }
            if (this.stackMap != null) {
                ++n4;
            }
            if (this.ctanns != null) {
                ++n4;
            }
            if (this.ictanns != null) {
                ++n4;
            }
            if (this.cattrs != null) {
                n4 += this.cattrs.getCount();
            }
            byteVector.putShort(n4);
            if (this.localVar != null) {
                byteVector.putShort(this.cw.newUTF8("LocalVariableTable"));
                byteVector.putInt(this.localVar.length + 2).putShort(this.localVarCount);
                byteVector.putByteArray(this.localVar.data, 0, this.localVar.length);
            }
            if (this.localVarType != null) {
                byteVector.putShort(this.cw.newUTF8("LocalVariableTypeTable"));
                byteVector.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
                byteVector.putByteArray(this.localVarType.data, 0, this.localVarType.length);
            }
            if (this.lineNumber != null) {
                byteVector.putShort(this.cw.newUTF8("LineNumberTable"));
                byteVector.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
                byteVector.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
            }
            if (this.stackMap != null) {
                boolean bl = (this.cw.version & 0xFFFF) >= 50;
                byteVector.putShort(this.cw.newUTF8(bl ? "StackMapTable" : "StackMap"));
                byteVector.putInt(this.stackMap.length + 2).putShort(this.frameCount);
                byteVector.putByteArray(this.stackMap.data, 0, this.stackMap.length);
            }
            if (this.ctanns != null) {
                byteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
                this.ctanns.put(byteVector);
            }
            if (this.ictanns != null) {
                byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
                this.ictanns.put(byteVector);
            }
            if (this.cattrs != null) {
                this.cattrs.put(this.cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, byteVector);
            }
        }
        if (this.exceptionCount > 0) {
            byteVector.putShort(this.cw.newUTF8("Exceptions")).putInt(2 * this.exceptionCount + 2);
            byteVector.putShort(this.exceptionCount);
            for (n = 0; n < this.exceptionCount; ++n) {
                byteVector.putShort(this.exceptions[n]);
            }
        }
        if ((this.access & 0x1000) != 0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
            byteVector.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.access & 0x20000) != 0) {
            byteVector.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
        }
        if (this.signature != null) {
            byteVector.putShort(this.cw.newUTF8("Signature")).putInt(2).putShort(this.cw.newUTF8(this.signature));
        }
        if (this.methodParameters != null) {
            byteVector.putShort(this.cw.newUTF8("MethodParameters"));
            byteVector.putInt(this.methodParameters.length + 1).putByte(this.methodParametersCount);
            byteVector.putByteArray(this.methodParameters.data, 0, this.methodParameters.length);
        }
        if (this.annd != null) {
            byteVector.putShort(this.cw.newUTF8("AnnotationDefault"));
            byteVector.putInt(this.annd.length);
            byteVector.putByteArray(this.annd.data, 0, this.annd.length);
        }
        if (this.anns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(byteVector);
        }
        if (this.ianns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(byteVector);
        }
        if (this.tanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(byteVector);
        }
        if (this.itanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(byteVector);
        }
        if (this.panns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
            AnnotationWriter.put(this.panns, this.synthetics, byteVector);
        }
        if (this.ipanns != null) {
            byteVector.putShort(this.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
            AnnotationWriter.put(this.ipanns, this.synthetics, byteVector);
        }
        if (this.attrs != null) {
            this.attrs.put(this.cw, null, 0, -1, -1, byteVector);
        }
    }

    private void resizeInstructions() {
        int n;
        Object[] objectArray;
        Object object;
        int n2;
        int n3;
        int n4;
        int n5;
        byte[] byArray = this.code.data;
        Object object2 = new int[]{};
        int[] nArray = new int[]{};
        boolean[] blArray = new boolean[this.code.length];
        int n6 = 3;
        do {
            if (n6 == 3) {
                n6 = 2;
            }
            n5 = 0;
            while (n5 < byArray.length) {
                int n7 = byArray[n5] & 0xFF;
                n4 = 0;
                switch (ClassWriter.TYPE[n7]) {
                    case 0: 
                    case 4: {
                        ++n5;
                        break;
                    }
                    case 9: {
                        if (n7 > 201) {
                            n7 = n7 < 218 ? n7 - 49 : n7 - 20;
                            n3 = n5 + MethodWriter.readUnsignedShort(byArray, n5 + 1);
                        } else {
                            n3 = n5 + MethodWriter.readShort(byArray, n5 + 1);
                        }
                        n2 = MethodWriter.getNewOffset(object2, nArray, n5, n3);
                        if (!(n2 >= Short.MIN_VALUE && n2 <= Short.MAX_VALUE || blArray[n5])) {
                            n4 = n7 == 167 || n7 == 168 ? 2 : 5;
                            blArray[n5] = true;
                        }
                        n5 += 3;
                        break;
                    }
                    case 10: {
                        n5 += 5;
                        break;
                    }
                    case 14: {
                        if (n6 == 1) {
                            n2 = MethodWriter.getNewOffset(object2, nArray, 0, n5);
                            n4 = -(n2 & 3);
                        } else if (!blArray[n5]) {
                            n4 = n5 & 3;
                            blArray[n5] = true;
                        }
                        n5 = n5 + 4 - (n5 & 3);
                        n5 += 4 * (MethodWriter.readInt(byArray, n5 + 8) - MethodWriter.readInt(byArray, n5 + 4) + 1) + 12;
                        break;
                    }
                    case 15: {
                        if (n6 == 1) {
                            n2 = MethodWriter.getNewOffset(object2, nArray, 0, n5);
                            n4 = -(n2 & 3);
                        } else if (!blArray[n5]) {
                            n4 = n5 & 3;
                            blArray[n5] = true;
                        }
                        n5 = n5 + 4 - (n5 & 3);
                        n5 += 8 * MethodWriter.readInt(byArray, n5 + 4) + 8;
                        break;
                    }
                    case 17: {
                        n7 = byArray[n5 + 1] & 0xFF;
                        if (n7 == 132) {
                            n5 += 6;
                            break;
                        }
                        n5 += 4;
                        break;
                    }
                    case 1: 
                    case 3: 
                    case 11: {
                        n5 += 2;
                        break;
                    }
                    case 2: 
                    case 5: 
                    case 6: 
                    case 12: 
                    case 13: {
                        n5 += 3;
                        break;
                    }
                    case 7: 
                    case 8: {
                        n5 += 5;
                        break;
                    }
                    default: {
                        n5 += 4;
                    }
                }
                if (n4 == 0) continue;
                object = new int[((int[])object2).length + 1];
                objectArray = new int[nArray.length + 1];
                System.arraycopy(object2, 0, object, 0, ((int[])object2).length);
                System.arraycopy(nArray, 0, objectArray, 0, nArray.length);
                object[((int[])object2).length] = n5;
                objectArray[nArray.length] = n4;
                object2 = object;
                nArray = objectArray;
                if (n4 <= 0) continue;
                n6 = 3;
            }
            if (n6 >= 3) continue;
            --n6;
        } while (n6 != 0);
        ByteVector byteVector = new ByteVector(this.code.length);
        n5 = 0;
        block24: while (n5 < this.code.length) {
            n4 = byArray[n5] & 0xFF;
            switch (ClassWriter.TYPE[n4]) {
                case 0: 
                case 4: {
                    byteVector.putByte(n4);
                    ++n5;
                    continue block24;
                }
                case 9: {
                    if (n4 > 201) {
                        n4 = n4 < 218 ? n4 - 49 : n4 - 20;
                        n3 = n5 + MethodWriter.readUnsignedShort(byArray, n5 + 1);
                    } else {
                        n3 = n5 + MethodWriter.readShort(byArray, n5 + 1);
                    }
                    n2 = MethodWriter.getNewOffset(object2, nArray, n5, n3);
                    if (blArray[n5]) {
                        if (n4 == 167) {
                            byteVector.putByte(200);
                        } else if (n4 == 168) {
                            byteVector.putByte(201);
                        } else {
                            byteVector.putByte(n4 <= 166 ? (n4 + 1 ^ 1) - 1 : n4 ^ 1);
                            byteVector.putShort(8);
                            byteVector.putByte(200);
                            n2 -= 3;
                        }
                        byteVector.putInt(n2);
                    } else {
                        byteVector.putByte(n4);
                        byteVector.putShort(n2);
                    }
                    n5 += 3;
                    continue block24;
                }
                case 10: {
                    n3 = n5 + MethodWriter.readInt(byArray, n5 + 1);
                    n2 = MethodWriter.getNewOffset(object2, nArray, n5, n3);
                    byteVector.putByte(n4);
                    byteVector.putInt(n2);
                    n5 += 5;
                    continue block24;
                }
                case 14: {
                    int n8 = n5;
                    n5 = n5 + 4 - (n8 & 3);
                    byteVector.putByte(170);
                    byteVector.putByteArray(null, 0, (4 - byteVector.length % 4) % 4);
                    n3 = n8 + MethodWriter.readInt(byArray, n5);
                    n2 = MethodWriter.getNewOffset(object2, nArray, n8, n3);
                    byteVector.putInt(n2);
                    int n9 = MethodWriter.readInt(byArray, n5 += 4);
                    byteVector.putInt(n9);
                    byteVector.putInt(MethodWriter.readInt(byArray, (n5 += 4) - 4));
                    for (n9 = MethodWriter.readInt(byArray, n5 += 4) - n9 + 1; n9 > 0; --n9) {
                        n3 = n8 + MethodWriter.readInt(byArray, n5);
                        n5 += 4;
                        n2 = MethodWriter.getNewOffset(object2, nArray, n8, n3);
                        byteVector.putInt(n2);
                    }
                    continue block24;
                }
                case 15: {
                    int n9;
                    int n8 = n5;
                    n5 = n5 + 4 - (n8 & 3);
                    byteVector.putByte(171);
                    byteVector.putByteArray(null, 0, (4 - byteVector.length % 4) % 4);
                    n3 = n8 + MethodWriter.readInt(byArray, n5);
                    n2 = MethodWriter.getNewOffset(object2, nArray, n8, n3);
                    byteVector.putInt(n2);
                    n5 += 4;
                    byteVector.putInt(n9);
                    for (n9 = MethodWriter.readInt(byArray, n5 += 4); n9 > 0; --n9) {
                        byteVector.putInt(MethodWriter.readInt(byArray, n5));
                        n3 = n8 + MethodWriter.readInt(byArray, n5 += 4);
                        n5 += 4;
                        n2 = MethodWriter.getNewOffset(object2, nArray, n8, n3);
                        byteVector.putInt(n2);
                    }
                    continue block24;
                }
                case 17: {
                    n4 = byArray[n5 + 1] & 0xFF;
                    if (n4 == 132) {
                        byteVector.putByteArray(byArray, n5, 6);
                        n5 += 6;
                        continue block24;
                    }
                    byteVector.putByteArray(byArray, n5, 4);
                    n5 += 4;
                    continue block24;
                }
                case 1: 
                case 3: 
                case 11: {
                    byteVector.putByteArray(byArray, n5, 2);
                    n5 += 2;
                    continue block24;
                }
                case 2: 
                case 5: 
                case 6: 
                case 12: 
                case 13: {
                    byteVector.putByteArray(byArray, n5, 3);
                    n5 += 3;
                    continue block24;
                }
                case 7: 
                case 8: {
                    byteVector.putByteArray(byArray, n5, 5);
                    n5 += 5;
                    continue block24;
                }
            }
            byteVector.putByteArray(byArray, n5, 4);
            n5 += 4;
        }
        if (this.compute == 0) {
            Label label = this.labels;
            while (label != null) {
                n5 = label.position - 3;
                if (n5 >= 0 && blArray[n5]) {
                    label.status |= 0x10;
                }
                MethodWriter.getNewOffset(object2, nArray, label);
                label = label.successor;
            }
            for (n = 0; n < this.cw.typeTable.length; ++n) {
                object = this.cw.typeTable[n];
                if (object == null || ((Item)object).type != 31) continue;
                ((Item)object).intVal = MethodWriter.getNewOffset(object2, nArray, 0, ((Item)object).intVal);
            }
        } else if (this.frameCount > 0) {
            this.cw.invalidFrames = true;
        }
        Handler handler = this.firstHandler;
        while (handler != null) {
            MethodWriter.getNewOffset(object2, nArray, handler.start);
            MethodWriter.getNewOffset(object2, nArray, handler.end);
            MethodWriter.getNewOffset(object2, nArray, handler.handler);
            handler = handler.next;
        }
        for (n = 0; n < 2; ++n) {
            object = n == 0 ? this.localVar : this.localVarType;
            if (object == null) continue;
            byArray = ((ByteVector)object).data;
            for (n5 = 0; n5 < ((ByteVector)object).length; n5 += 10) {
                n3 = MethodWriter.readUnsignedShort(byArray, n5);
                n2 = MethodWriter.getNewOffset(object2, nArray, 0, n3);
                MethodWriter.writeShort(byArray, n5, n2);
                n2 = MethodWriter.getNewOffset(object2, nArray, 0, n3 += MethodWriter.readUnsignedShort(byArray, n5 + 2)) - n2;
                MethodWriter.writeShort(byArray, n5 + 2, n2);
            }
        }
        if (this.lineNumber != null) {
            byArray = this.lineNumber.data;
            for (n5 = 0; n5 < this.lineNumber.length; n5 += 4) {
                MethodWriter.writeShort(byArray, n5, MethodWriter.getNewOffset(object2, nArray, 0, MethodWriter.readUnsignedShort(byArray, n5)));
            }
        }
        object = this.cattrs;
        while (object != null) {
            objectArray = ((Attribute)object).getLabels();
            if (objectArray != null) {
                for (n = objectArray.length - 1; n >= 0; --n) {
                    MethodWriter.getNewOffset(object2, nArray, (Label)objectArray[n]);
                }
            }
            object = ((Attribute)object).next;
        }
        this.code = byteVector;
    }

    static int readUnsignedShort(byte[] byArray, int n) {
        return (byArray[n] & 0xFF) << 8 | byArray[n + 1] & 0xFF;
    }

    static short readShort(byte[] byArray, int n) {
        return (short)((byArray[n] & 0xFF) << 8 | byArray[n + 1] & 0xFF);
    }

    static int readInt(byte[] byArray, int n) {
        return (byArray[n] & 0xFF) << 24 | (byArray[n + 1] & 0xFF) << 16 | (byArray[n + 2] & 0xFF) << 8 | byArray[n + 3] & 0xFF;
    }

    static void writeShort(byte[] byArray, int n, int n2) {
        byArray[n] = (byte)(n2 >>> 8);
        byArray[n + 1] = (byte)n2;
    }

    static int getNewOffset(int[] nArray, int[] nArray2, int n, int n2) {
        int n3 = n2 - n;
        for (int i = 0; i < nArray.length; ++i) {
            if (n < nArray[i] && nArray[i] <= n2) {
                n3 += nArray2[i];
                continue;
            }
            if (n2 >= nArray[i] || nArray[i] > n) continue;
            n3 -= nArray2[i];
        }
        return n3;
    }

    static void getNewOffset(int[] nArray, int[] nArray2, Label label) {
        if ((label.status & 4) == 0) {
            label.position = MethodWriter.getNewOffset(nArray, nArray2, 0, label.position);
            label.status |= 4;
        }
    }
}

