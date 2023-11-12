/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIBoxObject;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMLocation;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSDocument
extends nsISupports {
    public static final String NS_IDOMNSDOCUMENT_IID = "{a6cf90cd-15b3-11d2-932e-00805f8add32}";

    public String getCharacterSet();

    public String getDir();

    public void setDir(String var1);

    public nsIDOMLocation getLocation();

    public String getTitle();

    public void setTitle(String var1);

    public String getContentType();

    public String getLastModified();

    public String getReferrer();

    public nsIBoxObject getBoxObjectFor(nsIDOMElement var1);

    public void setBoxObjectFor(nsIDOMElement var1, nsIBoxObject var2);
}

