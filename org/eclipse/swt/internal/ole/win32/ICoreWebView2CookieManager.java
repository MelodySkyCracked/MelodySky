/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.ICoreWebView2Cookie;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2CookieManager
extends IUnknown {
    public ICoreWebView2CookieManager(long l2) {
        super(l2);
    }

    public int CreateCookie(char[] cArray, char[] cArray2, char[] cArray3, char[] cArray4, long[] lArray) {
        return COM.VtblCall(3, this.address, cArray, cArray2, cArray3, cArray4, lArray);
    }

    public int GetCookies(char[] cArray, IUnknown iUnknown) {
        return COM.VtblCall(5, this.address, cArray, iUnknown.getAddress());
    }

    public int AddOrUpdateCookie(ICoreWebView2Cookie iCoreWebView2Cookie) {
        return COM.VtblCall(6, this.address, iCoreWebView2Cookie.getAddress());
    }

    public int DeleteCookie(ICoreWebView2Cookie iCoreWebView2Cookie) {
        return COM.VtblCall(7, this.address, iCoreWebView2Cookie.getAddress());
    }
}

