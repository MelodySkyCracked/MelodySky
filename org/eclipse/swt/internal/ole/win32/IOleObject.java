/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;

public class IOleObject
extends IUnknown {
    public IOleObject(long l2) {
        super(l2);
    }

    public int Advise(long l2, int[] nArray) {
        return COM.VtblCall(19, this.address, l2, nArray);
    }

    public int Close(int n) {
        return COM.VtblCall(6, this.address, n);
    }

    public int DoVerb(int n, MSG mSG, long l2, int n2, long l3, RECT rECT) {
        return COM.VtblCall(11, this.address, n, mSG, l2, n2, l3, rECT);
    }

    public int GetClientSite(long[] lArray) {
        return COM.VtblCall(4, this.address, lArray);
    }

    public int GetExtent(int n, SIZE sIZE) {
        return COM.VtblCall(18, this.address, n, sIZE);
    }

    public int SetClientSite(long l2) {
        return COM.VtblCall(3, this.address, l2);
    }

    public int SetExtent(int n, SIZE sIZE) {
        return COM.VtblCall(17, this.address, n, sIZE);
    }

    public int SetHostNames(String string, String string2) {
        char[] cArray = null;
        if (string != null) {
            int n = string.length();
            cArray = new char[n + 1];
            string.getChars(0, n, cArray, 0);
        }
        char[] cArray2 = null;
        if (string2 != null) {
            int n = string2.length();
            cArray2 = new char[n + 1];
            string2.getChars(0, n, cArray2, 0);
        }
        return COM.VtblCall(5, this.address, cArray, cArray2);
    }

    public int Unadvise(int n) {
        return COM.VtblCall(20, this.address, n);
    }

    public int Update() {
        return COM.VtblCall(13, this.address);
    }
}

