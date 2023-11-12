/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;
import org.mozilla.interfaces.nsIDOMCSSStyleSheet;
import org.mozilla.interfaces.nsIDOMMediaList;

public interface nsIDOMCSSImportRule
extends nsIDOMCSSRule {
    public static final String NS_IDOMCSSIMPORTRULE_IID = "{a6cf90cf-15b3-11d2-932e-00805f8add32}";

    public String getHref();

    public nsIDOMMediaList getMedia();

    public nsIDOMCSSStyleSheet getStyleSheet();
}

