/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIStringBundle
extends nsISupports {
    public static final String NS_ISTRINGBUNDLE_IID = "{d85a17c2-aa7c-11d2-9b8c-00805f8a16d9}";

    public String getStringFromID(int var1);

    public String getStringFromName(String var1);

    public String formatStringFromID(int var1, String[] var2, long var3);

    public String formatStringFromName(String var1, String[] var2, long var3);

    public nsISimpleEnumerator getSimpleEnumeration();
}

