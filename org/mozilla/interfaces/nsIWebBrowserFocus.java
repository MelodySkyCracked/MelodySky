/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIWebBrowserFocus
extends nsISupports {
    public static final String NS_IWEBBROWSERFOCUS_IID = "{9c5d3c58-1dd1-11b2-a1c9-f3699284657a}";

    public void activate();

    public void deactivate();

    public void setFocusAtFirstElement();

    public void setFocusAtLastElement();

    public nsIDOMWindow getFocusedWindow();

    public void setFocusedWindow(nsIDOMWindow var1);

    public nsIDOMElement getFocusedElement();

    public void setFocusedElement(nsIDOMElement var1);
}

