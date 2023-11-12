/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLCollection;
import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLTableSectionElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLTABLESECTIONELEMENT_IID = "{a6cf90b5-15b3-11d2-932e-00805f8add32}";

    public String getAlign();

    public void setAlign(String var1);

    public String getCh();

    public void setCh(String var1);

    public String getChOff();

    public void setChOff(String var1);

    public String getVAlign();

    public void setVAlign(String var1);

    public nsIDOMHTMLCollection getRows();

    public nsIDOMHTMLElement insertRow(int var1);

    public void deleteRow(int var1);
}

