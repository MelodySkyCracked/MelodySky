/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProtocolHandler;
import org.mozilla.interfaces.nsIURI;

public interface nsIResProtocolHandler
extends nsIProtocolHandler {
    public static final String NS_IRESPROTOCOLHANDLER_IID = "{067ca872-e947-4bd6-8946-a479cb6ba5dd}";

    public void setSubstitution(String var1, nsIURI var2);

    public nsIURI getSubstitution(String var1);

    public boolean hasSubstitution(String var1);

    public String resolveURI(nsIURI var1);
}

