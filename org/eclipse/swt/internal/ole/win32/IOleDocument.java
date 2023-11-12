/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IOleDocument
extends IUnknown {
    public IOleDocument(long l2) {
        super(l2);
    }

    public int CreateView(long l2, long l3, int n, long[] lArray) {
        return COM.VtblCall(3, this.address, l2, l3, n, lArray);
    }
}

