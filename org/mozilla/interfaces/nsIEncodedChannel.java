/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUTF8StringEnumerator;

public interface nsIEncodedChannel
extends nsISupports {
    public static final String NS_IENCODEDCHANNEL_IID = "{30d7ec3a-f376-4652-9276-3092ec57abb6}";

    public nsIUTF8StringEnumerator getContentEncodings();

    public boolean getApplyConversion();

    public void setApplyConversion(boolean var1);
}

