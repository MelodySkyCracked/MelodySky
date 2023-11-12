/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLFormElement;

public interface nsIDOMHTMLInputElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLINPUTELEMENT_IID = "{a6cf9093-15b3-11d2-932e-00805f8add32}";

    public String getDefaultValue();

    public void setDefaultValue(String var1);

    public boolean getDefaultChecked();

    public void setDefaultChecked(boolean var1);

    public nsIDOMHTMLFormElement getForm();

    public String getAccept();

    public void setAccept(String var1);

    public String getAccessKey();

    public void setAccessKey(String var1);

    public String getAlign();

    public void setAlign(String var1);

    public String getAlt();

    public void setAlt(String var1);

    public boolean getChecked();

    public void setChecked(boolean var1);

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public int getMaxLength();

    public void setMaxLength(int var1);

    public String getName();

    public void setName(String var1);

    public boolean getReadOnly();

    public void setReadOnly(boolean var1);

    public long getSize();

    public void setSize(long var1);

    public String getSrc();

    public void setSrc(String var1);

    public int getTabIndex();

    public void setTabIndex(int var1);

    public String getType();

    public void setType(String var1);

    public String getUseMap();

    public void setUseMap(String var1);

    public String getValue();

    public void setValue(String var1);

    public void blur();

    public void focus();

    public void select();

    public void click();
}

