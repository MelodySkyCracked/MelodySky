/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class OSVERSIONINFOEX {
    public int dwOSVersionInfoSize;
    public int dwMajorVersion;
    public int dwMinorVersion;
    public int dwBuildNumber;
    public int dwPlatformId;
    public char[] szCSDVersion = new char[128];
    public int wServicePackMajor;
    public int wServicePackMinor;
    public int wSuiteMask;
    public int wProductType;
    public int wReserved;
    public static final int sizeof = OS.OSVERSIONINFOEX_sizeof();
}

