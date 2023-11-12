/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURL;
import org.mozilla.interfaces.nsIXmlRpcClientListener;
import org.mozilla.interfaces.nsIXmlRpcFault;

public interface nsIXmlRpcClient
extends nsISupports {
    public static final String NS_IXMLRPCCLIENT_IID = "{4d7d15c0-3747-4f7f-b6b3-792a5ea1a9aa}";
    public static final long INT = 1L;
    public static final long BOOLEAN = 2L;
    public static final long STRING = 3L;
    public static final long DOUBLE = 4L;
    public static final long DATETIME = 5L;
    public static final long ARRAY = 6L;
    public static final long STRUCT = 7L;

    public void init(String var1);

    public void setAuthentication(String var1, String var2);

    public void clearAuthentication(String var1, String var2);

    public void setEncoding(String var1);

    public nsIURL getServerURL();

    public void asyncCall(nsIXmlRpcClientListener var1, nsISupports var2, String var3, nsISupports[] var4, long var5);

    public boolean getInProgress();

    public nsIXmlRpcFault getFault();

    public nsISupports getResult();

    public long getResponseStatus();

    public long getResponseString();

    public nsISupports createType(long var1, String[] var3);
}

