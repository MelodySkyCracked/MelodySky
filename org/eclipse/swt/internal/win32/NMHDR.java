/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.win32.OS;

public class NMHDR {
    public long hwndFrom;
    public long idFrom;
    public int code;
    public static final int sizeof = OS.NMHDR_sizeof();
}

