/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLCollection;
import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLTableCaptionElement;
import org.mozilla.interfaces.nsIDOMHTMLTableSectionElement;

public interface nsIDOMHTMLTableElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLTABLEELEMENT_IID = "{a6cf90b2-15b3-11d2-932e-00805f8add32}";

    public nsIDOMHTMLTableCaptionElement getCaption();

    public void setCaption(nsIDOMHTMLTableCaptionElement var1);

    public nsIDOMHTMLTableSectionElement getTHead();

    public void setTHead(nsIDOMHTMLTableSectionElement var1);

    public nsIDOMHTMLTableSectionElement getTFoot();

    public void setTFoot(nsIDOMHTMLTableSectionElement var1);

    public nsIDOMHTMLCollection getRows();

    public nsIDOMHTMLCollection getTBodies();

    public String getAlign();

    public void setAlign(String var1);

    public String getBgColor();

    public void setBgColor(String var1);

    public String getBorder();

    public void setBorder(String var1);

    public String getCellPadding();

    public void setCellPadding(String var1);

    public String getCellSpacing();

    public void setCellSpacing(String var1);

    public String getFrame();

    public void setFrame(String var1);

    public String getRules();

    public void setRules(String var1);

    public String getSummary();

    public void setSummary(String var1);

    public String getWidth();

    public void setWidth(String var1);

    public nsIDOMHTMLElement createTHead();

    public void deleteTHead();

    public nsIDOMHTMLElement createTFoot();

    public void deleteTFoot();

    public nsIDOMHTMLElement createCaption();

    public void deleteCaption();

    public nsIDOMHTMLElement insertRow(int var1);

    public void deleteRow(int var1);
}

