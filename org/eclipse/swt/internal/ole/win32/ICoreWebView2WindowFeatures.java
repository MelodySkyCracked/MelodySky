/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2WindowFeatures
extends IUnknown {
    public ICoreWebView2WindowFeatures(long l2) {
        super(l2);
    }

    public int get_HasPosition(int[] nArray) {
        return COM.VtblCall(3, this.address, nArray);
    }

    public int get_HasSize(int[] nArray) {
        return COM.VtblCall(4, this.address, nArray);
    }

    public int get_Left(int[] nArray) {
        return COM.VtblCall(5, this.address, nArray);
    }

    public int get_Top(int[] nArray) {
        return COM.VtblCall(6, this.address, nArray);
    }

    public int get_Height(int[] nArray) {
        return COM.VtblCall(7, this.address, nArray);
    }

    public int get_Width(int[] nArray) {
        return COM.VtblCall(8, this.address, nArray);
    }

    public int get_ShouldDisplayMenuBar(int[] nArray) {
        return COM.VtblCall(9, this.address, nArray);
    }

    public int get_ShouldDisplayStatus(int[] nArray) {
        return COM.VtblCall(10, this.address, nArray);
    }

    public int get_ShouldDisplayToolbar(int[] nArray) {
        return COM.VtblCall(11, this.address, nArray);
    }

    public int get_ShouldDisplayScrollBars(int[] nArray) {
        return COM.VtblCall(12, this.address, nArray);
    }
}

