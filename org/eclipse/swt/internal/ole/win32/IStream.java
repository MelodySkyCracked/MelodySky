/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IStream
extends IUnknown {
    public IStream(long l2) {
        super(l2);
    }

    public int Commit(int n) {
        return COM.VtblCall(8, this.address, n);
    }

    public int Read(long l2, int n, int[] nArray) {
        return COM.VtblCall(3, this.address, l2, n, nArray);
    }

    public int Write(long l2, int n, int[] nArray) {
        return COM.VtblCall(4, this.address, l2, n, nArray);
    }
}

