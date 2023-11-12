/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMElementCSSInlineStyle
extends nsISupports {
    public static final String NS_IDOMELEMENTCSSINLINESTYLE_IID = "{99715845-95fc-4a56-aa53-214b65c26e22}";

    public nsIDOMCSSStyleDeclaration getStyle();
}

