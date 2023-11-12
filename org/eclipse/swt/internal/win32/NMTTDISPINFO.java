/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.NMHDR;
import org.eclipse.swt.internal.win32.OS;

public class NMTTDISPINFO
extends NMHDR {
    public long lpszText;
    public char[] szText = new char[80];
    public long hinst;
    public int uFlags;
    public long lParam;
    public static final int sizeof = OS.NMTTDISPINFO_sizeof();
}

