/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;
import org.mozilla.interfaces.nsIDOMCSSValue;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMCSSStyleDeclaration
extends nsISupports {
    public static final String NS_IDOMCSSSTYLEDECLARATION_IID = "{a6cf90be-15b3-11d2-932e-00805f8add32}";

    public String getCssText();

    public void setCssText(String var1);

    public String getPropertyValue(String var1);

    public nsIDOMCSSValue getPropertyCSSValue(String var1);

    public String removeProperty(String var1);

    public String getPropertyPriority(String var1);

    public void setProperty(String var1, String var2, String var3);

    public long getLength();

    public String item(long var1);

    public nsIDOMCSSRule getParentRule();
}

