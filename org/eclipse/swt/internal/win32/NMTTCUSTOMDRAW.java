/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMCUSTOMDRAW;
import org.eclipse.swt.internal.win32.OS;

public class NMTTCUSTOMDRAW
extends NMCUSTOMDRAW {
    public int uDrawFlags;
    public static final int sizeof = OS.NMTTCUSTOMDRAW_sizeof();
}

