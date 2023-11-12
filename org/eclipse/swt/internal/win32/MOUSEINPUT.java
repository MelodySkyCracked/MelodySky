/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class MOUSEINPUT {
    public int dx;
    public int dy;
    public int mouseData;
    public int dwFlags;
    public int time;
    public long dwExtraInfo;
    public static final int sizeof = OS.MOUSEINPUT_sizeof();
}

