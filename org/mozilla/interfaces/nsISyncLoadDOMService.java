/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsISyncLoadDOMService
extends nsISupports {
    public static final String NS_ISYNCLOADDOMSERVICE_IID = "{96a13c30-695a-492c-918b-04ae3edb4e4c}";

    public nsIDOMDocument loadDocument(nsIChannel var1, nsIURI var2);

    public nsIDOMDocument loadDocumentAsXML(nsIChannel var1, nsIURI var2);

    public nsIDOMDocument loadLocalDocument(nsIChannel var1, nsIURI var2);

    public nsIDOMDocument loadLocalXBLDocument(nsIChannel var1);
}

