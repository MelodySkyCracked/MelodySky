/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2CookieList
extends IUnknown {
    public ICoreWebView2CookieList(long l2) {
        super(l2);
    }

    public int get_Count(int[] nArray) {
        return COM.VtblCall(3, this.address, nArray);
    }

    public int GetValueAtIndex(int n, long[] lArray) {
        return COM.VtblCall(4, this.address, n, lArray);
    }
}

