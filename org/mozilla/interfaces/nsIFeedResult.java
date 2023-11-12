/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFeedContainer;
import org.mozilla.interfaces.nsIProperties;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIFeedResult
extends nsISupports {
    public static final String NS_IFEEDRESULT_IID = "{7a180b78-0f46-4569-8c22-f3d720ea1c57}";

    public boolean getBozo();

    public void setBozo(boolean var1);

    public nsIFeedContainer getDoc();

    public void setDoc(nsIFeedContainer var1);

    public nsIURI getUri();

    public void setUri(nsIURI var1);

    public String getVersion();

    public void setVersion(String var1);

    public nsIURI getStylesheet();

    public void setStylesheet(nsIURI var1);

    public nsIProperties getHeaders();

    public void setHeaders(nsIProperties var1);

    public void registerExtensionPrefix(String var1, String var2);
}

