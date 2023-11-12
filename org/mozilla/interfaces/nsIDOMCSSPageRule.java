/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;
import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;

public interface nsIDOMCSSPageRule
extends nsIDOMCSSRule {
    public static final String NS_IDOMCSSPAGERULE_IID = "{a6cf90bd-15b3-11d2-932e-00805f8add32}";

    public String getSelectorText();

    public void setSelectorText(String var1);

    public nsIDOMCSSStyleDeclaration getStyle();
}

