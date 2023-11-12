/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IShellItemArray
extends IUnknown {
    public IShellItemArray(long l2) {
        super(l2);
    }

    public int GetCount(int[] nArray) {
        return COM.VtblCall(7, this.address, nArray);
    }

    public int GetItemAt(int n, long[] lArray) {
        return COM.VtblCall(8, this.address, n, lArray);
    }
}

