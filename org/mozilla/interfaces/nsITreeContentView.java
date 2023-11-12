/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsITreeContentView
extends nsISupports {
    public static final String NS_ITREECONTENTVIEW_IID = "{5ef62896-0c0a-41f1-bb3c-44a60f5dfdab}";

    public nsIDOMElement getItemAtIndex(int var1);

    public int getIndexOfItem(nsIDOMElement var1);
}

