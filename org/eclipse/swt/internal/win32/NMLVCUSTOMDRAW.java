/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.OS;

public class NMLVCUSTOMDRAW
extends NMCUSTOMDRAW {
    public int clrText;
    public int clrTextBk;
    public int iSubItem;
    public int dwItemType;
    public int clrFace;
    public int iIconEffect;
    public int iIconPhase;
    public int iPartId;
    public int iStateId;
    public int rcText_left;
    public int rcText_top;
    public int rcText_right;
    public int rcText_bottom;
    public int uAlign;
    public static final int sizeof = OS.NMLVCUSTOMDRAW_sizeof();
}

