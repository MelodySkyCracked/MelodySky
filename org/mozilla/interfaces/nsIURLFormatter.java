/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIURLFormatter
extends nsISupports {
    public static final String NS_IURLFORMATTER_IID = "{4ab31d30-372d-11db-a98b-0800200c9a66}";

    public String formatURL(String var1);

    public String formatURLPref(String var1);
}

