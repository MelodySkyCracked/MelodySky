/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICookie;

public interface nsICookie2
extends nsICookie {
    public static final String NS_ICOOKIE2_IID = "{d3493503-7854-46ed-8284-8af54a847efb}";

    public String getRawHost();

    public boolean getIsSession();

    public long getExpiry();
}

