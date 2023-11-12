/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDocCharset
extends nsISupports {
    public static final String NS_IDOCCHARSET_IID = "{9c18bb4e-1dd1-11b2-bf91-9cc82c275823}";

    public String getCharset();

    public void setCharset(String var1);
}

