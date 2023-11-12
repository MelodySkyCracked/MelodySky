/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public final class FORMATETC {
    public int cfFormat;
    public long ptd;
    public int dwAspect;
    public int lindex;
    public int tymed;
    public static final int sizeof = COM.FORMATETC_sizeof();
}

