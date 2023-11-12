/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;
import org.mozilla.interfaces.nsIDOMCSSValue;
import org.mozilla.interfaces.nsIDOMSVGAnimatedString;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGStylable
extends nsISupports {
    public static final String NS_IDOMSVGSTYLABLE_IID = "{ea8a6cb1-9176-45db-989d-d0e89f563d7e}";

    public nsIDOMSVGAnimatedString getClassName();

    public nsIDOMCSSStyleDeclaration getStyle();

    public nsIDOMCSSValue getPresentationAttribute(String var1);
}

