/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.CAUUID;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ISpecifyPropertyPages
extends IUnknown {
    public ISpecifyPropertyPages(long l2) {
        super(l2);
    }

    public int GetPages(CAUUID cAUUID) {
        return COM.VtblCall(3, this.address, cAUUID);
    }
}

