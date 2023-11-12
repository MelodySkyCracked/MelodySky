/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;
import org.mozilla.interfaces.nsIDOMDocumentStyle;
import org.mozilla.interfaces.nsIDOMElement;

public interface nsIDOMDocumentCSS
extends nsIDOMDocumentStyle {
    public static final String NS_IDOMDOCUMENTCSS_IID = "{39f76c23-45b2-428a-9240-a981e5abf148}";

    public nsIDOMCSSStyleDeclaration getOverrideStyle(nsIDOMElement var1, String var2);
}

