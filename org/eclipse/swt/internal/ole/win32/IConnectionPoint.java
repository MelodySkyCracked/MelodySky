/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IConnectionPoint
extends IUnknown {
    public IConnectionPoint(long l2) {
        super(l2);
    }

    public int Advise(long l2, int[] nArray) {
        return COM.VtblCall(5, this.address, l2, nArray);
    }

    public int Unadvise(int n) {
        return COM.VtblCall(6, this.address, n);
    }
}

