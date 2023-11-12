/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.LICINFO;

public class IClassFactory2
extends IUnknown {
    public IClassFactory2(long l2) {
        super(l2);
    }

    public int CreateInstanceLic(long l2, long l3, GUID gUID, long l4, long[] lArray) {
        return COM.VtblCall(7, this.address, l2, l3, gUID, l4, lArray);
    }

    public int GetLicInfo(LICINFO lICINFO) {
        return COM.VtblCall(5, this.address, lICINFO);
    }

    public int RequestLicKey(int n, long[] lArray) {
        return COM.VtblCall(6, this.address, n, lArray);
    }
}

