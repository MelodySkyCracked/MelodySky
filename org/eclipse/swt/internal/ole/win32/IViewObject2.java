/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.SIZE;

public class IViewObject2
extends IUnknown {
    public IViewObject2(long l2) {
        super(l2);
    }

    public int GetExtent(int n, int n2, long l2, SIZE sIZE) {
        return COM.VtblCall(9, this.address, n, n2, l2, sIZE);
    }

    public int SetAdvise(int n, int n2, long l2) {
        return COM.VtblCall(7, this.address, n, (long)n2, l2);
    }
}

