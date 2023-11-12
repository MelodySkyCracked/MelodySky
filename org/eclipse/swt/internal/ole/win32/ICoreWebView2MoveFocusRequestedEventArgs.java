/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2MoveFocusRequestedEventArgs
extends IUnknown {
    public ICoreWebView2MoveFocusRequestedEventArgs(long l2) {
        super(l2);
    }

    public int get_Reason(int[] nArray) {
        return COM.VtblCall(3, this.address, nArray);
    }

    public int put_Handled(boolean bl) {
        return COM.VtblCall(5, this.address, bl ? 1 : 0);
    }
}

