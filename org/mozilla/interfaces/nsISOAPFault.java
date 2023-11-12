/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsISOAPFault
extends nsISupports {
    public static final String NS_ISOAPFAULT_IID = "{99ec6694-535f-11d4-9a58-000064657374}";

    public nsIDOMElement getElement();

    public void setElement(nsIDOMElement var1);

    public String getFaultNamespaceURI();

    public String getFaultCode();

    public String getFaultString();

    public String getFaultActor();

    public nsIDOMElement getDetail();
}

