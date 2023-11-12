/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIXmlRpcFault
extends nsISupports {
    public static final String NS_IXMLRPCFAULT_IID = "{691cb864-0a7e-448c-98ee-4a7f359cf145}";

    public int getFaultCode();

    public String getFaultString();

    public void init(int var1, String var2);

    public String toString();
}

