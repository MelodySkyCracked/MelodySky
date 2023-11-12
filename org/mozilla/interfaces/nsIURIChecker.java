/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIURIChecker
extends nsIRequest {
    public static final String NS_IURICHECKER_IID = "{4660c1a1-be2d-4c78-9baf-c22984176c28}";

    public void init(nsIURI var1);

    public nsIChannel getBaseChannel();

    public void asyncCheck(nsIRequestObserver var1, nsISupports var2);
}

