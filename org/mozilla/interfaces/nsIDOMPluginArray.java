/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMPlugin;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMPluginArray
extends nsISupports {
    public static final String NS_IDOMPLUGINARRAY_IID = "{f6134680-f28b-11d2-8360-c90899049c3c}";

    public long getLength();

    public nsIDOMPlugin item(long var1);

    public nsIDOMPlugin namedItem(String var1);
}

