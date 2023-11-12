/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFeedElementBase;
import org.mozilla.interfaces.nsIURI;

public interface nsIFeedPerson
extends nsIFeedElementBase {
    public static final String NS_IFEEDPERSON_IID = "{29cbd45f-f2d3-4b28-b557-3ab7a61ecde4}";

    public String getName();

    public void setName(String var1);

    public String getEmail();

    public void setEmail(String var1);

    public nsIURI getUri();

    public void setUri(nsIURI var1);
}

