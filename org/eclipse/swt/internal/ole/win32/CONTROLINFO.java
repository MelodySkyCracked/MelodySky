/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public final class CONTROLINFO {
    public int cb;
    public long hAccel;
    public short cAccel;
    public int dwFlags;
    public static final int sizeof = COM.CONTROLINFO_sizeof();
}

