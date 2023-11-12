/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class TCITEM {
    public int mask;
    public int dwState;
    public int dwStateMask;
    public long pszText;
    public int cchTextMax;
    public int iImage;
    public long lParam;
    public static final int sizeof = OS.TCITEM_sizeof();
}

