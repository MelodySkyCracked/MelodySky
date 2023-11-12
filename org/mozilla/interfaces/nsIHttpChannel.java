/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIHttpHeaderVisitor;
import org.mozilla.interfaces.nsIURI;

public interface nsIHttpChannel
extends nsIChannel {
    public static final String NS_IHTTPCHANNEL_IID = "{9277fe09-f0cc-4cd9-bbce-581dd94b0260}";

    public String getRequestMethod();

    public void setRequestMethod(String var1);

    public nsIURI getReferrer();

    public void setReferrer(nsIURI var1);

    public String getRequestHeader(String var1);

    public void setRequestHeader(String var1, String var2, boolean var3);

    public void visitRequestHeaders(nsIHttpHeaderVisitor var1);

    public boolean getAllowPipelining();

    public void setAllowPipelining(boolean var1);

    public long getRedirectionLimit();

    public void setRedirectionLimit(long var1);

    public long getResponseStatus();

    public String getResponseStatusText();

    public boolean getRequestSucceeded();

    public String getResponseHeader(String var1);

    public void setResponseHeader(String var1, String var2, boolean var3);

    public void visitResponseHeaders(nsIHttpHeaderVisitor var1);

    public boolean isNoStoreResponse();

    public boolean isNoCacheResponse();
}

