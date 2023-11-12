/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IStorage
extends IUnknown {
    public IStorage(long l2) {
        super(l2);
    }

    public int Commit(int n) {
        return COM.VtblCall(9, this.address, n);
    }

    public int CopyTo(int n, GUID gUID, String[] stringArray, long l2) {
        if (stringArray != null) {
            return -2147024809;
        }
        return COM.VtblCall(7, this.address, n, gUID, 0L, l2);
    }

    public int CreateStream(String string, int n, int n2, int n3, long[] lArray) {
        char[] cArray = null;
        if (string != null) {
            cArray = string.toCharArray();
        }
        return COM.VtblCall(3, this.address, cArray, n, n2, n3, lArray);
    }

    public int OpenStream(String string, long l2, int n, int n2, long[] lArray) {
        char[] cArray = null;
        if (string != null) {
            cArray = string.toCharArray();
        }
        return COM.VtblCall(4, this.address, cArray, l2, n, n2, lArray);
    }
}

