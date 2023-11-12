/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMREBARCHEVRON
extends NMHDR {
    public int uBand;
    public int wID;
    public long lParam;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public long lParamNM;
    public static int sizeof = OS.NMREBARCHEVRON_sizeof();
}

