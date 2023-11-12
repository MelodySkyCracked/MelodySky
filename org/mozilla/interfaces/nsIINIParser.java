/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUTF8StringEnumerator;

public interface nsIINIParser
extends nsISupports {
    public static final String NS_IINIPARSER_IID = "{7eb955f6-3e78-4d39-b72f-c1bf12a94bce}";

    public nsIUTF8StringEnumerator getSections();

    public nsIUTF8StringEnumerator getKeys(String var1);

    public String getString(String var1, String var2);
}

