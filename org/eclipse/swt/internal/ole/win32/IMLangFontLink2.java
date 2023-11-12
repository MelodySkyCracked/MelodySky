/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class IMLangFontLink2
extends IUnknown {
    public IMLangFontLink2(long l2) {
        super(l2);
    }

    public int GetStrCodePages(char[] cArray, int n, int n2, int[] nArray, int[] nArray2) {
        return COM.VtblCall(4, this.address, cArray, n, n2, nArray, nArray2);
    }

    public int ReleaseFont(long l2) {
        return COM.VtblCall(8, this.address, l2);
    }

    public int MapFont(long l2, int n, char c, long[] lArray) {
        return COM.VtblCall(10, this.address, l2, n, (int)c, lArray);
    }
}

