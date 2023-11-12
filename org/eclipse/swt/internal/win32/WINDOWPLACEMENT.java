/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class WINDOWPLACEMENT {
    public int length;
    public int flags;
    public int showCmd;
    public int ptMinPosition_x;
    public int ptMinPosition_y;
    public int ptMaxPosition_x;
    public int ptMaxPosition_y;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public static final int sizeof = OS.WINDOWPLACEMENT_sizeof();
}

