/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSHTMLButtonElement
extends nsISupports {
    public static final String NS_IDOMNSHTMLBUTTONELEMENT_IID = "{c914d7a4-63b3-4d40-943f-91a3c7ab0d4d}";

    public void blur();

    public void focus();

    public void click();

    public String getType();

    public void setType(String var1);
}

