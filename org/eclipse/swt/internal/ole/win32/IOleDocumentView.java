/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.RECT;

public class IOleDocumentView
extends IUnknown {
    public IOleDocumentView(long l2) {
        super(l2);
    }

    public int SetInPlaceSite(long l2) {
        return COM.VtblCall(3, this.address, l2);
    }

    public int SetRect(RECT rECT) {
        return COM.VtblCall(6, this.address, rECT);
    }

    public int Show(int n) {
        return COM.VtblCall(9, this.address, n);
    }

    public int UIActivate(int n) {
        return COM.VtblCall(10, this.address, n);
    }
}

