/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMHTMLCollection;
import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMNodeList;

public interface nsIDOMHTMLDocument
extends nsIDOMDocument {
    public static final String NS_IDOMHTMLDOCUMENT_IID = "{a6cf9084-15b3-11d2-932e-00805f8add32}";

    public String getTitle();

    public void setTitle(String var1);

    public String getReferrer();

    public String getURL();

    public nsIDOMHTMLElement getBody();

    public void setBody(nsIDOMHTMLElement var1);

    public nsIDOMHTMLCollection getImages();

    public nsIDOMHTMLCollection getApplets();

    public nsIDOMHTMLCollection getLinks();

    public nsIDOMHTMLCollection getForms();

    public nsIDOMHTMLCollection getAnchors();

    public String getCookie();

    public void setCookie(String var1);

    public void close();

    public nsIDOMNodeList getElementsByName(String var1);
}

