/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.SYSTEMTIME;

public class MCHITTESTINFO {
    public int cbSize;
    public POINT pt = new POINT();
    public int uHit;
    public SYSTEMTIME st = new SYSTEMTIME();
    public static final int sizeof = OS.MCHITTESTINFO_sizeof();
}

