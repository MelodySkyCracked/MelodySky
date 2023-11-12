/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.IMixinTransformerModule;
import org.spongepowered.asm.mixin.transformer.MixinClassWriter;
import org.spongepowered.asm.mixin.transformer.TargetClassContext;

public class MixinTransformerModuleCheckClass
implements IMixinTransformerModule {
    @Override
    public void preApply(TargetClassContext targetClassContext) {
    }

    @Override
    public void postApply(TargetClassContext targetClassContext) {
        try {
            targetClassContext.getClassNode().accept(new CheckClassAdapter(new MixinClassWriter(2)));
        }
        catch (RuntimeException runtimeException) {
            throw new ValidationFailedException(runtimeException.getMessage(), runtimeException);
        }
    }

    public static class ValidationFailedException
    extends MixinException {
        private static final long serialVersionUID = 1L;

        public ValidationFailedException(String string, Throwable throwable) {
            super(string, throwable);
        }

        public ValidationFailedException(String string) {
            super(string);
        }

        public ValidationFailedException(Throwable throwable) {
            super(throwable);
        }
    }
}

