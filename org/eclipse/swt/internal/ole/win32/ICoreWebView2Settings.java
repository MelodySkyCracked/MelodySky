/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2Settings
extends IUnknown {
    public ICoreWebView2Settings(long l2) {
        super(l2);
    }

    public int get_IsScriptEnabled(int[] nArray) {
        return COM.VtblCall(3, this.address, nArray);
    }

    public int put_IsScriptEnabled(boolean bl) {
        return COM.VtblCall(4, this.address, bl ? 1 : 0);
    }

    public int get_IsWebMessageEnabled(int[] nArray) {
        return COM.VtblCall(5, this.address, nArray);
    }

    public int put_IsWebMessageEnabled(boolean bl) {
        return COM.VtblCall(6, this.address, bl ? 1 : 0);
    }

    public int get_AreDefaultScriptDialogsEnabled(int[] nArray) {
        return COM.VtblCall(7, this.address, nArray);
    }

    public int put_AreDefaultScriptDialogsEnabled(boolean bl) {
        return COM.VtblCall(8, this.address, bl ? 1 : 0);
    }

    public int get_IsStatusBarEnabled(int[] nArray) {
        return COM.VtblCall(9, this.address, nArray);
    }

    public int put_IsStatusBarEnabled(boolean bl) {
        return COM.VtblCall(10, this.address, bl ? 1 : 0);
    }

    public int get_AreDevToolsEnabled(int[] nArray) {
        return COM.VtblCall(11, this.address, nArray);
    }

    public int put_AreDevToolsEnabled(boolean bl) {
        return COM.VtblCall(12, this.address, bl ? 1 : 0);
    }

    public int get_AreDefaultContextMenusEnabled(int[] nArray) {
        return COM.VtblCall(13, this.address, nArray);
    }

    public int put_AreDefaultContextMenusEnabled(boolean bl) {
        return COM.VtblCall(14, this.address, bl ? 1 : 0);
    }

    public int get_AreHostObjectsAllowed(int[] nArray) {
        return COM.VtblCall(15, this.address, nArray);
    }

    public int put_AreHostObjectsAllowed(boolean bl) {
        return COM.VtblCall(16, this.address, bl ? 1 : 0);
    }

    public int get_IsZoomControlEnabled(int[] nArray) {
        return COM.VtblCall(17, this.address, nArray);
    }

    public int put_IsZoomControlEnabled(boolean bl) {
        return COM.VtblCall(18, this.address, bl ? 1 : 0);
    }

    public int get_IsBuiltInErrorPageEnabled(int[] nArray) {
        return COM.VtblCall(19, this.address, nArray);
    }

    public int put_IsBuiltInErrorPageEnabled(boolean bl) {
        return COM.VtblCall(20, this.address, bl ? 1 : 0);
    }
}

