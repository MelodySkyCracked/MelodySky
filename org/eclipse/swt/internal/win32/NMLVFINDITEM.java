/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMLVFINDITEM
extends NMHDR {
    public int iStart;
    public int flags;
    public long psz;
    public long lParam;
    public int x;
    public int y;
    public int vkDirection;
    public static final int sizeof = OS.NMLVFINDITEM_sizeof();
}

