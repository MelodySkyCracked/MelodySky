/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWSDLBinding;
import org.mozilla.interfaces.nsIWSDLPart;

public interface nsIWSDLMessage
extends nsISupports {
    public static final String NS_IWSDLMESSAGE_IID = "{0458dac3-65de-11d5-9b42-00104bdf5339}";

    public String getName();

    public nsIDOMElement getDocumentation();

    public nsIWSDLBinding getBinding();

    public long getPartCount();

    public nsIWSDLPart getPart(long var1);

    public nsIWSDLPart getPartByName(String var1);
}

