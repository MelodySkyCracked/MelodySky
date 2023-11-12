/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IPersist;

public class IPersistFile
extends IPersist {
    public IPersistFile(long l2) {
        super(l2);
    }

    public int IsDirty() {
        return COM.VtblCall(4, this.address);
    }
}

