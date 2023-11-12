/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.struct;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

public abstract class SpecialMethodInfo {
    protected final AnnotationNode annotation;
    protected final ClassNode classNode;
    protected final MethodNode method;
    protected final MixinTargetContext mixin;

    public SpecialMethodInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        this.mixin = mixinTargetContext;
        this.method = methodNode;
        this.annotation = annotationNode;
        this.classNode = mixinTargetContext.getTargetClassNode();
    }

    public final MixinTargetContext getContext() {
        return this.mixin;
    }

    public final AnnotationNode getAnnotation() {
        return this.annotation;
    }

    public final ClassNode getClassNode() {
        return this.classNode;
    }

    public final MethodNode getMethod() {
        return this.method;
    }
}

