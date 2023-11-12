/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWSDLBinding;
import org.mozilla.interfaces.nsIWSDLMessage;

public interface nsIWSDLOperation
extends nsISupports {
    public static final String NS_IWSDLOPERATION_IID = "{0458dac2-65de-11d5-9b42-00104bdf5339}";

    public String getName();

    public nsIDOMElement getDocumentation();

    public nsIWSDLBinding getBinding();

    public nsIWSDLMessage getInput();

    public nsIWSDLMessage getOutput();

    public long getFaultCount();

    public nsIWSDLMessage getFault(long var1);

    public long getParameterOrderCount();

    public String getParameter(long var1);

    public long getParameterIndex(String var1);
}

