/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class NOTIFYICONDATA {
    public int cbSize;
    public long hWnd;
    public int uID;
    public int uFlags;
    public int uCallbackMessage;
    public long hIcon;
    public char[] szTip = new char[128];
    public int dwState;
    public int dwStateMask;
    public char[] szInfo = new char[256];
    public char[] szInfoTitle = new char[64];
    public int uVersion;
    public int dwInfoFlags;
    public static final int sizeof = OS.NOTIFYICONDATA_V2_SIZE;
}

