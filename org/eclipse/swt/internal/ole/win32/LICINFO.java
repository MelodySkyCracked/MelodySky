/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;

public final class LICINFO {
    public int cbLicInfo;
    public boolean fRuntimeKeyAvail;
    public boolean fLicVerified;
    public static final int sizeof = COM.LICINFO_sizeof();
}

