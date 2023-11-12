/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIURI
extends nsISupports {
    public static final String NS_IURI_IID = "{07a22cc0-0ce5-11d3-9331-00104ba0fd40}";

    public String getSpec();

    public void setSpec(String var1);

    public String getPrePath();

    public String getScheme();

    public void setScheme(String var1);

    public String getUserPass();

    public void setUserPass(String var1);

    public String getUsername();

    public void setUsername(String var1);

    public String getPassword();

    public void setPassword(String var1);

    public String getHostPort();

    public void setHostPort(String var1);

    public String getHost();

    public void setHost(String var1);

    public int getPort();

    public void setPort(int var1);

    public String getPath();

    public void setPath(String var1);

    public boolean _equals(nsIURI var1);

    public boolean schemeIs(String var1);

    public nsIURI _clone();

    public String resolve(String var1);

    public String getAsciiSpec();

    public String getAsciiHost();

    public String getOriginCharset();
}

