/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLFormElement;

public interface nsIDOMHTMLObjectElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLOBJECTELEMENT_IID = "{a6cf90ac-15b3-11d2-932e-00805f8add32}";

    public nsIDOMHTMLFormElement getForm();

    public String getCode();

    public void setCode(String var1);

    public String getAlign();

    public void setAlign(String var1);

    public String getArchive();

    public void setArchive(String var1);

    public String getBorder();

    public void setBorder(String var1);

    public String getCodeBase();

    public void setCodeBase(String var1);

    public String getCodeType();

    public void setCodeType(String var1);

    public String getData();

    public void setData(String var1);

    public boolean getDeclare();

    public void setDeclare(boolean var1);

    public String getHeight();

    public void setHeight(String var1);

    public int getHspace();

    public void setHspace(int var1);

    public String getName();

    public void setName(String var1);

    public String getStandby();

    public void setStandby(String var1);

    public int getTabIndex();

    public void setTabIndex(int var1);

    public String getType();

    public void setType(String var1);

    public String getUseMap();

    public void setUseMap(String var1);

    public int getVspace();

    public void setVspace(int var1);

    public String getWidth();

    public void setWidth(String var1);

    public nsIDOMDocument getContentDocument();
}

