/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;

public class IUnknown {
    long address;

    public IUnknown(long l2) {
        this.address = l2;
    }

    public int AddRef() {
        return COM.VtblCall(1, this.address);
    }

    public long getAddress() {
        return this.address;
    }

    public int QueryInterface(GUID gUID, long[] lArray) {
        return COM.VtblCall(0, this.address, gUID, lArray);
    }

    public int Release() {
        return COM.VtblCall(2, this.address);
    }
}

