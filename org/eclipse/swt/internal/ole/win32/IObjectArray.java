/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IObjectArray
extends IUnknown {
    public IObjectArray(long l2) {
        super(l2);
    }

    public int GetCount(int[] nArray) {
        return COM.VtblCall(3, this.address, nArray);
    }
}

