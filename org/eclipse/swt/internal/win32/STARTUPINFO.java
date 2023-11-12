/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class STARTUPINFO {
    public int cb;
    public long lpReserved;
    public long lpDesktop;
    public long lpTitle;
    public int dwX;
    public int dwY;
    public int dwXSize;
    public int dwYSize;
    public int dwXCountChars;
    public int dwYCountChars;
    public int dwFillAttribute;
    public int dwFlags;
    public short wShowWindow;
    public short cbReserved2;
    public long lpReserved2;
    public long hStdInput;
    public long hStdOutput;
    public long hStdError;
    public static int sizeof = OS.STARTUPINFO_sizeof();
}

