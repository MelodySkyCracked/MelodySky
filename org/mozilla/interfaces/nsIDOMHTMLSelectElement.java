/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLFormElement;
import org.mozilla.interfaces.nsIDOMHTMLOptionsCollection;

public interface nsIDOMHTMLSelectElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLSELECTELEMENT_IID = "{a6cf9090-15b3-11d2-932e-00805f8add32}";

    public String getType();

    public int getSelectedIndex();

    public void setSelectedIndex(int var1);

    public String getValue();

    public void setValue(String var1);

    public long getLength();

    public void setLength(long var1);

    public nsIDOMHTMLFormElement getForm();

    public nsIDOMHTMLOptionsCollection getOptions();

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public boolean getMultiple();

    public void setMultiple(boolean var1);

    public String getName();

    public void setName(String var1);

    public int getSize();

    public void setSize(int var1);

    public int getTabIndex();

    public void setTabIndex(int var1);

    public void add(nsIDOMHTMLElement var1, nsIDOMHTMLElement var2);

    public void remove(int var1);

    public void blur();

    public void focus();
}

