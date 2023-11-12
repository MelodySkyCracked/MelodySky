/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2NavigationCompletedEventArgs
extends IUnknown {
    public ICoreWebView2NavigationCompletedEventArgs(long l2) {
        super(l2);
    }

    public int get_IsSuccess(int[] nArray) {
        return COM.VtblCall(3, this.address, nArray);
    }

    public int get_WebErrorStatus(int[] nArray) {
        return COM.VtblCall(4, this.address, nArray);
    }

    public int get_NavigationId(long[] lArray) {
        return COM.VtblCall(5, this.address, lArray);
    }
}

