/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class ICONINFO {
    public boolean fIcon;
    public int xHotspot;
    public int yHotspot;
    public long hbmMask;
    public long hbmColor;
    public static final int sizeof = OS.ICONINFO_sizeof();
}

