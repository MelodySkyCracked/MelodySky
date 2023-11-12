/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;
import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;

public interface nsIDOMCSSFontFaceRule
extends nsIDOMCSSRule {
    public static final String NS_IDOMCSSFONTFACERULE_IID = "{a6cf90bb-15b3-11d2-932e-00805f8add32}";

    public nsIDOMCSSStyleDeclaration getStyle();
}

