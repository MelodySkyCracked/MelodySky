/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMTVITEMCHANGE
extends NMHDR {
    public int uChanged;
    public long hItem;
    public int uStateNew;
    public int uStateOld;
    public long lParam;
    public static int sizeof = OS.NMTVITEMCHANGE_sizeof();
}

