/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMCUSTOMDRAW
extends NMHDR {
    public int dwDrawStage;
    public long hdc;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public long dwItemSpec;
    public int uItemState;
    public long lItemlParam;
    public static final int sizeof = OS.NMCUSTOMDRAW_sizeof();
}

