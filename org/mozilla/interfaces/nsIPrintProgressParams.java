/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPrintProgressParams
extends nsISupports {
    public static final String NS_IPRINTPROGRESSPARAMS_IID = "{ca89b55b-6faf-4051-9645-1c03ef5108f8}";

    public String getDocTitle();

    public void setDocTitle(String var1);

    public String getDocURL();

    public void setDocURL(String var1);
}

