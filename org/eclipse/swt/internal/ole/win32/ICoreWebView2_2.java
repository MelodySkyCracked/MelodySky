/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2_2
extends ICoreWebView2 {
    public ICoreWebView2_2(long l2) {
        super(l2);
    }

    public int NavigateWithWebResourceRequest(IUnknown iUnknown) {
        return COM.VtblCall(63, this.address, iUnknown.address);
    }

    public int add_DOMContentLoaded(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(64, this.address, iUnknown.getAddress(), lArray);
    }

    public int get_CookieManager(long[] lArray) {
        return COM.VtblCall(66, this.address, lArray);
    }
}

