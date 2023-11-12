/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIWSDLSOAPBinding;

public interface nsISOAPOperationBinding
extends nsIWSDLSOAPBinding {
    public static final String NS_ISOAPOPERATIONBINDING_IID = "{0458dac8-65de-11d5-9b42-00104bdf5339}";

    public int getStyle();

    public String getSoapAction();
}

