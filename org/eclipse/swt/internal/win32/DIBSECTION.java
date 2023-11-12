/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.OS;

public class DIBSECTION
extends BITMAP {
    public int biSize;
    public int biWidth;
    public int biHeight;
    public short biPlanes;
    public short biBitCount;
    public int biCompression;
    public int biSizeImage;
    public int biXPelsPerMeter;
    public int biYPelsPerMeter;
    public int biClrUsed;
    public int biClrImportant;
    public int dsBitfields0;
    public int dsBitfields1;
    public int dsBitfields2;
    public long dshSection;
    public int dsOffset;
    public static final int sizeof = OS.DIBSECTION_sizeof();
}

