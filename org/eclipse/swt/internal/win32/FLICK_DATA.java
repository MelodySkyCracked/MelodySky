/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class FLICK_DATA {
    public int iFlickActionCommandCode;
    public byte iFlickDirection;
    public boolean fControlModifier;
    public boolean fMenuModifier;
    public boolean fAltGRModifier;
    public boolean fWinModifier;
    public boolean fShiftModifier;
    public int iReserved;
    public boolean fOnInkingSurface;
    public int iActionArgument;
    public static final int sizeof = OS.FLICK_DATA_sizeof();
}

