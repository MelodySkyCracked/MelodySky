/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class GUITHREADINFO {
    public int cbSize;
    public int flags;
    public long hwndActive;
    public long hwndFocus;
    public long hwndCapture;
    public long hwndMenuOwner;
    public long hwndMoveSize;
    public long hwndCaret;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public static int sizeof = OS.GUITHREADINFO_sizeof();
}

