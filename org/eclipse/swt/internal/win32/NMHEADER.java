/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMHEADER
extends NMHDR {
    public int iItem;
    public int iButton;
    public long pitem;
    public static int sizeof = OS.NMHEADER_sizeof();
}

