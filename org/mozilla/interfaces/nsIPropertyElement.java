/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPropertyElement
extends nsISupports {
    public static final String NS_IPROPERTYELEMENT_IID = "{283ee646-1aef-11d4-98b3-00c04fa0ce9a}";

    public String getKey();

    public void setKey(String var1);

    public String getValue();

    public void setValue(String var1);
}

