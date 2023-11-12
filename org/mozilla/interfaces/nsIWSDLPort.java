/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWSDLBinding;
import org.mozilla.interfaces.nsIWSDLOperation;

public interface nsIWSDLPort
extends nsISupports {
    public static final String NS_IWSDLPORT_IID = "{0458dac1-65de-11d5-9b42-00104bdf5339}";

    public String getName();

    public nsIDOMElement getDocumentation();

    public nsIWSDLBinding getBinding();

    public long getOperationCount();

    public nsIWSDLOperation getOperation(long var1);

    public nsIWSDLOperation getOperationByName(String var1);
}

