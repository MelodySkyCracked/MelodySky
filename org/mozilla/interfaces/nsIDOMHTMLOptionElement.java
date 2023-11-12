/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLFormElement;

public interface nsIDOMHTMLOptionElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLOPTIONELEMENT_IID = "{a6cf9092-15b3-11d2-932e-00805f8add32}";

    public nsIDOMHTMLFormElement getForm();

    public boolean getDefaultSelected();

    public void setDefaultSelected(boolean var1);

    public String getText();

    public int getIndex();

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public String getLabel();

    public void setLabel(String var1);

    public boolean getSelected();

    public void setSelected(boolean var1);

    public String getValue();

    public void setValue(String var1);
}

