/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISHEntry;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIDocShellLoadInfo
extends nsISupports {
    public static final String NS_IDOCSHELLLOADINFO_IID = "{4f813a88-7aca-4607-9896-d97270cdf15e}";
    public static final int loadNormal = 0;
    public static final int loadNormalReplace = 1;
    public static final int loadHistory = 2;
    public static final int loadReloadNormal = 3;
    public static final int loadReloadBypassCache = 4;
    public static final int loadReloadBypassProxy = 5;
    public static final int loadReloadBypassProxyAndCache = 6;
    public static final int loadLink = 7;
    public static final int loadRefresh = 8;
    public static final int loadReloadCharsetChange = 9;
    public static final int loadBypassHistory = 10;
    public static final int loadStopContent = 11;
    public static final int loadStopContentAndReplace = 12;
    public static final int loadNormalExternal = 13;

    public nsIURI getReferrer();

    public void setReferrer(nsIURI var1);

    public nsISupports getOwner();

    public void setOwner(nsISupports var1);

    public boolean getInheritOwner();

    public void setInheritOwner(boolean var1);

    public int getLoadType();

    public void setLoadType(int var1);

    public nsISHEntry getSHEntry();

    public void setSHEntry(nsISHEntry var1);

    public String getTarget();

    public void setTarget(String var1);

    public nsIInputStream getPostDataStream();

    public void setPostDataStream(nsIInputStream var1);

    public nsIInputStream getHeadersStream();

    public void setHeadersStream(nsIInputStream var1);

    public boolean getSendReferrer();

    public void setSendReferrer(boolean var1);
}

