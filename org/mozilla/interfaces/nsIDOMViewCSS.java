/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMAbstractView;
import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;
import org.mozilla.interfaces.nsIDOMElement;

public interface nsIDOMViewCSS
extends nsIDOMAbstractView {
    public static final String NS_IDOMVIEWCSS_IID = "{0b9341f3-95d4-4fa4-adcd-e119e0db2889}";

    public nsIDOMCSSStyleDeclaration getComputedStyle(nsIDOMElement var1, String var2);
}

