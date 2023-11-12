/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLCollection;
import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLTableRowElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLTABLEROWELEMENT_IID = "{a6cf90b6-15b3-11d2-932e-00805f8add32}";

    public int getRowIndex();

    public int getSectionRowIndex();

    public nsIDOMHTMLCollection getCells();

    public String getAlign();

    public void setAlign(String var1);

    public String getBgColor();

    public void setBgColor(String var1);

    public String getCh();

    public void setCh(String var1);

    public String getChOff();

    public void setChOff(String var1);

    public String getVAlign();

    public void setVAlign(String var1);

    public nsIDOMHTMLElement insertCell(int var1);

    public void deleteCell(int var1);
}

