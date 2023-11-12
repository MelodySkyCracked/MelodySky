/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISchemaComponent;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWSDLBinding;

public interface nsIWSDLPart
extends nsISupports {
    public static final String NS_IWSDLPART_IID = "{0458dac4-65de-11d5-9b42-00104bdf5339}";

    public String getName();

    public nsIWSDLBinding getBinding();

    public String getType();

    public String getElementName();

    public nsISchemaComponent getSchemaComponent();
}

