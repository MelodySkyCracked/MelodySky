/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.TVITEM;

public class NMTREEVIEW {
    public NMHDR hdr = new NMHDR();
    public int action;
    public TVITEM itemOld = new TVITEM();
    public TVITEM itemNew = new TVITEM();
    public POINT ptDrag = new POINT();
    public static final int sizeof = OS.NMTREEVIEW_sizeof();
}

