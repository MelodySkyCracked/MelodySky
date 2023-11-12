/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLocation
extends nsISupports {
    public static final String NS_IDOMLOCATION_IID = "{a6cf906d-15b3-11d2-932e-00805f8add32}";

    public String getHash();

    public void setHash(String var1);

    public String getHost();

    public void setHost(String var1);

    public String getHostname();

    public void setHostname(String var1);

    public String getHref();

    public void setHref(String var1);

    public String getPathname();

    public void setPathname(String var1);

    public String getPort();

    public void setPort(String var1);

    public String getProtocol();

    public void setProtocol(String var1);

    public String getSearch();

    public void setSearch(String var1);

    public void replace(String var1);

    public void assign(String var1);

    public String toString();
}

