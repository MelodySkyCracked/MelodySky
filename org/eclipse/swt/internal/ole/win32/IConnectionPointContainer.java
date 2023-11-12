/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IConnectionPointContainer
extends IUnknown {
    public IConnectionPointContainer(long l2) {
        super(l2);
    }

    public int FindConnectionPoint(GUID gUID, long[] lArray) {
        return COM.VtblCall(4, this.address, gUID, lArray);
    }
}

