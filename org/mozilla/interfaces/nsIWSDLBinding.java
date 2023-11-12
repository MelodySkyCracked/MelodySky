/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIWSDLBinding
extends nsISupports {
    public static final String NS_IWSDLBINDING_IID = "{0458dac0-65de-11d5-9b42-00104bdf5339}";

    public String getProtocol();

    public nsIDOMElement getDocumentation();
}

