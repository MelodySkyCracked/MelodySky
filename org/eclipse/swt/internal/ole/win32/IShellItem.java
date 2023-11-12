/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IShellItem
extends IUnknown {
    public IShellItem(long l2) {
        super(l2);
    }

    public int GetDisplayName(int n, long[] lArray) {
        return COM.VtblCall(5, this.address, n, lArray);
    }
}

