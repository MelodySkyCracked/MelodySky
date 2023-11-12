/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISAXAttributes;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIFeedElementBase
extends nsISupports {
    public static final String NS_IFEEDELEMENTBASE_IID = "{5215291e-fa0a-40c2-8ce7-e86cd1a1d3fa}";

    public nsISAXAttributes getAttributes();

    public void setAttributes(nsISAXAttributes var1);

    public nsIURI getBaseURI();

    public void setBaseURI(nsIURI var1);
}

