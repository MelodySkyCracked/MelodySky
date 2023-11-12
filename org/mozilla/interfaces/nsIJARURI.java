/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIURL;

public interface nsIJARURI
extends nsIURL {
    public static final String NS_IJARURI_IID = "{c7e410d3-85f2-11d3-9f63-006008a6efe9}";

    public nsIURI getJARFile();

    public void setJARFile(nsIURI var1);

    public String getJAREntry();

    public void setJAREntry(String var1);
}

