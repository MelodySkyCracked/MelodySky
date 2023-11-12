/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class CHOOSEFONT {
    public int lStructSize;
    public long hwndOwner;
    public long hDC;
    public long lpLogFont;
    public int iPointSize;
    public int Flags;
    public int rgbColors;
    public long lCustData;
    public long lpfnHook;
    public long lpTemplateName;
    public long hInstance;
    public long lpszStyle;
    public short nFontType;
    public int nSizeMin;
    public int nSizeMax;
    public static final int sizeof = OS.CHOOSEFONT_sizeof();
}

