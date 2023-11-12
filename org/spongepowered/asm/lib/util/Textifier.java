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
import org.spongepowered.asm.lib.TypeReference;
import org.spongepowered.asm.lib.signature.SignatureReader;
import org.spongepowered.asm.lib.signature.SignatureVisitor;
import org.spongepowered.asm.lib.util.Printer;
import org.spongepowered.asm.lib.util.Textifiable;
import org.spongepowered.asm.lib.util.TraceClassVisitor;
import org.spongepowered.asm.lib.util.TraceSignatureVisitor;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Textifier
extends Printer {
    public static final int INTERNAL_NAME = 0;
    public static final int FIELD_DESCRIPTOR = 1;
    public static final int FIELD_SIGNATURE = 2;
    public static final int METHOD_DESCRIPTOR = 3;
    public static final int METHOD_SIGNATURE = 4;
    public static final int CLASS_SIGNATURE = 5;
    public static final int TYPE_DECLARATION = 6;
    public static final int CLASS_DECLARATION = 7;
    public static final int PARAMETERS_DECLARATION = 8;
    public static final int HANDLE_DESCRIPTOR = 9;
    protected String tab = "  ";
    protected String tab2 = "    ";
    protected String tab3 = "      ";
    protected String ltab = "   ";
    protected Map labelNames;
    private int access;
    private int valueNumber = 0;

    public Textifier() {
        this(327680);
        if (this.getClass() != Textifier.class) {
            throw new IllegalStateException();
        }
    }

    protected Textifier(int n) {
        super(n);
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
            System.err.println("Prints a disassembled view of the given class.");
            System.err.println("Usage: Textifier [-debug] <fully qualified class name or class file name>");
            return;
        }
        ClassReader classReader = stringArray[n].endsWith(".class") || stringArray[n].indexOf(92) > -1 || stringArray[n].indexOf(47) > -1 ? new ClassReader(new FileInputStream(stringArray[n])) : new ClassReader(stringArray[n]);
        classReader.accept(new TraceClassVisitor(new PrintWriter(System.out)), n2);
    }

    public void visit(int n, int n2, String string, String string2, String string3, String[] stringArray) {
        this.access = n2;
        int n3 = n & 0xFFFF;
        int n4 = n >>> 16;
        this.buf.setLength(0);
        this.buf.append("// class version ").append(n3).append('.').append(n4).append(" (").append(n).append(")\n");
        if ((n2 & 0x20000) != 0) {
            this.buf.append("// DEPRECATED\n");
        }
        this.buf.append("// access flags 0x").append(Integer.toHexString(n2).toUpperCase()).append('\n');
        this.appendDescriptor(5, string2);
        if (string2 != null) {
            TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(n2);
            SignatureReader signatureReader = new SignatureReader(string2);
            signatureReader.accept(traceSignatureVisitor);
            this.buf.append("// declaration: ").append(string).append(traceSignatureVisitor.getDeclaration()).append('\n');
        }
        this.appendAccess(n2 & 0xFFFFFFDF);
        if ((n2 & 0x2000) != 0) {
            this.buf.append("@interface ");
        } else if ((n2 & 0x200) != 0) {
            this.buf.append("interface ");
        } else if ((n2 & 0x4000) == 0) {
            this.buf.append("class ");
        }
        this.appendDescriptor(0, string);
        if (string3 != null && !"java/lang/Object".equals(string3)) {
            this.buf.append(" extends ");
            this.appendDescriptor(0, string3);
            this.buf.append(' ');
        }
        if (stringArray != null && stringArray.length > 0) {
            this.buf.append(" implements ");
            for (int i = 0; i < stringArray.length; ++i) {
                this.appendDescriptor(0, stringArray[i]);
                this.buf.append(' ');
            }
        }
        this.buf.append(" {\n\n");
        this.text.add(this.buf.toString());
    }

    public void visitSource(String string, String string2) {
        this.buf.setLength(0);
        if (string != null) {
            this.buf.append(this.tab).append("// compiled from: ").append(string).append('\n');
        }
        if (string2 != null) {
            this.buf.append(this.tab).append("// debug info: ").append(string2).append('\n');
        }
        if (this.buf.length() > 0) {
            this.text.add(this.buf.toString());
        }
    }

    public void visitOuterClass(String string, String string2, String string3) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("OUTERCLASS ");
        this.appendDescriptor(0, string);
        this.buf.append(' ');
        if (string2 != null) {
            this.buf.append(string2).append(' ');
        }
        this.appendDescriptor(3, string3);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public Textifier visitClassAnnotation(String string, boolean bl) {
        this.text.add("\n");
        return this.visitAnnotation(string, bl);
    }

    public Printer visitClassTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        this.text.add("\n");
        return this.visitTypeAnnotation(n, typePath, string, bl);
    }

    public void visitClassAttribute(Attribute attribute) {
        this.text.add("\n");
        this.visitAttribute(attribute);
    }

    public void visitInnerClass(String string, String string2, String string3, int n) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("// access flags 0x");
        this.buf.append(Integer.toHexString(n & 0xFFFFFFDF).toUpperCase()).append('\n');
        this.buf.append(this.tab);
        this.appendAccess(n);
        this.buf.append("INNERCLASS ");
        this.appendDescriptor(0, string);
        this.buf.append(' ');
        this.appendDescriptor(0, string2);
        this.buf.append(' ');
        this.appendDescriptor(0, string3);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public Textifier visitField(int n, String string, String string2, String string3, Object object) {
        Object object2;
        this.buf.setLength(0);
        this.buf.append('\n');
        if ((n & 0x20000) != 0) {
            this.buf.append(this.tab).append("// DEPRECATED\n");
        }
        this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(n).toUpperCase()).append('\n');
        if (string3 != null) {
            this.buf.append(this.tab);
            this.appendDescriptor(2, string3);
            object2 = new TraceSignatureVisitor(0);
            SignatureReader signatureReader = new SignatureReader(string3);
            signatureReader.acceptType((SignatureVisitor)object2);
            this.buf.append(this.tab).append("// declaration: ").append(((TraceSignatureVisitor)object2).getDeclaration()).append('\n');
        }
        this.buf.append(this.tab);
        this.appendAccess(n);
        this.appendDescriptor(1, string2);
        this.buf.append(' ').append(string);
        if (object != null) {
            this.buf.append(" = ");
            if (object instanceof String) {
                this.buf.append('\"').append(object).append('\"');
            } else {
                this.buf.append(object);
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
        object2 = this.createTextifier();
        this.text.add(((Printer)object2).getText());
        return object2;
    }

    public Textifier visitMethod(int n, String string, String string2, String string3, String[] stringArray) {
        Object object;
        this.buf.setLength(0);
        this.buf.append('\n');
        if ((n & 0x20000) != 0) {
            this.buf.append(this.tab).append("// DEPRECATED\n");
        }
        this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(n).toUpperCase()).append('\n');
        if (string3 != null) {
            this.buf.append(this.tab);
            this.appendDescriptor(4, string3);
            object = new TraceSignatureVisitor(0);
            SignatureReader signatureReader = new SignatureReader(string3);
            signatureReader.accept((SignatureVisitor)object);
            String string4 = ((TraceSignatureVisitor)object).getDeclaration();
            String string5 = ((TraceSignatureVisitor)object).getReturnType();
            String string6 = ((TraceSignatureVisitor)object).getExceptions();
            this.buf.append(this.tab).append("// declaration: ").append(string5).append(' ').append(string).append(string4);
            if (string6 != null) {
                this.buf.append(" throws ").append(string6);
            }
            this.buf.append('\n');
        }
        this.buf.append(this.tab);
        this.appendAccess(n & 0xFFFFFFBF);
        if ((n & 0x100) != 0) {
            this.buf.append("native ");
        }
        if ((n & 0x80) != 0) {
            this.buf.append("varargs ");
        }
        if ((n & 0x40) != 0) {
            this.buf.append("bridge ");
        }
        if ((this.access & 0x200) != 0 && (n & 0x400) == 0 && (n & 8) == 0) {
            this.buf.append("default ");
        }
        this.buf.append(string);
        this.appendDescriptor(3, string2);
        if (stringArray != null && stringArray.length > 0) {
            this.buf.append(" throws ");
            for (int i = 0; i < stringArray.length; ++i) {
                this.appendDescriptor(0, stringArray[i]);
                this.buf.append(' ');
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
        object = this.createTextifier();
        this.text.add(((Printer)object).getText());
        return object;
    }

    public void visitClassEnd() {
        this.text.add("}\n");
    }

    public void visit(String string, Object object) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (string != null) {
            this.buf.append(string).append('=');
        }
        if (object instanceof String) {
            this.visitString((String)object);
        } else if (object instanceof Type) {
            this.visitType((Type)object);
        } else if (object instanceof Byte) {
            this.visitByte((Byte)object);
        } else if (object instanceof Boolean) {
            this.visitBoolean((Boolean)object);
        } else if (object instanceof Short) {
            this.visitShort((Short)object);
        } else if (object instanceof Character) {
            this.visitChar(((Character)object).charValue());
        } else if (object instanceof Integer) {
            this.visitInt((Integer)object);
        } else if (object instanceof Float) {
            this.visitFloat(((Float)object).floatValue());
        } else if (object instanceof Long) {
            this.visitLong((Long)object);
        } else if (object instanceof Double) {
            this.visitDouble((Double)object);
        } else if (object.getClass().isArray()) {
            this.buf.append('{');
            if (object instanceof byte[]) {
                byte[] byArray = (byte[])object;
                for (int i = 0; i < byArray.length; ++i) {
                    this.appendComa(i);
                    this.visitByte(byArray[i]);
                }
            } else if (object instanceof boolean[]) {
                boolean[] blArray = (boolean[])object;
                for (int i = 0; i < blArray.length; ++i) {
                    this.appendComa(i);
                    this.visitBoolean(blArray[i]);
                }
            } else if (object instanceof short[]) {
                short[] sArray = (short[])object;
                for (int i = 0; i < sArray.length; ++i) {
                    this.appendComa(i);
                    this.visitShort(sArray[i]);
                }
            } else if (object instanceof char[]) {
                char[] cArray = (char[])object;
                for (int i = 0; i < cArray.length; ++i) {
                    this.appendComa(i);
                    this.visitChar(cArray[i]);
                }
            } else if (object instanceof int[]) {
                int[] nArray = (int[])object;
                for (int i = 0; i < nArray.length; ++i) {
                    this.appendComa(i);
                    this.visitInt(nArray[i]);
                }
            } else if (object instanceof long[]) {
                long[] lArray = (long[])object;
                for (int i = 0; i < lArray.length; ++i) {
                    this.appendComa(i);
                    this.visitLong(lArray[i]);
                }
            } else if (object instanceof float[]) {
                float[] fArray = (float[])object;
                for (int i = 0; i < fArray.length; ++i) {
                    this.appendComa(i);
                    this.visitFloat(fArray[i]);
                }
            } else if (object instanceof double[]) {
                double[] dArray = (double[])object;
                for (int i = 0; i < dArray.length; ++i) {
                    this.appendComa(i);
                    this.visitDouble(dArray[i]);
                }
            }
            this.buf.append('}');
        }
        this.text.add(this.buf.toString());
    }

    private void visitInt(int n) {
        this.buf.append(n);
    }

    private void visitLong(long l2) {
        this.buf.append(l2).append('L');
    }

    private void visitFloat(float f) {
        this.buf.append(f).append('F');
    }

    private void visitDouble(double d) {
        this.buf.append(d).append('D');
    }

    private void visitChar(char c) {
        this.buf.append("(char)").append((int)c);
    }

    private void visitShort(short s) {
        this.buf.append("(short)").append(s);
    }

    private void visitByte(byte by) {
        this.buf.append("(byte)").append(by);
    }

    private void visitBoolean(boolean bl) {
        this.buf.append(bl);
    }

    private void visitString(String string) {
        Textifier.appendString(this.buf, string);
    }

    private void visitType(Type type) {
        this.buf.append(type.getClassName()).append(".class");
    }

    public void visitEnum(String string, String string2, String string3) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (string != null) {
            this.buf.append(string).append('=');
        }
        this.appendDescriptor(1, string2);
        this.buf.append('.').append(string3);
        this.text.add(this.buf.toString());
    }

    public Textifier visitAnnotation(String string, String string2) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (string != null) {
            this.buf.append(string).append('=');
        }
        this.buf.append('@');
        this.appendDescriptor(1, string2);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier textifier = this.createTextifier();
        this.text.add(textifier.getText());
        this.text.add(")");
        return textifier;
    }

    public Textifier visitArray(String string) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (string != null) {
            this.buf.append(string).append('=');
        }
        this.buf.append('{');
        this.text.add(this.buf.toString());
        Textifier textifier = this.createTextifier();
        this.text.add(textifier.getText());
        this.text.add("}");
        return textifier;
    }

    public void visitAnnotationEnd() {
    }

    public Textifier visitFieldAnnotation(String string, boolean bl) {
        return this.visitAnnotation(string, bl);
    }

    public Printer visitFieldTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation(n, typePath, string, bl);
    }

    public void visitFieldAttribute(Attribute attribute) {
        this.visitAttribute(attribute);
    }

    public void visitFieldEnd() {
    }

    public void visitParameter(String string, int n) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("// parameter ");
        this.appendAccess(n);
        this.buf.append(' ').append(string == null ? "<no name>" : string).append('\n');
        this.text.add(this.buf.toString());
    }

    public Textifier visitAnnotationDefault() {
        this.text.add(this.tab2 + "default=");
        Textifier textifier = this.createTextifier();
        this.text.add(textifier.getText());
        this.text.add("\n");
        return textifier;
    }

    public Textifier visitMethodAnnotation(String string, boolean bl) {
        return this.visitAnnotation(string, bl);
    }

    public Printer visitMethodTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation(n, typePath, string, bl);
    }

    public Textifier visitParameterAnnotation(int n, String string, boolean bl) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append('@');
        this.appendDescriptor(1, string);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier textifier = this.createTextifier();
        this.text.add(textifier.getText());
        this.text.add(bl ? ") // parameter " : ") // invisible, parameter ");
        this.text.add(new Integer(n));
        this.text.add("\n");
        return textifier;
    }

    public void visitMethodAttribute(Attribute attribute) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("ATTRIBUTE ");
        this.appendDescriptor(-1, attribute.type);
        if (attribute instanceof Textifiable) {
            ((Textifiable)((Object)attribute)).textify(this.buf, this.labelNames);
        } else {
            this.buf.append(" : unknown\n");
        }
        this.text.add(this.buf.toString());
    }

    public void visitCode() {
    }

    public void visitFrame(int n, int n2, Object[] objectArray, int n3, Object[] objectArray2) {
        this.buf.setLength(0);
        this.buf.append(this.ltab);
        this.buf.append("FRAME ");
        switch (n) {
            case -1: 
            case 0: {
                this.buf.append("FULL [");
                this.appendFrameTypes(n2, objectArray);
                this.buf.append("] [");
                this.appendFrameTypes(n3, objectArray2);
                this.buf.append(']');
                break;
            }
            case 1: {
                this.buf.append("APPEND [");
                this.appendFrameTypes(n2, objectArray);
                this.buf.append(']');
                break;
            }
            case 2: {
                this.buf.append("CHOP ").append(n2);
                break;
            }
            case 3: {
                this.buf.append("SAME");
                break;
            }
            case 4: {
                this.buf.append("SAME1 ");
                this.appendFrameTypes(1, objectArray2);
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitInsn(int n) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[n]).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitIntInsn(int n, int n2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[n]).append(' ').append(n == 188 ? TYPES[n2] : Integer.toString(n2)).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitVarInsn(int n, int n2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[n]).append(' ').append(n2).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitTypeInsn(int n, String string) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[n]).append(' ');
        this.appendDescriptor(0, string);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitFieldInsn(int n, String string, String string2, String string3) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[n]).append(' ');
        this.appendDescriptor(0, string);
        this.buf.append('.').append(string2).append(" : ");
        this.appendDescriptor(1, string3);
        this.buf.append('\n');
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
        this.buf.append(this.tab2).append(OPCODES[n]).append(' ');
        this.appendDescriptor(0, string);
        this.buf.append('.').append(string2).append(' ');
        this.appendDescriptor(3, string3);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitInvokeDynamicInsn(String string, String string2, Handle handle, Object ... objectArray) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("INVOKEDYNAMIC").append(' ');
        this.buf.append(string);
        this.appendDescriptor(3, string2);
        this.buf.append(" [");
        this.buf.append('\n');
        this.buf.append(this.tab3);
        this.appendHandle(handle);
        this.buf.append('\n');
        this.buf.append(this.tab3).append("// arguments:");
        if (objectArray.length == 0) {
            this.buf.append(" none");
        } else {
            this.buf.append('\n');
            for (int i = 0; i < objectArray.length; ++i) {
                this.buf.append(this.tab3);
                Object object = objectArray[i];
                if (object instanceof String) {
                    Printer.appendString(this.buf, (String)object);
                } else if (object instanceof Type) {
                    Type type = (Type)object;
                    if (type.getSort() == 11) {
                        this.appendDescriptor(3, type.getDescriptor());
                    } else {
                        this.buf.append(type.getDescriptor()).append(".class");
                    }
                } else if (object instanceof Handle) {
                    this.appendHandle((Handle)object);
                } else {
                    this.buf.append(object);
                }
                this.buf.append(", \n");
            }
            this.buf.setLength(this.buf.length() - 3);
        }
        this.buf.append('\n');
        this.buf.append(this.tab2).append("]\n");
        this.text.add(this.buf.toString());
    }

    public void visitJumpInsn(int n, Label label) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(OPCODES[n]).append(' ');
        this.appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitLabel(Label label) {
        this.buf.setLength(0);
        this.buf.append(this.ltab);
        this.appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitLdcInsn(Object object) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LDC ");
        if (object instanceof String) {
            Printer.appendString(this.buf, (String)object);
        } else if (object instanceof Type) {
            this.buf.append(((Type)object).getDescriptor()).append(".class");
        } else {
            this.buf.append(object);
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitIincInsn(int n, int n2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("IINC ").append(n).append(' ').append(n2).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitTableSwitchInsn(int n, int n2, Label label, Label ... labelArray) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TABLESWITCH\n");
        for (int i = 0; i < labelArray.length; ++i) {
            this.buf.append(this.tab3).append(n + i).append(": ");
            this.appendLabel(labelArray[i]);
            this.buf.append('\n');
        }
        this.buf.append(this.tab3).append("default: ");
        this.appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitLookupSwitchInsn(Label label, int[] nArray, Label[] labelArray) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOOKUPSWITCH\n");
        for (int i = 0; i < labelArray.length; ++i) {
            this.buf.append(this.tab3).append(nArray[i]).append(": ");
            this.appendLabel(labelArray[i]);
            this.buf.append('\n');
        }
        this.buf.append(this.tab3).append("default: ");
        this.appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitMultiANewArrayInsn(String string, int n) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MULTIANEWARRAY ");
        this.appendDescriptor(1, string);
        this.buf.append(' ').append(n).append('\n');
        this.text.add(this.buf.toString());
    }

    public Printer visitInsnAnnotation(int n, TypePath typePath, String string, boolean bl) {
        return this.visitTypeAnnotation(n, typePath, string, bl);
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TRYCATCHBLOCK ");
        this.appendLabel(label);
        this.buf.append(' ');
        this.appendLabel(label2);
        this.buf.append(' ');
        this.appendLabel(label3);
        this.buf.append(' ');
        this.appendDescriptor(0, string);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public Printer visitTryCatchAnnotation(int n, TypePath typePath, String string, boolean bl) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TRYCATCHBLOCK @");
        this.appendDescriptor(1, string);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier textifier = this.createTextifier();
        this.text.add(textifier.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(n);
        this.buf.append(", ").append(typePath);
        this.buf.append(bl ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return textifier;
    }

    public void visitLocalVariable(String string, String string2, String string3, Label label, Label label2, int n) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOCALVARIABLE ").append(string).append(' ');
        this.appendDescriptor(1, string2);
        this.buf.append(' ');
        this.appendLabel(label);
        this.buf.append(' ');
        this.appendLabel(label2);
        this.buf.append(' ').append(n).append('\n');
        if (string3 != null) {
            this.buf.append(this.tab2);
            this.appendDescriptor(2, string3);
            TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(0);
            SignatureReader signatureReader = new SignatureReader(string3);
            signatureReader.acceptType(traceSignatureVisitor);
            this.buf.append(this.tab2).append("// declaration: ").append(traceSignatureVisitor.getDeclaration()).append('\n');
        }
        this.text.add(this.buf.toString());
    }

    public Printer visitLocalVariableAnnotation(int n, TypePath typePath, Label[] labelArray, Label[] labelArray2, int[] nArray, String string, boolean bl) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOCALVARIABLE @");
        this.appendDescriptor(1, string);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier textifier = this.createTextifier();
        this.text.add(textifier.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(n);
        this.buf.append(", ").append(typePath);
        for (int i = 0; i < labelArray.length; ++i) {
            this.buf.append(" [ ");
            this.appendLabel(labelArray[i]);
            this.buf.append(" - ");
            this.appendLabel(labelArray2[i]);
            this.buf.append(" - ").append(nArray[i]).append(" ]");
        }
        this.buf.append(bl ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return textifier;
    }

    public void visitLineNumber(int n, Label label) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LINENUMBER ").append(n).append(' ');
        this.appendLabel(label);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitMaxs(int n, int n2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MAXSTACK = ").append(n).append('\n');
        this.text.add(this.buf.toString());
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MAXLOCALS = ").append(n2).append('\n');
        this.text.add(this.buf.toString());
    }

    public void visitMethodEnd() {
    }

    public Textifier visitAnnotation(String string, boolean bl) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append('@');
        this.appendDescriptor(1, string);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier textifier = this.createTextifier();
        this.text.add(textifier.getText());
        this.text.add(bl ? ")\n" : ") // invisible\n");
        return textifier;
    }

    public Textifier visitTypeAnnotation(int n, TypePath typePath, String string, boolean bl) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append('@');
        this.appendDescriptor(1, string);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        Textifier textifier = this.createTextifier();
        this.text.add(textifier.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(n);
        this.buf.append(", ").append(typePath);
        this.buf.append(bl ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return textifier;
    }

    public void visitAttribute(Attribute attribute) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("ATTRIBUTE ");
        this.appendDescriptor(-1, attribute.type);
        if (attribute instanceof Textifiable) {
            ((Textifiable)((Object)attribute)).textify(this.buf, null);
        } else {
            this.buf.append(" : unknown\n");
        }
        this.text.add(this.buf.toString());
    }

    protected Textifier createTextifier() {
        return new Textifier();
    }

    protected void appendDescriptor(int n, String string) {
        if (n == 5 || n == 2 || n == 4) {
            if (string != null) {
                this.buf.append("// signature ").append(string).append('\n');
            }
        } else {
            this.buf.append(string);
        }
    }

    protected void appendLabel(Label label) {
        String string;
        if (this.labelNames == null) {
            this.labelNames = new HashMap();
        }
        if ((string = (String)this.labelNames.get(label)) == null) {
            string = "L" + this.labelNames.size();
            this.labelNames.put(label, string);
        }
        this.buf.append(string);
    }

    protected void appendHandle(Handle handle) {
        int n = handle.getTag();
        this.buf.append("// handle kind 0x").append(Integer.toHexString(n)).append(" : ");
        boolean bl = false;
        switch (n) {
            case 1: {
                this.buf.append("GETFIELD");
                break;
            }
            case 2: {
                this.buf.append("GETSTATIC");
                break;
            }
            case 3: {
                this.buf.append("PUTFIELD");
                break;
            }
            case 4: {
                this.buf.append("PUTSTATIC");
                break;
            }
            case 9: {
                this.buf.append("INVOKEINTERFACE");
                bl = true;
                break;
            }
            case 7: {
                this.buf.append("INVOKESPECIAL");
                bl = true;
                break;
            }
            case 6: {
                this.buf.append("INVOKESTATIC");
                bl = true;
                break;
            }
            case 5: {
                this.buf.append("INVOKEVIRTUAL");
                bl = true;
                break;
            }
            case 8: {
                this.buf.append("NEWINVOKESPECIAL");
                bl = true;
            }
        }
        this.buf.append('\n');
        this.buf.append(this.tab3);
        this.appendDescriptor(0, handle.getOwner());
        this.buf.append('.');
        this.buf.append(handle.getName());
        if (!bl) {
            this.buf.append('(');
        }
        this.appendDescriptor(9, handle.getDesc());
        if (!bl) {
            this.buf.append(')');
        }
    }

    private void appendAccess(int n) {
        if ((n & 1) != 0) {
            this.buf.append("public ");
        }
        if ((n & 2) != 0) {
            this.buf.append("private ");
        }
        if ((n & 4) != 0) {
            this.buf.append("protected ");
        }
        if ((n & 0x10) != 0) {
            this.buf.append("final ");
        }
        if ((n & 8) != 0) {
            this.buf.append("static ");
        }
        if ((n & 0x20) != 0) {
            this.buf.append("synchronized ");
        }
        if ((n & 0x40) != 0) {
            this.buf.append("volatile ");
        }
        if ((n & 0x80) != 0) {
            this.buf.append("transient ");
        }
        if ((n & 0x400) != 0) {
            this.buf.append("abstract ");
        }
        if ((n & 0x800) != 0) {
            this.buf.append("strictfp ");
        }
        if ((n & 0x1000) != 0) {
            this.buf.append("synthetic ");
        }
        if ((n & 0x8000) != 0) {
            this.buf.append("mandated ");
        }
        if ((n & 0x4000) != 0) {
            this.buf.append("enum ");
        }
    }

    private void appendComa(int n) {
        if (n != 0) {
            this.buf.append(", ");
        }
    }

    private void appendTypeReference(int n) {
        TypeReference typeReference = new TypeReference(n);
        switch (typeReference.getSort()) {
            case 0: {
                this.buf.append("CLASS_TYPE_PARAMETER ").append(typeReference.getTypeParameterIndex());
                break;
            }
            case 1: {
                this.buf.append("METHOD_TYPE_PARAMETER ").append(typeReference.getTypeParameterIndex());
                break;
            }
            case 16: {
                this.buf.append("CLASS_EXTENDS ").append(typeReference.getSuperTypeIndex());
                break;
            }
            case 17: {
                this.buf.append("CLASS_TYPE_PARAMETER_BOUND ").append(typeReference.getTypeParameterIndex()).append(", ").append(typeReference.getTypeParameterBoundIndex());
                break;
            }
            case 18: {
                this.buf.append("METHOD_TYPE_PARAMETER_BOUND ").append(typeReference.getTypeParameterIndex()).append(", ").append(typeReference.getTypeParameterBoundIndex());
                break;
            }
            case 19: {
                this.buf.append("FIELD");
                break;
            }
            case 20: {
                this.buf.append("METHOD_RETURN");
                break;
            }
            case 21: {
                this.buf.append("METHOD_RECEIVER");
                break;
            }
            case 22: {
                this.buf.append("METHOD_FORMAL_PARAMETER ").append(typeReference.getFormalParameterIndex());
                break;
            }
            case 23: {
                this.buf.append("THROWS ").append(typeReference.getExceptionIndex());
                break;
            }
            case 64: {
                this.buf.append("LOCAL_VARIABLE");
                break;
            }
            case 65: {
                this.buf.append("RESOURCE_VARIABLE");
                break;
            }
            case 66: {
                this.buf.append("EXCEPTION_PARAMETER ").append(typeReference.getTryCatchBlockIndex());
                break;
            }
            case 67: {
                this.buf.append("INSTANCEOF");
                break;
            }
            case 68: {
                this.buf.append("NEW");
                break;
            }
            case 69: {
                this.buf.append("CONSTRUCTOR_REFERENCE");
                break;
            }
            case 70: {
                this.buf.append("METHOD_REFERENCE");
                break;
            }
            case 71: {
                this.buf.append("CAST ").append(typeReference.getTypeArgumentIndex());
                break;
            }
            case 72: {
                this.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(typeReference.getTypeArgumentIndex());
                break;
            }
            case 73: {
                this.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(typeReference.getTypeArgumentIndex());
                break;
            }
            case 74: {
                this.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(typeReference.getTypeArgumentIndex());
                break;
            }
            case 75: {
                this.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(typeReference.getTypeArgumentIndex());
            }
        }
    }

    private void appendFrameTypes(int n, Object[] objectArray) {
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                this.buf.append(' ');
            }
            if (objectArray[i] instanceof String) {
                String string = (String)objectArray[i];
                if (string.startsWith("[")) {
                    this.appendDescriptor(1, string);
                    continue;
                }
                this.appendDescriptor(0, string);
                continue;
            }
            if (objectArray[i] instanceof Integer) {
                switch ((Integer)objectArray[i]) {
                    case 0: {
                        this.appendDescriptor(1, "T");
                        break;
                    }
                    case 1: {
                        this.appendDescriptor(1, "I");
                        break;
                    }
                    case 2: {
                        this.appendDescriptor(1, "F");
                        break;
                    }
                    case 3: {
                        this.appendDescriptor(1, "D");
                        break;
                    }
                    case 4: {
                        this.appendDescriptor(1, "J");
                        break;
                    }
                    case 5: {
                        this.appendDescriptor(1, "N");
                        break;
                    }
                    case 6: {
                        this.appendDescriptor(1, "U");
                    }
                }
                continue;
            }
            this.appendLabel((Label)objectArray[i]);
        }
    }

    public Printer visitParameterAnnotation(int n, String string, boolean bl) {
        return this.visitParameterAnnotation(n, string, bl);
    }

    public Printer visitMethodAnnotation(String string, boolean bl) {
        return this.visitMethodAnnotation(string, bl);
    }

    public Printer visitAnnotationDefault() {
        return this.visitAnnotationDefault();
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

    public Printer visitClassAnnotation(String string, boolean bl) {
        return this.visitClassAnnotation(string, bl);
    }
}

