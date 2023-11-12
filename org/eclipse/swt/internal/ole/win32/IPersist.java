/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IPersist
extends IUnknown {
    public IPersist(long l2) {
        super(l2);
    }

    public int GetClassID(GUID gUID) {
        return COM.VtblCall(3, this.address, gUID);
    }
}

