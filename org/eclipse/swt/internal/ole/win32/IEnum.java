/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IEnum
extends IUnknown {
    public IEnum(long l2) {
        super(l2);
    }

    public int Clone(long[] lArray) {
        return COM.VtblCall(6, this.address, lArray);
    }

    public int Next(int n, long l2, int[] nArray) {
        return COM.VtblCall(3, this.address, n, l2, nArray);
    }

    public int Reset() {
        return COM.VtblCall(5, this.address);
    }

    public int Skip(int n) {
        return COM.VtblCall(4, this.address, n);
    }
}

