/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.MixinPreProcessorStandard;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
import org.spongepowered.asm.util.ASMHelper;

class MixinPreProcessorInterface
extends MixinPreProcessorStandard {
    MixinPreProcessorInterface(MixinInfo mixinInfo, MixinInfo.MixinClassNode mixinClassNode) {
        super(mixinInfo, mixinClassNode);
    }

    @Override
    protected void prepareMethod(MixinInfo.MixinMethodNode mixinMethodNode, ClassInfo.Method method) {
        if (!ASMHelper.hasFlag(mixinMethodNode, 1) && !ASMHelper.hasFlag(mixinMethodNode, 4096)) {
            throw new InvalidInterfaceMixinException((IMixinInfo)this.mixin, "Interface mixin contains a non-public method! Found " + method + " in " + this.mixin);
        }
        super.prepareMethod(mixinMethodNode, method);
    }

    protected boolean validateField(MixinTargetContext mixinTargetContext, FieldNode fieldNode, AnnotationNode annotationNode) {
        if (!ASMHelper.hasFlag(fieldNode, 8)) {
            throw new InvalidInterfaceMixinException((IMixinInfo)this.mixin, "Interface mixin contains an instance field! Found " + fieldNode.name + " in " + this.mixin);
        }
        return super.validateField(mixinTargetContext, fieldNode, annotationNode);
    }
}

