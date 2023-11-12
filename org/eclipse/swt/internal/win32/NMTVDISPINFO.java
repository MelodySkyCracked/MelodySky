/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMTVDISPINFO
extends NMHDR {
    public int mask;
    public long hItem;
    public int state;
    public int stateMask;
    public long pszText;
    public int cchTextMax;
    public int iImage;
    public int iSelectedImage;
    public int cChildren;
    public long lParam;
    public static final int sizeof = OS.NMTVDISPINFO_sizeof();
}

