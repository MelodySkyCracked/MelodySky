/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsIProperties;
import org.mozilla.interfaces.nsISimpleEnumerator;

public interface nsIPersistentProperties
extends nsIProperties {
    public static final String NS_IPERSISTENTPROPERTIES_IID = "{1a180f60-93b2-11d2-9b8b-00805f8a16d9}";

    public void load(nsIInputStream var1);

    public void save(nsIOutputStream var1, String var2);

    public void subclass(nsIPersistentProperties var1);

    public nsISimpleEnumerator enumerate();

    public String getStringProperty(String var1);

    public String setStringProperty(String var1, String var2);
}

