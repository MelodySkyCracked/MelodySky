/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.TF_DISPLAYATTRIBUTE;

public class ITfDisplayAttributeInfo
extends IUnknown {
    public ITfDisplayAttributeInfo(long l2) {
        super(l2);
    }

    public int GetAttributeInfo(TF_DISPLAYATTRIBUTE tF_DISPLAYATTRIBUTE) {
        return COM.VtblCall(5, this.address, tF_DISPLAYATTRIBUTE);
    }
}

