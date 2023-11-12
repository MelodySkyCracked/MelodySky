/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IServiceProvider
extends IUnknown {
    public IServiceProvider(long l2) {
        super(l2);
    }

    public int QueryService(GUID gUID, GUID gUID2, long[] lArray) {
        return COM.VtblCall(3, this.address, gUID, gUID2, lArray);
    }
}

