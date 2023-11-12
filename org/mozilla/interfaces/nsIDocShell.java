/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChromeEventHandler;
import org.mozilla.interfaces.nsIContentViewer;
import org.mozilla.interfaces.nsIDocShellLoadInfo;
import org.mozilla.interfaces.nsIDocumentCharsetInfo;
import org.mozilla.interfaces.nsISecureBrowserUI;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIDocShell
extends nsISupports {
    public static final String NS_IDOCSHELL_IID = "{9f0c7461-b9a4-47f6-b88c-421dce1bce66}";
    public static final int INTERNAL_LOAD_FLAGS_NONE = 0;
    public static final int INTERNAL_LOAD_FLAGS_INHERIT_OWNER = 1;
    public static final int INTERNAL_LOAD_FLAGS_DONT_SEND_REFERRER = 2;
    public static final int INTERNAL_LOAD_FLAGS_ALLOW_THIRD_PARTY_FIXUP = 4;
    public static final int INTERNAL_LOAD_FLAGS_FIRST_LOAD = 8;
    public static final int ENUMERATE_FORWARDS = 0;
    public static final int ENUMERATE_BACKWARDS = 1;
    public static final long APP_TYPE_UNKNOWN = 0L;
    public static final long APP_TYPE_MAIL = 1L;
    public static final long APP_TYPE_EDITOR = 2L;
    public static final long BUSY_FLAGS_NONE = 0L;
    public static final long BUSY_FLAGS_BUSY = 1L;
    public static final long BUSY_FLAGS_BEFORE_PAGE_LOAD = 2L;
    public static final long BUSY_FLAGS_PAGE_LOADING = 4L;
    public static final long LOAD_CMD_NORMAL = 1L;
    public static final long LOAD_CMD_RELOAD = 2L;
    public static final long LOAD_CMD_HISTORY = 4L;

    public void createLoadInfo(nsIDocShellLoadInfo[] var1);

    public void prepareForNewContentModel();

    public void setCurrentURI(nsIURI var1);

    public nsIContentViewer getContentViewer();

    public nsIChromeEventHandler getChromeEventHandler();

    public void setChromeEventHandler(nsIChromeEventHandler var1);

    public nsIDocumentCharsetInfo getDocumentCharsetInfo();

    public void setDocumentCharsetInfo(nsIDocumentCharsetInfo var1);

    public boolean getAllowPlugins();

    public void setAllowPlugins(boolean var1);

    public boolean getAllowJavascript();

    public void setAllowJavascript(boolean var1);

    public boolean getAllowMetaRedirects();

    public void setAllowMetaRedirects(boolean var1);

    public boolean getAllowSubframes();

    public void setAllowSubframes(boolean var1);

    public boolean getAllowImages();

    public void setAllowImages(boolean var1);

    public nsISimpleEnumerator getDocShellEnumerator(int var1, int var2);

    public long getAppType();

    public void setAppType(long var1);

    public boolean getAllowAuth();

    public void setAllowAuth(boolean var1);

    public float getZoom();

    public void setZoom(float var1);

    public int getMarginWidth();

    public void setMarginWidth(int var1);

    public int getMarginHeight();

    public void setMarginHeight(int var1);

    public boolean getHasFocus();

    public void setHasFocus(boolean var1);

    public boolean getCanvasHasFocus();

    public void setCanvasHasFocus(boolean var1);

    public void tabToTreeOwner(boolean var1, boolean[] var2);

    public long getBusyFlags();

    public long getLoadType();

    public void setLoadType(long var1);

    public boolean isBeingDestroyed();

    public boolean getIsExecutingOnLoadHandler();

    public nsISupports getLayoutHistoryState();

    public void setLayoutHistoryState(nsISupports var1);

    public boolean getShouldSaveLayoutState();

    public nsISecureBrowserUI getSecurityUI();

    public void setSecurityUI(nsISecureBrowserUI var1);

    public void suspendRefreshURIs();

    public void resumeRefreshURIs();

    public void beginRestore(nsIContentViewer var1, boolean var2);

    public void finishRestore();

    public boolean getRestoringDocument();

    public boolean getUseErrorPages();

    public void setUseErrorPages(boolean var1);

    public int getPreviousTransIndex();

    public int getLoadedTransIndex();

    public void historyPurged(int var1);
}

