/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class MONITORINFO {
    public int cbSize;
    public int rcMonitor_left;
    public int rcMonitor_top;
    public int rcMonitor_right;
    public int rcMonitor_bottom;
    public int rcWork_left;
    public int rcWork_top;
    public int rcWork_right;
    public int rcWork_bottom;
    public int dwFlags;
    public static final int sizeof = OS.MONITORINFO_sizeof();
}

