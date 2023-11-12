/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2NavigationStartingEventArgs
extends IUnknown {
    public ICoreWebView2NavigationStartingEventArgs(long l2) {
        super(l2);
    }

    public int get_Uri(long[] lArray) {
        return COM.VtblCall(3, this.address, lArray);
    }

    public int get_IsUserInitiated(int[] nArray) {
        return COM.VtblCall(4, this.address, nArray);
    }

    public int get_IsRedirected(int[] nArray) {
        return COM.VtblCall(5, this.address, nArray);
    }

    public int get_RequestHeaders(long[] lArray) {
        return COM.VtblCall(6, this.address, lArray);
    }

    public int get_Cancel(int[] nArray) {
        return COM.VtblCall(7, this.address, nArray);
    }

    public int put_Cancel(boolean bl) {
        return COM.VtblCall(8, this.address, bl ? 1 : 0);
    }

    public int get_NavigationId(long[] lArray) {
        return COM.VtblCall(9, this.address, lArray);
    }
}

