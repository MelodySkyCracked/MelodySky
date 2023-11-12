/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLElement;

public interface nsIDOMHTMLParamElement
extends nsIDOMHTMLElement {
    public static final String NS_IDOMHTMLPARAMELEMENT_IID = "{a6cf90ad-15b3-11d2-932e-00805f8add32}";

    public String getName();

    public void setName(String var1);

    public String getType();

    public void setType(String var1);

    public String getValue();

    public void setValue(String var1);

    public String getValueType();

    public void setValueType(String var1);
}

