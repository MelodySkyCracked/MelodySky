/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IPersist;

public class IPersistStorage
extends IPersist {
    public IPersistStorage(long l2) {
        super(l2);
    }

    public int InitNew(long l2) {
        return COM.VtblCall(5, this.address, l2);
    }

    public int Load(long l2) {
        return COM.VtblCall(6, this.address, l2);
    }

    public int Save(long l2, boolean bl) {
        return COM.VtblCall(7, this.address, l2, bl ? 1 : 0);
    }

    public int SaveCompleted(long l2) {
        return COM.VtblCall(8, this.address, l2);
    }

    public int HandsOffStorage() {
        return COM.VtblCall(9, this.address);
    }
}

