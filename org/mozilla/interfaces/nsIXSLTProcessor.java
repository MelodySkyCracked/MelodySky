/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMDocumentFragment;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIXSLTProcessor
extends nsISupports {
    public static final String NS_IXSLTPROCESSOR_IID = "{4a91aeb3-4100-43ee-a21e-9866268757c5}";

    public void importStylesheet(nsIDOMNode var1);

    public nsIDOMDocumentFragment transformToFragment(nsIDOMNode var1, nsIDOMDocument var2);

    public nsIDOMDocument transformToDocument(nsIDOMNode var1);

    public void setParameter(String var1, String var2, nsIVariant var3);

    public nsIVariant getParameter(String var1, String var2);

    public void removeParameter(String var1, String var2);

    public void clearParameters();

    public void reset();
}

