/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsIDNSListener;
import org.mozilla.interfaces.nsIDNSRecord;
import org.mozilla.interfaces.nsIEventTarget;
import org.mozilla.interfaces.nsISupports;

public interface nsIDNSService
extends nsISupports {
    public static final String NS_IDNSSERVICE_IID = "{5c8ec09d-bfbf-4eaf-8a36-0d84b5c8f35b}";
    public static final long RESOLVE_BYPASS_CACHE = 1L;
    public static final long RESOLVE_CANONICAL_NAME = 2L;

    public nsICancelable asyncResolve(String var1, long var2, nsIDNSListener var4, nsIEventTarget var5);

    public nsIDNSRecord resolve(String var1, long var2);

    public String getMyHostName();
}

