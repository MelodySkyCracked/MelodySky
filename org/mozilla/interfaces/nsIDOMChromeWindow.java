/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIBrowserDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMChromeWindow
extends nsISupports {
    public static final String NS_IDOMCHROMEWINDOW_IID = "{445fa0fc-2151-4cb4-83d3-34c3e39453de}";
    public static final int STATE_MAXIMIZED = 1;
    public static final int STATE_MINIMIZED = 2;
    public static final int STATE_NORMAL = 3;

    public String getTitle();

    public void setTitle(String var1);

    public int getWindowState();

    public nsIBrowserDOMWindow getBrowserDOMWindow();

    public void setBrowserDOMWindow(nsIBrowserDOMWindow var1);

    public void getAttention();

    public void getAttentionWithCycleCount(int var1);

    public void setCursor(String var1);

    public void maximize();

    public void minimize();

    public void restore();
}

