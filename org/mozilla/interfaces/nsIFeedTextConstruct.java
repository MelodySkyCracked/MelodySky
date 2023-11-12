/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocumentFragment;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIFeedTextConstruct
extends nsISupports {
    public static final String NS_IFEEDTEXTCONSTRUCT_IID = "{fc97a2a9-d649-4494-931e-db81a156c873}";

    public nsIURI getBase();

    public void setBase(nsIURI var1);

    public String getLang();

    public void setLang(String var1);

    public String getType();

    public void setType(String var1);

    public String getText();

    public void setText(String var1);

    public String plainText();

    public nsIDOMDocumentFragment createDocumentFragment(nsIDOMElement var1);
}

