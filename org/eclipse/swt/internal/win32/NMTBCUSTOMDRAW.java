/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.OS;

public class NMTBCUSTOMDRAW
extends NMCUSTOMDRAW {
    public long hbrMonoDither;
    public long hbrLines;
    public long hpenLines;
    public int clrText;
    public int clrMark;
    public int clrTextHighlight;
    public int clrBtnFace;
    public int clrBtnHighlight;
    public int clrHighlightHotTrack;
    public int rcText_left;
    public int rcText_top;
    public int rcText_right;
    public int rcText_bottom;
    public int nStringBkMode;
    public int nHLStringBkMode;
    public int iListGap;
    public static final int sizeof = OS.NMTBCUSTOMDRAW_sizeof();
}

