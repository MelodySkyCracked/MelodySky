/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMKeyEvent;
import org.mozilla.interfaces.nsISupports;

public interface nsIMenuBoxObject
extends nsISupports {
    public static final String NS_IMENUBOXOBJECT_IID = "{f5099746-5049-4e81-a03e-945d5110fee2}";

    public void openMenu(boolean var1);

    public nsIDOMElement getActiveChild();

    public void setActiveChild(nsIDOMElement var1);

    public boolean handleKeyPress(nsIDOMKeyEvent var1);
}

