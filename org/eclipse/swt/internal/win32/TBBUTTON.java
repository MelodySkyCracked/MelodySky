/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class TBBUTTON {
    public int iBitmap;
    public int idCommand;
    public byte fsState;
    public byte fsStyle;
    public long dwData;
    public long iString;
    public static final int sizeof = OS.TBBUTTON_sizeof();
}

