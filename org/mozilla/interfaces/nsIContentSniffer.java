/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIContentSniffer
extends nsISupports {
    public static final String NS_ICONTENTSNIFFER_IID = "{a5710331-74ec-45fb-aa85-ed3bc7c36924}";

    public String getMIMETypeFromContent(byte[] var1, long var2);
}

