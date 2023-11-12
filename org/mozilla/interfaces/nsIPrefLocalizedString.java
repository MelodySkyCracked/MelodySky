/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPrefLocalizedString
extends nsISupports {
    public static final String NS_IPREFLOCALIZEDSTRING_IID = "{ae419e24-1dd1-11b2-b39a-d3e5e7073802}";

    public String getData();

    public void setData(String var1);

    public String toString();

    public void setDataWithLength(long var1, String var3);
}

