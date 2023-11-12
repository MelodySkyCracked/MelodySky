/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsISupports;

public interface nsIWyciwygChannel
extends nsIChannel {
    public static final String NS_IWYCIWYGCHANNEL_IID = "{280da566-6f19-4487-a8ca-70c5ba1602c1}";

    public void writeToCacheEntry(String var1);

    public void closeCacheEntry(long var1);

    public void setSecurityInfo(nsISupports var1);
}

