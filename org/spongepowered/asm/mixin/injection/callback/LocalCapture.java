/*
 * Decompiled with CFR 0.152.
 */
package org.spongepowered.asm.mixin.injection.callback;

public enum LocalCapture {
    NO_CAPTURE(false, false),
    PRINT(false, true),
    CAPTURE_FAILSOFT,
    CAPTURE_FAILHARD,
    CAPTURE_FAILEXCEPTION;

    private final boolean captureLocals;
    private final boolean printLocals;

    /*
     * WARNING - Possible parameter corruption
     * WARNING - void declaration
     */
    private LocalCapture() {
        this((String)var1_-1, (int)var2_-1, true, false);
        void var2_-1;
        void var1_-1;
    }

    /*
     * WARNING - void declaration
     */
    private LocalCapture() {
        void var4_2;
        void var3_1;
        this.captureLocals = var3_1;
        this.printLocals = var4_2;
    }

    boolean isCaptureLocals() {
        return this.captureLocals;
    }

    boolean isPrintLocals() {
        return this.printLocals;
    }
}

