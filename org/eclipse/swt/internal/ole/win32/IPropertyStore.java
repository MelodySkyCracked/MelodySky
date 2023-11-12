/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.PROPERTYKEY;

public class IPropertyStore
extends IUnknown {
    public IPropertyStore(long l2) {
        super(l2);
    }

    public int SetValue(PROPERTYKEY pROPERTYKEY, long l2) {
        return COM.VtblCall(6, this.address, pROPERTYKEY, l2);
    }

    public int Commit() {
        return COM.VtblCall(7, this.address);
    }
}

