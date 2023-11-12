/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

public class LRESULT {
    public long value;
    public static final LRESULT ONE = new LRESULT(1L);
    public static final LRESULT ZERO = new LRESULT(0L);

    public LRESULT(long l2) {
        this.value = l2;
    }
}

