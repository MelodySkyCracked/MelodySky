/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class KEYBDINPUT {
    public short wVk;
    public short wScan;
    public int dwFlags;
    public int time;
    public long dwExtraInfo;
    public static final int sizeof = OS.KEYBDINPUT_sizeof();
}

