/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal;

import org.eclipse.swt.internal.Library;
import org.eclipse.swt.internal.Platform;

public class C
extends Platform {
    public static final int PTR_SIZEOF;

    public static final native void free(long var0);

    public static final native long getenv(byte[] var0);

    public static final native int setenv(byte[] var0, byte[] var1, int var2);

    public static final native long malloc(long var0);

    public static final native void memmove(long var0, byte[] var2, long var3);

    public static final native void memmove(long var0, char[] var2, long var3);

    public static final native void memmove(long var0, double[] var2, long var3);

    public static final native void memmove(long var0, float[] var2, long var3);

    public static final native void memmove(long var0, int[] var2, long var3);

    public static final native void memmove(long var0, long[] var2, long var3);

    public static final native void memmove(long var0, short[] var2, long var3);

    public static final native void memmove(byte[] var0, char[] var1, long var2);

    public static final native void memmove(byte[] var0, long var1, long var3);

    public static final native void memmove(long var0, long var2, long var4);

    public static final native void memmove(char[] var0, long var1, long var3);

    public static final native void memmove(double[] var0, long var1, long var3);

    public static final native void memmove(float[] var0, long var1, long var3);

    public static final native void memmove(int[] var0, byte[] var1, long var2);

    public static final native void memmove(short[] var0, long var1, long var3);

    public static final native void memmove(int[] var0, long var1, long var3);

    public static final native void memmove(long[] var0, long var1, long var3);

    public static final native long memset(long var0, int var2, long var3);

    public static final native int PTR_sizeof();

    public static final native int strlen(long var0);

    static {
        Library.loadLibrary("swt");
        PTR_SIZEOF = C.PTR_sizeof();
    }
}

