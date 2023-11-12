/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class CHOOSECOLOR {
    public int lStructSize;
    public long hwndOwner;
    public long hInstance;
    public int rgbResult;
    public long lpCustColors;
    public int Flags;
    public long lCustData;
    public long lpfnHook;
    public long lpTemplateName;
    public static final int sizeof = OS.CHOOSECOLOR_sizeof();
}

