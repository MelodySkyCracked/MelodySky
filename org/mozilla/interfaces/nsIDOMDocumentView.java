/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMAbstractView;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDocumentView
extends nsISupports {
    public static final String NS_IDOMDOCUMENTVIEW_IID = "{1acdb2ba-1dd2-11b2-95bc-9542495d2569}";

    public nsIDOMAbstractView getDefaultView();
}

