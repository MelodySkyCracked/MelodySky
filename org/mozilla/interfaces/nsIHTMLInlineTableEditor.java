/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIHTMLInlineTableEditor
extends nsISupports {
    public static final String NS_IHTMLINLINETABLEEDITOR_IID = "{eda2e65c-a758-451f-9b05-77cb8de74ed2}";

    public boolean getInlineTableEditingEnabled();

    public void setInlineTableEditingEnabled(boolean var1);

    public void showInlineTableEditingUI(nsIDOMElement var1);

    public void hideInlineTableEditingUI();

    public void doInlineTableEditingAction(nsIDOMElement var1);

    public void refreshInlineTableEditingUI();
}

