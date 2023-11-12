/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOM3Node;
import org.mozilla.interfaces.nsIDOMDOMConfiguration;
import org.mozilla.interfaces.nsIDOMNode;

public interface nsIDOM3Document
extends nsIDOM3Node {
    public static final String NS_IDOM3DOCUMENT_IID = "{2e0e9ea1-72ab-4d9e-bdeb-ca64e1abeba4}";

    public String getInputEncoding();

    public String getXmlEncoding();

    public boolean getXmlStandalone();

    public void setXmlStandalone(boolean var1);

    public String getXmlVersion();

    public void setXmlVersion(String var1);

    public boolean getStrictErrorChecking();

    public void setStrictErrorChecking(boolean var1);

    public String getDocumentURI();

    public void setDocumentURI(String var1);

    public nsIDOMNode adoptNode(nsIDOMNode var1);

    public nsIDOMDOMConfiguration getDomConfig();

    public void normalizeDocument();

    public nsIDOMNode renameNode(nsIDOMNode var1, String var2, String var3);
}

