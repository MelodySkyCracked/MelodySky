/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocumentFragment;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIScriptableUnescapeHTML
extends nsISupports {
    public static final String NS_ISCRIPTABLEUNESCAPEHTML_IID = "{3ab244a9-f09d-44da-9e3f-ee4d67367f2d}";

    public String unescape(String var1);

    public nsIDOMDocumentFragment parseFragment(String var1, boolean var2, nsIURI var3, nsIDOMElement var4);
}

