/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class PRINTDLG {
    public int lStructSize;
    public long hwndOwner;
    public long hDevMode;
    public long hDevNames;
    public long hDC;
    public int Flags;
    public short nFromPage;
    public short nToPage;
    public short nMinPage;
    public short nMaxPage;
    public short nCopies;
    public long hInstance;
    public long lCustData;
    public long lpfnPrintHook;
    public long lpfnSetupHook;
    public long lpPrintTemplateName;
    public long lpSetupTemplateName;
    public long hPrintTemplate;
    public long hSetupTemplate;
    public static final int sizeof = OS.PRINTDLG_sizeof();
}

