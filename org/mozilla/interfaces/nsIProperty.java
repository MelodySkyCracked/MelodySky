/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIProperty
extends nsISupports {
    public static final String NS_IPROPERTY_IID = "{6dcf9030-a49f-11d5-910d-0010a4e73d9a}";

    public String getName();

    public nsIVariant getValue();
}

