/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFeedElementBase;
import org.mozilla.interfaces.nsIURI;

public interface nsIFeedGenerator
extends nsIFeedElementBase {
    public static final String NS_IFEEDGENERATOR_IID = "{0fecd56b-bd92-481b-a486-b8d489cdd385}";

    public String getAgent();

    public void setAgent(String var1);

    public String getVersion();

    public void setVersion(String var1);

    public nsIURI getUri();

    public void setUri(nsIURI var1);
}

