/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLFormElement;

public interface nsIDOMHTMLButtonElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLBUTTONELEMENT_IID = "{a6cf9095-15b3-11d2-932e-00805f8add32}";

    public nsIDOMHTMLFormElement getForm();

    public String getAccessKey();

    public void setAccessKey(String var1);

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public String getName();

    public void setName(String var1);

    public int getTabIndex();

    public void setTabIndex(int var1);

    public String getValue();

    public void setValue(String var1);
}

