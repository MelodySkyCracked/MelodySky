/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IPropertyBag
extends IUnknown {
    public IPropertyBag(long l2) {
        super(l2);
    }

    public int Read(long l2, long l3, long[] lArray) {
        return COM.VtblCall(3, this.getAddress(), l2, l3, lArray);
    }
}

