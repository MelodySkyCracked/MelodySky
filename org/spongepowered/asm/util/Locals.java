/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.verify.MixinVerifier;
import org.spongepowered.asm.util.throwables.LVTGeneratorException;

public class Locals {
    private static final Map calculatedLocalVariables = new HashMap();

    public static void loadLocals(Type[] typeArray, InsnList insnList, int n, int n2) {
        while (n < typeArray.length && n2 > 0) {
            if (typeArray[n] != null) {
                insnList.add(new VarInsnNode(typeArray[n].getOpcode(21), n));
                --n2;
            }
            ++n;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static LocalVariableNode[] getLocalsAt(ClassNode classNode, MethodNode methodNode, AbstractInsnNode abstractInsnNode) {
        void var12_17;
        for (int i = 0; i < 3 && (abstractInsnNode instanceof LabelNode || abstractInsnNode instanceof LineNumberNode); ++i) {
            abstractInsnNode = Locals.nextNode(methodNode.instructions, abstractInsnNode);
        }
        ClassInfo classInfo = ClassInfo.forName(classNode.name);
        if (classInfo == null) {
            throw new LVTGeneratorException("Could not load class metadata for " + classNode.name + " generating LVT for " + methodNode.name);
        }
        ClassInfo.Method method = classInfo.findMethod(methodNode);
        if (method == null) {
            throw new LVTGeneratorException("Could not locate method metadata for " + methodNode.name + " generating LVT in " + classNode.name);
        }
        List list = method.getFrames();
        LocalVariableNode[] localVariableNodeArray = new LocalVariableNode[methodNode.maxLocals];
        int n = 0;
        int n2 = 0;
        if ((methodNode.access & 8) == 0) {
            localVariableNodeArray[n++] = new LocalVariableNode("this", classNode.name, null, null, null, 0);
        }
        for (Type type : Type.getArgumentTypes(methodNode.desc)) {
            localVariableNodeArray[n] = new LocalVariableNode("arg" + n2++, type.toString(), null, null, null, n);
            n += type.getSize();
        }
        int n3 = n;
        int n4 = -1;
        int n5 = 0;
        ListIterator listIterator = methodNode.instructions.iterator();
        while (listIterator.hasNext()) {
            AbstractInsnNode abstractInsnNode2;
            AbstractInsnNode abstractInsnNode3 = (AbstractInsnNode)listIterator.next();
            if (abstractInsnNode3 instanceof FrameNode) {
                abstractInsnNode2 = (FrameNode)abstractInsnNode3;
                ClassInfo.FrameData frameData = ++n4 < list.size() ? (ClassInfo.FrameData)list.get(n4) : null;
                n5 = frameData != null && frameData.type == 0 ? Math.max(n5, abstractInsnNode2.local.size()) : abstractInsnNode2.local.size();
                int n6 = 0;
                int n7 = 0;
                while (n7 < localVariableNodeArray.length) {
                    Object var18_23;
                    Object v0 = var18_23 = n6 < abstractInsnNode2.local.size() ? abstractInsnNode2.local.get(n6) : null;
                    if (var18_23 instanceof String) {
                        localVariableNodeArray[n7] = Locals.getLocalVariableAt(classNode, methodNode, abstractInsnNode, n7);
                    } else if (var18_23 instanceof Integer) {
                        boolean bl;
                        boolean bl2 = var18_23 == Opcodes.UNINITIALIZED_THIS || var18_23 == Opcodes.NULL;
                        boolean bl3 = var18_23 == Opcodes.INTEGER || var18_23 == Opcodes.FLOAT;
                        boolean bl4 = bl = var18_23 == Opcodes.DOUBLE || var18_23 == Opcodes.LONG;
                        if (var18_23 != Opcodes.TOP) {
                            if (bl2) {
                                localVariableNodeArray[n7] = null;
                            } else {
                                if (!bl3 && !bl) throw new LVTGeneratorException("Unrecognised locals opcode " + var18_23 + " in locals array at position " + n6 + " in " + classNode.name + "." + methodNode.name + methodNode.desc);
                                localVariableNodeArray[n7] = Locals.getLocalVariableAt(classNode, methodNode, abstractInsnNode, n7);
                                if (bl) {
                                    localVariableNodeArray[++n7] = null;
                                }
                            }
                        }
                    } else {
                        if (var18_23 != null) throw new LVTGeneratorException("Invalid value " + var18_23 + " in locals array at position " + n6 + " in " + classNode.name + "." + methodNode.name + methodNode.desc);
                        if (n7 >= n3 && n7 >= n5 && n5 > 0) {
                            localVariableNodeArray[n7] = null;
                        }
                    }
                    ++n7;
                    ++n6;
                }
                continue;
            }
            if (abstractInsnNode3 instanceof VarInsnNode) {
                abstractInsnNode2 = (VarInsnNode)abstractInsnNode3;
                localVariableNodeArray[((VarInsnNode)abstractInsnNode2).var] = Locals.getLocalVariableAt(classNode, methodNode, abstractInsnNode, ((VarInsnNode)abstractInsnNode2).var);
                continue;
            }
            if (abstractInsnNode3 != abstractInsnNode) continue;
            break;
        }
        boolean bl = false;
        while (var12_17 < localVariableNodeArray.length) {
            if (localVariableNodeArray[var12_17] != null && localVariableNodeArray[var12_17].desc == null) {
                localVariableNodeArray[var12_17] = null;
            }
            ++var12_17;
        }
        return localVariableNodeArray;
    }

    public static LocalVariableNode getLocalVariableAt(ClassNode classNode, MethodNode methodNode, AbstractInsnNode abstractInsnNode, int n) {
        return Locals.getLocalVariableAt(classNode, methodNode, methodNode.instructions.indexOf(abstractInsnNode), n);
    }

    /*
     * Exception decompiling
     */
    private static LocalVariableNode getLocalVariableAt(ClassNode var0, MethodNode var1, int var2, int var3) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Invalid stack depths @ lbl10 : ALOAD - null : trying to set 1 previously set to 0
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:207)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op02WithProcessedDataAndRefs.populateStackInfo(Op02WithProcessedDataAndRefs.java:1559)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:434)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static List getLocalVariableTable(ClassNode classNode, MethodNode methodNode) {
        if (methodNode.localVariables.isEmpty()) {
            return Locals.getGeneratedLocalVariableTable(classNode, methodNode);
        }
        return methodNode.localVariables;
    }

    public static List getGeneratedLocalVariableTable(ClassNode classNode, MethodNode methodNode) {
        String string = String.format("%s.%s%s", classNode.name, methodNode.name, methodNode.desc);
        List list = (List)calculatedLocalVariables.get(string);
        if (list != null) {
            return list;
        }
        list = Locals.generateLocalVariableTable(classNode, methodNode);
        calculatedLocalVariables.put(string, list);
        return list;
    }

    public static List generateLocalVariableTable(ClassNode classNode, MethodNode methodNode) {
        int n;
        Object object2;
        ArrayList<Type> arrayList = null;
        if (classNode.interfaces != null) {
            arrayList = new ArrayList<Type>();
            for (Object object2 : classNode.interfaces) {
                arrayList.add(Type.getObjectType((String)object2));
            }
        }
        Object object3 = null;
        if (classNode.superName != null) {
            object3 = Type.getObjectType(classNode.superName);
        }
        object2 = new Analyzer(new MixinVerifier(Type.getObjectType(classNode.name), (Type)object3, arrayList, false));
        try {
            ((Analyzer)object2).analyze(classNode.name, methodNode);
        }
        catch (AnalyzerException analyzerException) {
            analyzerException.printStackTrace();
        }
        Frame[] frameArray = ((Analyzer)object2).getFrames();
        int n2 = methodNode.instructions.size();
        ArrayList<LocalVariableNode> arrayList2 = new ArrayList<LocalVariableNode>();
        LocalVariableNode[] localVariableNodeArray = new LocalVariableNode[methodNode.maxLocals];
        BasicValue[] basicValueArray = new BasicValue[methodNode.maxLocals];
        LabelNode[] labelNodeArray = new LabelNode[n2];
        String[] stringArray = new String[methodNode.maxLocals];
        for (int i = 0; i < n2; ++i) {
            Frame frame = frameArray[i];
            if (frame == null) continue;
            LabelNode labelNode = null;
            for (int j = 0; j < frame.getLocals(); ++j) {
                Object object4;
                BasicValue basicValue = (BasicValue)frame.getLocal(j);
                if (basicValue == null && basicValueArray[j] == null || basicValue != null && basicValue.equals(basicValueArray[j])) continue;
                if (labelNode == null) {
                    object4 = methodNode.instructions.get(i);
                    if (object4 instanceof LabelNode) {
                        labelNode = (LabelNode)object4;
                    } else {
                        labelNodeArray[i] = labelNode = new LabelNode();
                    }
                }
                if (basicValue == null && basicValueArray[j] != null) {
                    arrayList2.add(localVariableNodeArray[j]);
                    localVariableNodeArray[j].end = labelNode;
                    localVariableNodeArray[j] = null;
                } else if (basicValue != null) {
                    if (basicValueArray[j] != null) {
                        arrayList2.add(localVariableNodeArray[j]);
                        localVariableNodeArray[j].end = labelNode;
                        localVariableNodeArray[j] = null;
                    }
                    object4 = basicValue.getType() != null ? basicValue.getType().getDescriptor() : stringArray[j];
                    localVariableNodeArray[j] = new LocalVariableNode("var" + j, (String)object4, null, labelNode, null, j);
                    if (object4 != null) {
                        stringArray[j] = object4;
                    }
                }
                basicValueArray[j] = basicValue;
            }
        }
        LabelNode labelNode = null;
        for (n = 0; n < localVariableNodeArray.length; ++n) {
            if (localVariableNodeArray[n] == null) continue;
            if (labelNode == null) {
                labelNode = new LabelNode();
                methodNode.instructions.add(labelNode);
            }
            localVariableNodeArray[n].end = labelNode;
            arrayList2.add(localVariableNodeArray[n]);
        }
        for (n = n2 - 1; n >= 0; --n) {
            if (labelNodeArray[n] == null) continue;
            methodNode.instructions.insert(methodNode.instructions.get(n), labelNodeArray[n]);
        }
        return arrayList2;
    }

    private static AbstractInsnNode nextNode(InsnList insnList, AbstractInsnNode abstractInsnNode) {
        int n = insnList.indexOf(abstractInsnNode) + 1;
        if (n > 0 && n < insnList.size()) {
            return insnList.get(n);
        }
        return abstractInsnNode;
    }
}

