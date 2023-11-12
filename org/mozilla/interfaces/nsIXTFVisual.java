/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIXTFElement;

public interface nsIXTFVisual
extends nsIXTFElement {
    public static final String NS_IXTFVISUAL_IID = "{2ee5520b-6593-43c1-b660-4885939a6b68}";
    public static final long NOTIFY_DID_LAYOUT = 131072L;

    public nsIDOMElement getVisualContent();

    public nsIDOMElement getInsertionPoint();

    public boolean getApplyDocumentStyleSheets();

    public void didLayout();
}

