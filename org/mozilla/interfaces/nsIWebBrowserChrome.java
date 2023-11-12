/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebBrowser;

public interface nsIWebBrowserChrome
extends nsISupports {
    public static final String NS_IWEBBROWSERCHROME_IID = "{ba434c60-9d52-11d3-afb0-00a024ffc08c}";
    public static final long STATUS_SCRIPT = 1L;
    public static final long STATUS_SCRIPT_DEFAULT = 2L;
    public static final long STATUS_LINK = 3L;
    public static final long CHROME_DEFAULT = 1L;
    public static final long CHROME_WINDOW_BORDERS = 2L;
    public static final long CHROME_WINDOW_CLOSE = 4L;
    public static final long CHROME_WINDOW_RESIZE = 8L;
    public static final long CHROME_MENUBAR = 16L;
    public static final long CHROME_TOOLBAR = 32L;
    public static final long CHROME_LOCATIONBAR = 64L;
    public static final long CHROME_STATUSBAR = 128L;
    public static final long CHROME_PERSONAL_TOOLBAR = 256L;
    public static final long CHROME_SCROLLBARS = 512L;
    public static final long CHROME_TITLEBAR = 1024L;
    public static final long CHROME_EXTRA = 2048L;
    public static final long CHROME_WITH_SIZE = 4096L;
    public static final long CHROME_WITH_POSITION = 8192L;
    public static final long CHROME_WINDOW_MIN = 16384L;
    public static final long CHROME_WINDOW_POPUP = 32768L;
    public static final long CHROME_WINDOW_RAISED = 0x2000000L;
    public static final long CHROME_WINDOW_LOWERED = 0x4000000L;
    public static final long CHROME_CENTER_SCREEN = 0x8000000L;
    public static final long CHROME_DEPENDENT = 0x10000000L;
    public static final long CHROME_MODAL = 0x20000000L;
    public static final long CHROME_OPENAS_DIALOG = 0x40000000L;
    public static final long CHROME_OPENAS_CHROME = 0x80000000L;
    public static final long CHROME_ALL = 4094L;

    public void setStatus(long var1, String var3);

    public nsIWebBrowser getWebBrowser();

    public void setWebBrowser(nsIWebBrowser var1);

    public long getChromeFlags();

    public void setChromeFlags(long var1);

    public void destroyBrowserWindow();

    public void sizeBrowserTo(int var1, int var2);

    public void showAsModal();

    public boolean isWindowModal();

    public void exitModalEventLoop(long var1);
}

