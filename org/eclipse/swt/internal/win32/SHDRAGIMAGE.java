/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.SIZE;

public final class SHDRAGIMAGE {
    public SIZE sizeDragImage = new SIZE();
    public POINT ptOffset = new POINT();
    public long hbmpDragImage;
    public int crColorKey;
    public static final int sizeof = OS.SHDRAGIMAGE_sizeof();
}

