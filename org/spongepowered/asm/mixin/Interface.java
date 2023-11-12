/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.CLASS)
public @interface Interface {
    public Class iface();

    public String prefix();

    public boolean unique() default false;

    public Remap remap() default Remap.ALL;

    public static enum Remap {
        ALL,
        FORCE(true),
        ONLY_PREFIXED,
        NONE;

        private final boolean forceRemap;

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private Remap() {
            this((String)var1_-1, (int)var2_-1, false);
            void var2_-1;
            void var1_-1;
        }

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private Remap() {
            void var3_1;
            this.forceRemap = var3_1;
        }

        public boolean forceRemap() {
            return this.forceRemap;
        }
    }
}

