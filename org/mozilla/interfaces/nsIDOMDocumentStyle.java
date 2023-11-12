/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMStyleSheetList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDocumentStyle
extends nsISupports {
    public static final String NS_IDOMDOCUMENTSTYLE_IID = "{3d9f4973-dd2e-48f5-b5f7-2634e09eadd9}";

    public nsIDOMStyleSheetList getStyleSheets();
}

