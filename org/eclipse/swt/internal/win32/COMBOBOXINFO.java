/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class COMBOBOXINFO {
    public int cbSize;
    public int itemLeft;
    public int itemTop;
    public int itemRight;
    public int itemBottom;
    public int buttonLeft;
    public int buttonTop;
    public int buttonRight;
    public int buttonBottom;
    public int stateButton;
    public long hwndCombo;
    public long hwndItem;
    public long hwndList;
    public static final int sizeof = OS.COMBOBOXINFO_sizeof();
}

