/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2Environment
extends IUnknown {
    public ICoreWebView2Environment(long l2) {
        super(l2);
    }

    public int CreateCoreWebView2Controller(long l2, IUnknown iUnknown) {
        return COM.VtblCall(3, this.address, l2, iUnknown.address);
    }

    public int get_BrowserVersionString(long[] lArray) {
        return COM.VtblCall(5, this.address, lArray);
    }
}

