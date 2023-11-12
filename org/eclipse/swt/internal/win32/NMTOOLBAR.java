/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMTOOLBAR
extends NMHDR {
    public int iItem;
    public int iBitmap;
    public int idCommand;
    public byte fsState;
    public byte fsStyle;
    public long dwData;
    public long iString;
    public int cchText;
    public long pszText;
    public int left;
    public int top;
    public int right;
    public int bottom;
    public static final int sizeof = OS.NMTOOLBAR_sizeof();
}

