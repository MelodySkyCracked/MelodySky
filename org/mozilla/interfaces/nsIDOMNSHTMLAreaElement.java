/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSHTMLAreaElement
extends nsISupports {
    public static final String NS_IDOMNSHTMLAREAELEMENT_IID = "{3dce9071-f3b9-4280-a6ee-776cdfe3dd9e}";

    public String getProtocol();

    public void setProtocol(String var1);

    public String getHost();

    public void setHost(String var1);

    public String getHostname();

    public void setHostname(String var1);

    public String getPathname();

    public void setPathname(String var1);

    public String getSearch();

    public void setSearch(String var1);

    public String getPort();

    public void setPort(String var1);

    public String getHash();

    public void setHash(String var1);

    public String toString();
}

