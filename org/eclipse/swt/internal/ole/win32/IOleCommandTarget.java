/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.OLECMD;

public class IOleCommandTarget
extends IUnknown {
    public IOleCommandTarget(long l2) {
        super(l2);
    }

    public int Exec(GUID gUID, int n, int n2, long l2, long l3) {
        return COM.VtblCall(4, this.address, gUID, n, n2, l2, l3);
    }

    public int QueryStatus(GUID gUID, int n, OLECMD oLECMD, long l2) {
        if (n > 1) {
            return -2147024809;
        }
        return COM.VtblCall(3, this.address, gUID, n, oLECMD, l2);
    }
}

