/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.spongepowered.asm.mixin.injection.Constant;

@Target(value={ElementType.METHOD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface ModifyConstant {
    public String method();

    public Constant constant() default @Constant;

    public boolean remap() default true;

    public int require() default -1;

    public int expect() default 1;

    public String constraints() default "";
}

