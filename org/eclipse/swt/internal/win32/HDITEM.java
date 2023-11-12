/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class HDITEM {
    public int mask;
    public int cxy;
    public long pszText;
    public long hbm;
    public int cchTextMax;
    public int fmt;
    public long lParam;
    public int iImage;
    public int iOrder;
    public int type;
    public long pvFilter;
    public static int sizeof = OS.HDITEM_sizeof();
}

