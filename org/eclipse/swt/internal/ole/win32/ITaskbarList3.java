/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ITaskbarList3
extends IUnknown {
    public ITaskbarList3(long l2) {
        super(l2);
    }

    public int SetProgressValue(long l2, long l3, long l4) {
        return COM.VtblCall(9, this.address, l2, l3, l4);
    }

    public int SetProgressState(long l2, int n) {
        return COM.VtblCall(10, this.address, l2, n);
    }

    public int SetOverlayIcon(long l2, long l3, long l4) {
        return COM.VtblCall(18, this.address, l2, l3, l4);
    }
}

