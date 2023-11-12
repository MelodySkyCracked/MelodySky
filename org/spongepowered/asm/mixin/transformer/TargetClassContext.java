/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer;

import java.io.OutputStream;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinApplicatorInterface;
import org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard;
import org.spongepowered.asm.mixin.transformer.meta.SourceMap;
import org.spongepowered.asm.util.ASMHelper;

class TargetClassContext {
    private final String sessionId;
    private final String className;
    private final ClassNode classNode;
    private final ClassInfo classInfo;
    private final SourceMap sourceMap;
    private final SortedSet mixins;
    private final Map targetMethods = new HashMap();
    private int nextUniqueMethodIndex;
    private int nextUniqueFieldIndex;
    private boolean applied;
    private boolean forceExport;

    TargetClassContext(String string, String string2, ClassNode classNode, SortedSet sortedSet) {
        this.sessionId = string;
        this.className = string2;
        this.classNode = classNode;
        this.classInfo = ClassInfo.fromClassNode(classNode);
        this.mixins = sortedSet;
        this.sourceMap = new SourceMap(classNode.sourceFile);
        this.sourceMap.addFile(this.classNode);
    }

    public String toString() {
        return this.className;
    }

    public boolean isApplied() {
        return this.applied;
    }

    public boolean isExportForced() {
        return this.forceExport;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public String getName() {
        return this.classNode.name;
    }

    public String getClassName() {
        return this.className;
    }

    public ClassNode getClassNode() {
        return this.classNode;
    }

    public List getMethods() {
        return this.classNode.methods;
    }

    public List getFields() {
        return this.classNode.fields;
    }

    public ClassInfo getClassInfo() {
        return this.classInfo;
    }

    public SortedSet getMixins() {
        return this.mixins;
    }

    public SourceMap getSourceMap() {
        return this.sourceMap;
    }

    MethodNode findAliasedMethod(Deque deque, String string) {
        String string2 = (String)deque.poll();
        if (string2 == null) {
            return null;
        }
        for (MethodNode methodNode : this.classNode.methods) {
            if (!methodNode.name.equals(string2) || !methodNode.desc.equals(string)) continue;
            return methodNode;
        }
        return this.findAliasedMethod(deque, string);
    }

    FieldNode findAliasedField(Deque deque, String string) {
        String string2 = (String)deque.poll();
        if (string2 == null) {
            return null;
        }
        for (FieldNode fieldNode : this.classNode.fields) {
            if (!fieldNode.name.equals(string2) || !fieldNode.desc.equals(string)) continue;
            return fieldNode;
        }
        return this.findAliasedField(deque, string);
    }

    public Target getTargetMethod(MethodNode methodNode) {
        if (!this.classNode.methods.contains(methodNode)) {
            throw new IllegalArgumentException("Invalid target method supplied to getTargetMethod()");
        }
        String string = methodNode.name + methodNode.desc;
        Target target = (Target)this.targetMethods.get(string);
        if (target == null) {
            target = new Target(this.classNode, methodNode);
            this.targetMethods.put(string, target);
        }
        return target;
    }

    public String getUniqueName(MethodNode methodNode) {
        String string = Integer.toHexString(this.nextUniqueMethodIndex++);
        return String.format("md%s$%s$%s", this.sessionId.substring(30), methodNode.name, string);
    }

    public String getUniqueName(FieldNode fieldNode) {
        String string = Integer.toHexString(this.nextUniqueFieldIndex++);
        return String.format("fd%s$%s$%s", this.sessionId.substring(30), fieldNode.name, string);
    }

    public void applyMixins() {
        if (this.applied) {
            throw new IllegalStateException("Mixins already applied to target class " + this.className);
        }
        this.applied = true;
        MixinApplicatorStandard mixinApplicatorStandard = this.createApplicator();
        mixinApplicatorStandard.apply(this.mixins);
    }

    private MixinApplicatorStandard createApplicator() {
        if (this.classInfo.isInterface()) {
            return new MixinApplicatorInterface(this);
        }
        return new MixinApplicatorStandard(this);
    }

    public void processDebugTasks() {
        if (!MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            return;
        }
        AnnotationNode annotationNode = ASMHelper.getVisibleAnnotation(this.classNode, Debug.class);
        if (annotationNode != null) {
            this.forceExport = Boolean.TRUE.equals(ASMHelper.getAnnotationValue(annotationNode, "export"));
            if (Boolean.TRUE.equals(ASMHelper.getAnnotationValue(annotationNode, "print"))) {
                ASMHelper.textify(this.classNode, (OutputStream)System.err);
            }
        }
        for (MethodNode methodNode : this.classNode.methods) {
            AnnotationNode annotationNode2 = ASMHelper.getVisibleAnnotation(methodNode, Debug.class);
            if (annotationNode2 == null || !Boolean.TRUE.equals(ASMHelper.getAnnotationValue(annotationNode2, "print"))) continue;
            ASMHelper.textify(methodNode, (OutputStream)System.err);
        }
    }
}

