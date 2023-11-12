/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class DOCINFO {
    public int cbSize;
    public long lpszDocName;
    public long lpszOutput;
    public long lpszDatatype;
    public int fwType;
    public static final int sizeof = OS.DOCINFO_sizeof();
}

