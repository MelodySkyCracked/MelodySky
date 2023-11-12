/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.KEYBDINPUT;
import org.eclipse.swt.internal.win32.MOUSEINPUT;
import org.eclipse.swt.internal.win32.OS;

public class INPUT {
    public int type;
    public KEYBDINPUT ki;
    public MOUSEINPUT mi;
    public static final int sizeof = OS.INPUT_sizeof();
}

