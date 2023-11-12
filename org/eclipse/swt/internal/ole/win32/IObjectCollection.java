/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IObjectArray;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IObjectCollection
extends IObjectArray {
    public IObjectCollection(long l2) {
        super(l2);
    }

    public int AddObject(IUnknown iUnknown) {
        return COM.VtblCall(5, this.address, iUnknown.address);
    }
}

