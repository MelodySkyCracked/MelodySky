/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class TOUCHINPUT {
    public int x;
    public int y;
    public long hSource;
    public int dwID;
    public int dwFlags;
    public int dwMask;
    public int dwTime;
    public long dwExtraInfo;
    public int cxContact;
    public int cyContact;
    public static final int sizeof = OS.TOUCHINPUT_sizeof();
}

