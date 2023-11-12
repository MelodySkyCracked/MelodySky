/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IOleLink
extends IUnknown {
    public IOleLink(long l2) {
        super(l2);
    }

    public int BindIfRunning() {
        return COM.VtblCall(10, this.address);
    }

    public int GetSourceMoniker(long[] lArray) {
        return COM.VtblCall(6, this.address, lArray);
    }
}

