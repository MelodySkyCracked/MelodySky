/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class RECT {
    public int left;
    public int top;
    public int right;
    public int bottom;
    public static final int sizeof = OS.RECT_sizeof();

    public RECT() {
    }

    public RECT(int n, int n2, int n3, int n4) {
        this.left = n;
        this.top = n2;
        this.right = n3;
        this.bottom = n4;
    }
}

