/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.Level
 */
package org.spongepowered.asm.mixin.extensibility;

import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public interface IMixinErrorHandler {
    public ErrorAction onPrepareError(IMixinConfig var1, Throwable var2, IMixinInfo var3, ErrorAction var4);

    public ErrorAction onApplyError(String var1, Throwable var2, IMixinInfo var3, ErrorAction var4);

    public static enum ErrorAction {
        NONE(Level.INFO),
        WARN(Level.WARN),
        ERROR(Level.FATAL);

        public final Level logLevel;

        /*
         * WARNING - Possible parameter corruption
         * WARNING - void declaration
         */
        private ErrorAction() {
            void var3_1;
            this.logLevel = var3_1;
        }
    }
}

