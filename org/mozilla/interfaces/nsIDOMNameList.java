/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNameList
extends nsISupports {
    public static final String NS_IDOMNAMELIST_IID = "{faaf1b80-1ddd-11d9-8c46-000a95dc234c}";

    public String getName(long var1);

    public String getNamespaceURI(long var1);

    public long getLength();

    public boolean contains(String var1);

    public boolean containsNS(String var1, String var2);
}

