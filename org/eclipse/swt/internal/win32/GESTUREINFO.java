/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class GESTUREINFO {
    public int cbSize;
    public int dwFlags;
    public int dwID;
    public long hwndTarget;
    public short x;
    public short y;
    public int dwInstanceID;
    public int dwSequenceID;
    public long ullArguments;
    public int cbExtraArgs;
    public static final int sizeof = OS.GESTUREINFO_sizeof();
}

