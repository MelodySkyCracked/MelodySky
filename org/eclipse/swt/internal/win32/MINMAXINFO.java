/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class MINMAXINFO {
    public int ptReserved_x;
    public int ptReserved_y;
    public int ptMaxSize_x;
    public int ptMaxSize_y;
    public int ptMaxPosition_x;
    public int ptMaxPosition_y;
    public int ptMinTrackSize_x;
    public int ptMinTrackSize_y;
    public int ptMaxTrackSize_x;
    public int ptMaxTrackSize_y;
    public static final int sizeof = OS.MINMAXINFO_sizeof();
}

