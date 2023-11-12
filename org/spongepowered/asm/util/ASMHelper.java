/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 *  com.google.common.primitives.Ints
 */
package org.spongepowered.asm.util;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.IntInsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.lib.util.TraceClassVisitor;

public class ASMHelper {
    public static final int[] CONSTANTS_INT = new int[]{2, 3, 4, 5, 6, 7, 8};
    public static final int[] CONSTANTS_FLOAT = new int[]{11, 12, 13};
    public static final int[] CONSTANTS_DOUBLE = new int[]{14, 15};
    public static final int[] CONSTANTS_LONG = new int[]{9, 10};
    public static final int[] CONSTANTS_ALL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
    private static final Object[] CONSTANTS_VALUES = new Object[]{null, -1, 0, 1, 2, 3, 4, 5, 0L, 1L, Float.valueOf(0.0f), Float.valueOf(1.0f), Float.valueOf(2.0f), 0.0, 1.0};
    private static final String[] CONSTANTS_TYPES = new String[]{null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I"};

    public static MethodNode findMethod(ClassNode classNode, String string, String string2) {
        for (MethodNode methodNode : classNode.methods) {
            if (!methodNode.name.equals(string) || !methodNode.desc.equals(string2)) continue;
            return methodNode;
        }
        return null;
    }

    public static void textify(ClassNode classNode, OutputStream outputStream) {
        classNode.accept(new TraceClassVisitor(new PrintWriter(outputStream)));
    }

    public static void textify(MethodNode methodNode, OutputStream outputStream) {
        TraceClassVisitor traceClassVisitor = new TraceClassVisitor(new PrintWriter(outputStream));
        MethodVisitor methodVisitor = traceClassVisitor.visitMethod(methodNode.access, methodNode.name, methodNode.desc, methodNode.signature, methodNode.exceptions.toArray(new String[0]));
        methodNode.accept(methodVisitor);
        traceClassVisitor.visitEnd();
    }

    public static void dumpClass(ClassNode classNode) {
        ClassWriter classWriter = new ClassWriter(3);
        classNode.accept(classWriter);
        ASMHelper.dumpClass(classWriter.toByteArray());
    }

    public static void dumpClass(byte[] byArray) {
        ClassReader classReader = new ClassReader(byArray);
        CheckClassAdapter.verify(classReader, true, new PrintWriter(System.out));
    }

    public static void printMethodWithOpcodeIndices(MethodNode methodNode) {
        System.err.printf("%s%s\n", methodNode.name, methodNode.desc);
        int n = 0;
        ListIterator listIterator = methodNode.instructions.iterator();
        while (listIterator.hasNext()) {
            System.err.printf("[%4d] %s\n", n++, ASMHelper.getNodeDescriptionForDebug((AbstractInsnNode)listIterator.next()));
        }
    }

    public static void printMethod(MethodNode methodNode) {
        System.err.printf("%s%s\n", methodNode.name, methodNode.desc);
        ListIterator listIterator = methodNode.instructions.iterator();
        while (listIterator.hasNext()) {
            System.err.print("  ");
            ASMHelper.printNode((AbstractInsnNode)listIterator.next());
        }
    }

    public static void printNode(AbstractInsnNode abstractInsnNode) {
        System.err.printf("%s\n", ASMHelper.getNodeDescriptionForDebug(abstractInsnNode));
    }

    public static String getNodeDescriptionForDebug(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode instanceof LabelNode) {
            return String.format("[%s]", ((LabelNode)abstractInsnNode).getLabel());
        }
        String string = String.format("   %-14s ", abstractInsnNode.getClass().getSimpleName().replace("Node", ""));
        if (abstractInsnNode instanceof JumpInsnNode) {
            string = string + String.format("[%s] [%s]", ASMHelper.getOpcodeName(abstractInsnNode), ((JumpInsnNode)abstractInsnNode).label.getLabel());
        } else if (abstractInsnNode instanceof VarInsnNode) {
            string = string + String.format("[%s] %d", ASMHelper.getOpcodeName(abstractInsnNode), ((VarInsnNode)abstractInsnNode).var);
        } else if (abstractInsnNode instanceof MethodInsnNode) {
            MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;
            string = string + String.format("[%s] %s %s %s", ASMHelper.getOpcodeName(abstractInsnNode), methodInsnNode.owner, methodInsnNode.name, methodInsnNode.desc);
        } else if (abstractInsnNode instanceof FieldInsnNode) {
            FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractInsnNode;
            string = string + String.format("[%s] %s %s %s", ASMHelper.getOpcodeName(abstractInsnNode), fieldInsnNode.owner, fieldInsnNode.name, fieldInsnNode.desc);
        } else if (abstractInsnNode instanceof LineNumberNode) {
            LineNumberNode lineNumberNode = (LineNumberNode)abstractInsnNode;
            string = string + String.format("LINE=[%d] LABEL=[%s]", lineNumberNode.line, lineNumberNode.start.getLabel());
        } else {
            string = abstractInsnNode instanceof LdcInsnNode ? string + ((LdcInsnNode)abstractInsnNode).cst : (abstractInsnNode instanceof IntInsnNode ? string + ((IntInsnNode)abstractInsnNode).operand : (abstractInsnNode instanceof FrameNode ? string + String.format("[%s] ", ASMHelper.getOpcodeName(((FrameNode)abstractInsnNode).type, "H_INVOKEINTERFACE", -1)) : string + String.format("[%s] ", ASMHelper.getOpcodeName(abstractInsnNode))));
        }
        return string;
    }

    public static String getOpcodeName(AbstractInsnNode abstractInsnNode) {
        return ASMHelper.getOpcodeName(abstractInsnNode.getOpcode());
    }

    public static String getOpcodeName(int n) {
        return ASMHelper.getOpcodeName(n, "UNINITIALIZED_THIS", 1);
    }

    private static String getOpcodeName(int n, String string, int n2) {
        if (n >= n2) {
            boolean bl = false;
            try {
                for (Field field : Opcodes.class.getDeclaredFields()) {
                    if (!bl && !field.getName().equals(string)) continue;
                    bl = true;
                    if (field.getType() != Integer.TYPE || field.getInt(null) != n) continue;
                    return field.getName();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return n >= 0 ? String.valueOf(n) : "UNKNOWN";
    }

    public static void setVisibleAnnotation(FieldNode fieldNode, Class clazz, Object ... objectArray) {
        AnnotationNode annotationNode = ASMHelper.makeAnnotationNode(Type.getDescriptor(clazz), objectArray);
        fieldNode.visibleAnnotations = ASMHelper.addAnnotation(fieldNode.visibleAnnotations, annotationNode);
    }

    public static void setInvisibleAnnotation(FieldNode fieldNode, Class clazz, Object ... objectArray) {
        AnnotationNode annotationNode = ASMHelper.makeAnnotationNode(Type.getDescriptor(clazz), objectArray);
        fieldNode.invisibleAnnotations = ASMHelper.addAnnotation(fieldNode.invisibleAnnotations, annotationNode);
    }

    public static void setVisibleAnnotation(MethodNode methodNode, Class clazz, Object ... objectArray) {
        AnnotationNode annotationNode = ASMHelper.makeAnnotationNode(Type.getDescriptor(clazz), objectArray);
        methodNode.visibleAnnotations = ASMHelper.addAnnotation(methodNode.visibleAnnotations, annotationNode);
    }

    public static void setInvisibleAnnotation(MethodNode methodNode, Class clazz, Object ... objectArray) {
        AnnotationNode annotationNode = ASMHelper.makeAnnotationNode(Type.getDescriptor(clazz), objectArray);
        methodNode.invisibleAnnotations = ASMHelper.addAnnotation(methodNode.invisibleAnnotations, annotationNode);
    }

    private static AnnotationNode makeAnnotationNode(String string, Object ... objectArray) {
        AnnotationNode annotationNode = new AnnotationNode(string);
        for (int i = 0; i < objectArray.length - 1; i += 2) {
            if (!(objectArray[i] instanceof String)) {
                throw new IllegalArgumentException("Annotation keys must be strings, found " + objectArray[i].getClass().getSimpleName() + " with " + objectArray[i].toString() + " at index " + i + " creating " + string);
            }
            annotationNode.visit((String)objectArray[i], objectArray[i + 1]);
        }
        return annotationNode;
    }

    private static List addAnnotation(List arrayList, AnnotationNode annotationNode) {
        if (arrayList == null) {
            arrayList = new ArrayList<AnnotationNode>(1);
        } else {
            arrayList.remove(ASMHelper.getAnnotation(arrayList, annotationNode.desc));
        }
        arrayList.add(annotationNode);
        return arrayList;
    }

    public static AnnotationNode getVisibleAnnotation(FieldNode fieldNode, Class clazz) {
        return ASMHelper.getAnnotation(fieldNode.visibleAnnotations, Type.getDescriptor(clazz));
    }

    public static AnnotationNode getInvisibleAnnotation(FieldNode fieldNode, Class clazz) {
        return ASMHelper.getAnnotation(fieldNode.invisibleAnnotations, Type.getDescriptor(clazz));
    }

    public static AnnotationNode getVisibleAnnotation(MethodNode methodNode, Class clazz) {
        return ASMHelper.getAnnotation(methodNode.visibleAnnotations, Type.getDescriptor(clazz));
    }

    public static AnnotationNode getInvisibleAnnotation(MethodNode methodNode, Class clazz) {
        return ASMHelper.getAnnotation(methodNode.invisibleAnnotations, Type.getDescriptor(clazz));
    }

    public static AnnotationNode getSingleVisibleAnnotation(MethodNode methodNode, Class ... classArray) {
        return ASMHelper.getSingleAnnotation(methodNode.visibleAnnotations, classArray);
    }

    public static AnnotationNode getSingleInvisibleAnnotation(MethodNode methodNode, Class ... classArray) {
        return ASMHelper.getSingleAnnotation(methodNode.invisibleAnnotations, classArray);
    }

    public static AnnotationNode getVisibleAnnotation(ClassNode classNode, Class clazz) {
        return ASMHelper.getAnnotation(classNode.visibleAnnotations, Type.getDescriptor(clazz));
    }

    public static AnnotationNode getInvisibleAnnotation(ClassNode classNode, Class clazz) {
        return ASMHelper.getAnnotation(classNode.invisibleAnnotations, Type.getDescriptor(clazz));
    }

    public static AnnotationNode getVisibleParameterAnnotation(MethodNode methodNode, Class clazz, int n) {
        return ASMHelper.getParameterAnnotation(methodNode.visibleParameterAnnotations, Type.getDescriptor(clazz), n);
    }

    public static AnnotationNode getInvisibleParameterAnnotation(MethodNode methodNode, Class clazz, int n) {
        return ASMHelper.getParameterAnnotation(methodNode.invisibleParameterAnnotations, Type.getDescriptor(clazz), n);
    }

    public static AnnotationNode getParameterAnnotation(List[] listArray, String string, int n) {
        if (listArray == null || n < 0 || n >= listArray.length) {
            return null;
        }
        return ASMHelper.getAnnotation(listArray[n], string);
    }

    public static AnnotationNode getAnnotation(List list, String string) {
        if (list == null) {
            return null;
        }
        for (AnnotationNode annotationNode : list) {
            if (!string.equals(annotationNode.desc)) continue;
            return annotationNode;
        }
        return null;
    }

    private static AnnotationNode getSingleAnnotation(List list, Class[] classArray) {
        ArrayList<AnnotationNode> arrayList = new ArrayList<AnnotationNode>();
        for (Class clazz : classArray) {
            AnnotationNode annotationNode = ASMHelper.getAnnotation(list, Type.getDescriptor(clazz));
            if (annotationNode == null) continue;
            arrayList.add(annotationNode);
        }
        int n = arrayList.size();
        if (n > 1) {
            throw new IllegalArgumentException("Conflicting annotations found: " + classArray);
        }
        return n == 0 ? null : (AnnotationNode)arrayList.get(0);
    }

    public static Object getAnnotationValue(AnnotationNode annotationNode) {
        return ASMHelper.getAnnotationValue(annotationNode, "value");
    }

    public static Object getAnnotationValue(AnnotationNode annotationNode, String string, Object object) {
        Object object2 = ASMHelper.getAnnotationValue(annotationNode, string);
        return object2 != null ? object2 : object;
    }

    public static Object getAnnotationValue(AnnotationNode annotationNode, String string, Class clazz) {
        Preconditions.checkNotNull((Object)clazz, (Object)"annotationClass cannot be null");
        Object object = ASMHelper.getAnnotationValue(annotationNode, string);
        if (object == null) {
            try {
                object = clazz.getDeclaredMethod(string, new Class[0]).getDefaultValue();
            }
            catch (NoSuchMethodException noSuchMethodException) {
                // empty catch block
            }
        }
        return object;
    }

    public static Object getAnnotationValue(AnnotationNode annotationNode, String string) {
        boolean bl = false;
        if (annotationNode == null || annotationNode.values == null) {
            return null;
        }
        for (Object e : annotationNode.values) {
            if (bl) {
                return e;
            }
            if (!e.equals(string)) continue;
            bl = true;
        }
        return null;
    }

    public static Enum getAnnotationValue(AnnotationNode annotationNode, String string, Class clazz, Enum enum_) {
        String[] stringArray = (String[])ASMHelper.getAnnotationValue(annotationNode, string);
        if (stringArray == null) {
            return enum_;
        }
        if (!clazz.getName().equals(Type.getType(stringArray[0]).getClassName())) {
            throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
        }
        return Enum.valueOf(clazz, stringArray[1]);
    }

    public static boolean methodIsStatic(MethodNode methodNode) {
        return (methodNode.access & 8) == 8;
    }

    public static boolean fieldIsStatic(FieldNode fieldNode) {
        return (fieldNode.access & 8) == 8;
    }

    public static int getFirstNonArgLocalIndex(MethodNode methodNode) {
        return ASMHelper.getFirstNonArgLocalIndex(Type.getArgumentTypes(methodNode.desc), (methodNode.access & 8) == 0);
    }

    public static int getFirstNonArgLocalIndex(Type[] typeArray, boolean bl) {
        return ASMHelper.getArgsSize(typeArray) + (bl ? 1 : 0);
    }

    public static int getArgsSize(Type[] typeArray) {
        int n = 0;
        for (Type type : typeArray) {
            n += type.getSize();
        }
        return n;
    }

    public static void loadArgs(Type[] typeArray, InsnList insnList, int n) {
        ASMHelper.loadArgs(typeArray, insnList, n, -1);
    }

    public static void loadArgs(Type[] typeArray, InsnList insnList, int n, int n2) {
        int n3 = n;
        for (Type type : typeArray) {
            insnList.add(new VarInsnNode(type.getOpcode(21), n3));
            if (n2 < n || (n3 += type.getSize()) < n2) continue;
            return;
        }
    }

    public static Map cloneLabels(InsnList insnList) {
        HashMap<LabelNode, LabelNode> hashMap = new HashMap<LabelNode, LabelNode>();
        ListIterator listIterator = insnList.iterator();
        while (listIterator.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
            if (!(abstractInsnNode instanceof LabelNode)) continue;
            hashMap.put((LabelNode)abstractInsnNode, new LabelNode(((LabelNode)abstractInsnNode).getLabel()));
        }
        return hashMap;
    }

    public static String generateDescriptor(Object object, Object ... objectArray) {
        StringBuilder stringBuilder = new StringBuilder().append('(');
        for (Object object2 : objectArray) {
            stringBuilder.append(ASMHelper.toDescriptor(object2));
        }
        return stringBuilder.append(')').append(object != null ? ASMHelper.toDescriptor(object) : "V").toString();
    }

    private static String toDescriptor(Object object) {
        if (object instanceof String) {
            return (String)object;
        }
        if (object instanceof Type) {
            return object.toString();
        }
        if (object instanceof Class) {
            return Type.getDescriptor((Class)object).toString();
        }
        return object == null ? "" : object.toString();
    }

    public static String getSimpleName(Class clazz) {
        return clazz.getSimpleName();
    }

    public static String getSimpleName(AnnotationNode annotationNode) {
        return ASMHelper.getSimpleName(annotationNode.desc);
    }

    public static String getSimpleName(String string) {
        return string.substring(string.lastIndexOf(47) + 1).replace(";", "");
    }

    public static boolean isConstant(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return false;
        }
        return Ints.contains((int[])CONSTANTS_ALL, (int)abstractInsnNode.getOpcode());
    }

    public static Object getConstant(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return null;
        }
        if (abstractInsnNode instanceof LdcInsnNode) {
            return ((LdcInsnNode)abstractInsnNode).cst;
        }
        if (abstractInsnNode instanceof IntInsnNode) {
            int n = ((IntInsnNode)abstractInsnNode).operand;
            if (abstractInsnNode.getOpcode() == 16 || abstractInsnNode.getOpcode() == 17) {
                return n;
            }
            throw new IllegalArgumentException("IntInsnNode with invalid opcode " + abstractInsnNode.getOpcode() + " in getConstant");
        }
        int n = Ints.indexOf((int[])CONSTANTS_ALL, (int)abstractInsnNode.getOpcode());
        return n < 0 ? null : CONSTANTS_VALUES[n];
    }

    public static Type getConstantType(AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode == null) {
            return null;
        }
        if (abstractInsnNode instanceof LdcInsnNode) {
            Object object = ((LdcInsnNode)abstractInsnNode).cst;
            if (object instanceof Integer) {
                return Type.getType("I");
            }
            if (object instanceof Float) {
                return Type.getType("F");
            }
            if (object instanceof Long) {
                return Type.getType("J");
            }
            if (object instanceof Double) {
                return Type.getType("D");
            }
            if (object instanceof String) {
                return Type.getType("Ljava/lang/String;");
            }
            if (object instanceof Type) {
                return Type.getType("Ljava/lang/Class;");
            }
            throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + object.getClass() + " in getConstant");
        }
        int n = Ints.indexOf((int[])CONSTANTS_ALL, (int)abstractInsnNode.getOpcode());
        return n < 0 ? null : Type.getType(CONSTANTS_TYPES[n]);
    }

    public static boolean hasFlag(ClassNode classNode, int n) {
        return (classNode.access & n) == n;
    }

    public static boolean hasFlag(MethodNode methodNode, int n) {
        return (methodNode.access & n) == n;
    }

    public static boolean hasFlag(FieldNode fieldNode, int n) {
        return (fieldNode.access & n) == n;
    }

    public static int getMaxLineNumber(ClassNode classNode, int n, int n2) {
        int n3 = 0;
        for (MethodNode methodNode : classNode.methods) {
            ListIterator listIterator = methodNode.instructions.iterator();
            while (listIterator.hasNext()) {
                AbstractInsnNode abstractInsnNode = (AbstractInsnNode)listIterator.next();
                if (!(abstractInsnNode instanceof LineNumberNode)) continue;
                n3 = Math.max(n3, ((LineNumberNode)abstractInsnNode).line);
            }
        }
        return Math.max(n, n3 + n2);
    }
}

