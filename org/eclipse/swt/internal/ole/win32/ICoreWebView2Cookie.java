/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2Cookie
extends IUnknown {
    public ICoreWebView2Cookie(long l2) {
        super(l2);
    }

    public int get_Name(long[] lArray) {
        return COM.VtblCall(3, this.address, lArray);
    }

    public int get_Value(long[] lArray) {
        return COM.VtblCall(4, this.address, lArray);
    }

    public int put_Value(char[] cArray) {
        return COM.VtblCall(5, this.address, cArray);
    }

    public int put_Expires(double d) {
        return COM.VtblCall(9, this.address, d);
    }

    public int put_IsHttpOnly(boolean bl) {
        return COM.VtblCall(11, this.address, bl ? 1 : 0);
    }

    public int put_IsSecure(boolean bl) {
        return COM.VtblCall(15, this.address, bl ? 1 : 0);
    }

    public int get_IsSession(int[] nArray) {
        return COM.VtblCall(16, this.address, nArray);
    }
}

