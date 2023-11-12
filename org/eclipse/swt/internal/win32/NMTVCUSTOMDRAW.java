/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.OS;

public class NMTVCUSTOMDRAW
extends NMCUSTOMDRAW {
    public int clrText;
    public int clrTextBk;
    public int iLevel;
    public static final int sizeof = OS.NMTVCUSTOMDRAW_sizeof();
}

