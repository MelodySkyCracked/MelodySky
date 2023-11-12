/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class SHFILEINFO {
    public long hIcon;
    public int iIcon;
    public int dwAttributes;
    public char[] szDisplayName = new char[260];
    public char[] szTypeName = new char[80];
    public static int sizeof = OS.SHFILEINFO_sizeof();
}

