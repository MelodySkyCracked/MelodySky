/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class SHELLEXECUTEINFO {
    public int cbSize;
    public int fMask;
    public long hwnd;
    public long lpVerb;
    public long lpFile;
    public long lpParameters;
    public long lpDirectory;
    public int nShow;
    public long hInstApp;
    public long lpIDList;
    public long lpClass;
    public long hkeyClass;
    public int dwHotKey;
    public long hIcon;
    public long hProcess;
    public static final int sizeof = OS.SHELLEXECUTEINFO_sizeof();
}

