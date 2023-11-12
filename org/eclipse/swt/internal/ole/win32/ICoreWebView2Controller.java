/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.win32.RECT;

public class ICoreWebView2Controller
extends IUnknown {
    public ICoreWebView2Controller(long l2) {
        super(l2);
    }

    public int put_IsVisible(boolean bl) {
        return COM.VtblCall(4, this.address, bl ? 1 : 0);
    }

    public int put_Bounds(RECT rECT) {
        return COM.VtblCall_put_Bounds(6, this.address, rECT);
    }

    public int MoveFocus(int n) {
        return COM.VtblCall(12, this.address, n);
    }

    public int add_MoveFocusRequested(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(13, this.address, iUnknown.address, lArray);
    }

    public int add_GotFocus(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(15, this.address, iUnknown.address, lArray);
    }

    public int add_LostFocus(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(17, this.address, iUnknown.address, lArray);
    }

    public int add_AcceleratorKeyPressed(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(19, this.address, iUnknown.address, lArray);
    }

    public int NotifyParentWindowPositionChanged() {
        return COM.VtblCall(23, this.address);
    }

    public int Close() {
        return COM.VtblCall(24, this.address);
    }

    public int get_CoreWebView2(long[] lArray) {
        return COM.VtblCall(25, this.address, lArray);
    }
}

