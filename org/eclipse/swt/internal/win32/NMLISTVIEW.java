/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMLISTVIEW
extends NMHDR {
    public int iItem;
    public int iSubItem;
    public int uNewState;
    public int uOldState;
    public int uChanged;
    public int x;
    public int y;
    public long lParam;
    public static int sizeof = OS.NMLISTVIEW_sizeof();
}

