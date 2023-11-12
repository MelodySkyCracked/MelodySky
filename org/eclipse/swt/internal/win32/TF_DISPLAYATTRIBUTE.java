/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TF_DA_COLOR;

public class TF_DISPLAYATTRIBUTE {
    public TF_DA_COLOR crText = new TF_DA_COLOR();
    public TF_DA_COLOR crBk = new TF_DA_COLOR();
    public int lsStyle;
    public boolean fBoldLine;
    public TF_DA_COLOR crLine = new TF_DA_COLOR();
    public int bAttr;
    public static final int sizeof = OS.TF_DISPLAYATTRIBUTE_sizeof();
}

