/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public class VARIANT {
    public short vt;
    public short wReserved1;
    public short wReserved2;
    public short wReserved3;
    public int lVal;
    public static final int sizeof = COM.VARIANT_sizeof();
}

