/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsIDNSRecord;
import org.mozilla.interfaces.nsISupports;

public interface nsIDNSListener
extends nsISupports {
    public static final String NS_IDNSLISTENER_IID = "{41466a9f-f027-487d-a96c-af39e629b8d2}";

    public void onLookupComplete(nsICancelable var1, nsIDNSRecord var2, long var3);
}

