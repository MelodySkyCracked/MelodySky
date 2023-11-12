/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProxyInfo;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIHttpChannelInternal
extends nsISupports {
    public static final String NS_IHTTPCHANNELINTERNAL_IID = "{f3764874-ed7e-4873-883c-11d67a4e3638}";

    public nsIURI getDocumentURI();

    public void setDocumentURI(nsIURI var1);

    public void getRequestVersion(long[] var1, long[] var2);

    public void getResponseVersion(long[] var1, long[] var2);

    public void setCookie(String var1);

    public nsIProxyInfo getProxyInfo();
}

