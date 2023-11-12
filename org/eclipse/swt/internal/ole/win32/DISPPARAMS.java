/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public final class DISPPARAMS {
    public long rgvarg;
    public long rgdispidNamedArgs;
    public int cArgs;
    public int cNamedArgs;
    public static final int sizeof = COM.DISPPARAMS_sizeof();
}

