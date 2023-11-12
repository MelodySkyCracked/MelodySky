/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;

public interface nsIContentSniffer_MOZILLA_1_8_BRANCH
extends nsISupports {
    public static final String NS_ICONTENTSNIFFER_MOZILLA_1_8_BRANCH_IID = "{a5772d1b-fc63-495e-a169-96e8d3311af0}";

    public String getMIMETypeFromContent(nsIRequest var1, byte[] var2, long var3);
}

