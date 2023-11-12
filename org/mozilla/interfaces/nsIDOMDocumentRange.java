/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDocumentRange
extends nsISupports {
    public static final String NS_IDOMDOCUMENTRANGE_IID = "{7b9badc6-c9bc-447a-8670-dbd195aed24b}";

    public nsIDOMRange createRange();
}

