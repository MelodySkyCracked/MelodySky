/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEditor;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSEditableElement
extends nsISupports {
    public static final String NS_IDOMNSEDITABLEELEMENT_IID = "{c4a71f8e-82ba-49d7-94f9-beb359361072}";

    public nsIEditor getEditor();
}

