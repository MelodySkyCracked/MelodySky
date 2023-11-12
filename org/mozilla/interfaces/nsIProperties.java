/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIProperties
extends nsISupports {
    public static final String NS_IPROPERTIES_IID = "{78650582-4e93-4b60-8e85-26ebd3eb14ca}";

    public nsISupports get(String var1, String var2);

    public void set(String var1, nsISupports var2);

    public boolean has(String var1);

    public void undefine(String var1);

    public String[] getKeys(long[] var1);
}

