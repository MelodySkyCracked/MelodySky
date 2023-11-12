/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2Deferral
extends IUnknown {
    public ICoreWebView2Deferral(long l2) {
        super(l2);
    }

    public int Complete() {
        return COM.VtblCall(3, this.address);
    }
}

