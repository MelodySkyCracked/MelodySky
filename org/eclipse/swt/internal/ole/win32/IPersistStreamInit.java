/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IPersist;

public class IPersistStreamInit
extends IPersist {
    public IPersistStreamInit(long l2) {
        super(l2);
    }

    public int Load(long l2) {
        return COM.VtblCall(5, this.address, l2);
    }

    public int InitNew() {
        return COM.VtblCall(8, this.address);
    }
}

