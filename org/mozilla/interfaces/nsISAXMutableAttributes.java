/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISAXAttributes;

public interface nsISAXMutableAttributes
extends nsISAXAttributes {
    public static final String NS_ISAXMUTABLEATTRIBUTES_IID = "{8b1de83d-cebb-49fa-8245-c0fe319eb7b6}";

    public void addAttribute(String var1, String var2, String var3, String var4, String var5);

    public void clear();

    public void removeAttribute(long var1);

    public void setAttributes(nsISAXAttributes var1);

    public void setAttribute(long var1, String var3, String var4, String var5, String var6, String var7);

    public void setLocalName(long var1, String var3);

    public void setQName(long var1, String var3);

    public void setType(long var1, String var3);

    public void setURI(long var1, String var3);

    public void setValue(long var1, String var3);
}

