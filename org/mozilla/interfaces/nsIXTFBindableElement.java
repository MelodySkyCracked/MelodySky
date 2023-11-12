/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIXTFBindableElementWrapper;
import org.mozilla.interfaces.nsIXTFElement;

public interface nsIXTFBindableElement
extends nsIXTFElement {
    public static final String NS_IXTFBINDABLEELEMENT_IID = "{8dcc630c-9adc-4c60-9954-a004cb45e4a7}";

    public void onCreated(nsIXTFBindableElementWrapper var1);
}

