/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDictionary
extends nsISupports {
    public static final String NS_IDICTIONARY_IID = "{1dd0cb45-aea3-4a52-8b29-01429a542863}";

    public boolean hasKey(String var1);

    public String[] getKeys(long[] var1);

    public nsISupports getValue(String var1);

    public void setValue(String var1, nsISupports var2);

    public nsISupports deleteValue(String var1);

    public void clear();
}

