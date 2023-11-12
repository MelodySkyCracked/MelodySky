/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.IUnknown;

public class ICoreWebView2
extends IUnknown {
    public ICoreWebView2(long l2) {
        super(l2);
    }

    public int get_Settings(long[] lArray) {
        return COM.VtblCall(3, this.address, lArray);
    }

    public int get_Source(long[] lArray) {
        return COM.VtblCall(4, this.address, lArray);
    }

    public int Navigate(char[] cArray) {
        return COM.VtblCall(5, this.address, cArray);
    }

    public int NavigateToString(char[] cArray) {
        return COM.VtblCall(6, this.address, cArray);
    }

    public int add_NavigationStarting(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(7, this.address, iUnknown.address, lArray);
    }

    public int add_ContentLoading(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(9, this.address, iUnknown.address, lArray);
    }

    public int add_SourceChanged(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(11, this.address, iUnknown.address, lArray);
    }

    public int add_HistoryChanged(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(13, this.address, iUnknown.address, lArray);
    }

    public int add_NavigationCompleted(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(15, this.address, iUnknown.address, lArray);
    }

    public int add_FrameNavigationStarting(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(17, this.address, iUnknown.address, lArray);
    }

    public int add_FrameNavigationCompleted(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(19, this.address, iUnknown.address, lArray);
    }

    public int AddScriptToExecuteOnDocumentCreated(char[] cArray, long l2) {
        return COM.VtblCall(27, this.address, cArray, l2);
    }

    public int ExecuteScript(char[] cArray, IUnknown iUnknown) {
        return COM.VtblCall(29, this.address, cArray, iUnknown.address);
    }

    public int Reload() {
        return COM.VtblCall(31, this.address);
    }

    public int PostWebMessageAsJson(char[] cArray) {
        return COM.VtblCall(32, this.address, cArray);
    }

    public int add_WebMessageReceived(long l2, long[] lArray) {
        return COM.VtblCall(34, this.address, l2, lArray);
    }

    public int get_CanGoBack(int[] nArray) {
        return COM.VtblCall(38, this.address, nArray);
    }

    public int get_CanGoForward(int[] nArray) {
        return COM.VtblCall(39, this.address, nArray);
    }

    public int GoBack() {
        return COM.VtblCall(40, this.address);
    }

    public int GoForward() {
        return COM.VtblCall(41, this.address);
    }

    public int Stop() {
        return COM.VtblCall(43, this.address);
    }

    public int add_NewWindowRequested(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(44, this.address, iUnknown.address, lArray);
    }

    public int add_DocumentTitleChanged(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(46, this.address, iUnknown.address, lArray);
    }

    public int get_DocumentTitle(long[] lArray) {
        return COM.VtblCall(48, this.address, lArray);
    }

    public int AddHostObjectToScript(char[] cArray, long[] lArray) {
        return COM.VtblCall(49, this.address, cArray, lArray);
    }

    public int add_ContainsFullScreenElementChanged(long l2, long[] lArray) {
        return COM.VtblCall(52, this.address, l2, lArray);
    }

    public int get_ContainsFullScreenElement(int[] nArray) {
        return COM.VtblCall(54, this.address, nArray);
    }

    public int add_WindowCloseRequested(IUnknown iUnknown, long[] lArray) {
        return COM.VtblCall(59, this.address, iUnknown.address, lArray);
    }
}

