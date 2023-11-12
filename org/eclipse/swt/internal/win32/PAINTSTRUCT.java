/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class PAINTSTRUCT {
    public long hdc;
    public boolean fErase;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public boolean fRestore;
    public boolean fIncUpdate;
    public byte[] rgbReserved = new byte[32];
    public static final int sizeof = OS.PAINTSTRUCT_sizeof();
}

