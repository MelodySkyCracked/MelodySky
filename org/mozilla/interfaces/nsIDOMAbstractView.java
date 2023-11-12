/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocumentView;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMAbstractView
extends nsISupports {
    public static final String NS_IDOMABSTRACTVIEW_IID = "{f51ebade-8b1a-11d3-aae7-0010830123b4}";

    public nsIDOMDocumentView getDocument();
}

