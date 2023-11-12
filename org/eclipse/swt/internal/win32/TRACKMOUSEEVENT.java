/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class TRACKMOUSEEVENT {
    public int cbSize;
    public int dwFlags;
    public long hwndTrack;
    public int dwHoverTime;
    public static final int sizeof = OS.TRACKMOUSEEVENT_sizeof();
}

