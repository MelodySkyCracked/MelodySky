/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public final class EXCEPINFO {
    public short wCode;
    public short wReserved;
    public long bstrSource;
    public long bstrDescription;
    public long bstrHelpFile;
    public int dwHelpContext;
    public long pvReserved;
    public long pfnDeferredFillIn;
    public int scode;
    public static final int sizeof = COM.EXCEPINFO_sizeof();
}

