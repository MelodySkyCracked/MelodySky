/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value=RetentionPolicy.RUNTIME)
public @interface Constant {
    public boolean nullValue() default false;

    public int intValue() default 0;

    public float floatValue() default 0.0f;

    public long longValue() default 0L;

    public double doubleValue() default 0.0;

    public String stringValue() default "";

    public Class classValue() default Object.class;

    public int ordinal() default -1;

    public boolean log() default false;
}

