/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEnumerator;

public interface nsIRegistryEnumerator
extends nsIEnumerator {
    public static final String NS_IREGISTRYENUMERATOR_IID = "{8cecf236-1dd2-11b2-893c-f9848956eaec}";

    public String currentItemInPlaceUTF8(long[] var1);
}

