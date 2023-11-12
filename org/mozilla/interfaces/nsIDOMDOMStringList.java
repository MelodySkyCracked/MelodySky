/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDOMStringList
extends nsISupports {
    public static final String NS_IDOMDOMSTRINGLIST_IID = "{0bbae65c-1dde-11d9-8c46-000a95dc234c}";

    public String item(long var1);

    public long getLength();

    public boolean contains(String var1);
}

