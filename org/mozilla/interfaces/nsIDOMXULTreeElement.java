/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMXULElement;
import org.mozilla.interfaces.nsIDOMXULTextBoxElement;
import org.mozilla.interfaces.nsITreeColumns;
import org.mozilla.interfaces.nsITreeView;

public interface nsIDOMXULTreeElement
extends nsIDOMXULElement {
    public static final String NS_IDOMXULTREEELEMENT_IID = "{1f8111b2-d44d-4d11-845a-a70ae06b7d04}";

    public nsITreeColumns getColumns();

    public nsITreeView getView();

    public void setView(nsITreeView var1);

    public nsIDOMElement getBody();

    public boolean getEditable();

    public void setEditable(boolean var1);

    public nsIDOMXULTextBoxElement getInputField();
}

