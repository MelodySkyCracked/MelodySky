/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2WebMessageReceivedEventArgs
extends IUnknown {
    public ICoreWebView2WebMessageReceivedEventArgs(long l2) {
        super(l2);
    }

    public int get_Source(long[] lArray) {
        return COM.VtblCall(3, this.address, lArray);
    }

    public int get_WebMessageAsJson(long[] lArray) {
        return COM.VtblCall(4, this.address, lArray);
    }
}

