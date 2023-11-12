/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 */
package org.spongepowered.asm.mixin.injection.struct;

import com.google.common.base.Strings;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInjector;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.ASMHelper;

public class CallbackInjectionInfo
extends InjectionInfo {
    protected CallbackInjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }

    @Override
    protected Injector parseInjector(AnnotationNode annotationNode) {
        boolean bl = (Boolean)ASMHelper.getAnnotationValue(annotationNode, "cancellable", false);
        LocalCapture localCapture = (LocalCapture)ASMHelper.getAnnotationValue(annotationNode, "locals", LocalCapture.class, LocalCapture.NO_CAPTURE);
        String string = (String)ASMHelper.getAnnotationValue(annotationNode, "id", "");
        return new CallbackInjector(this, bl, localCapture, string);
    }

    @Override
    public String getSliceId(String string) {
        return Strings.nullToEmpty((String)string);
    }
}

