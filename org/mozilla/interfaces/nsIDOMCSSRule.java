/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSStyleSheet;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMCSSRule
extends nsISupports {
    public static final String NS_IDOMCSSRULE_IID = "{a6cf90c1-15b3-11d2-932e-00805f8add32}";
    public static final int UNKNOWN_RULE = 0;
    public static final int STYLE_RULE = 1;
    public static final int CHARSET_RULE = 2;
    public static final int IMPORT_RULE = 3;
    public static final int MEDIA_RULE = 4;
    public static final int FONT_FACE_RULE = 5;
    public static final int PAGE_RULE = 6;

    public int getType();

    public String getCssText();

    public void setCssText(String var1);

    public nsIDOMCSSStyleSheet getParentStyleSheet();

    public nsIDOMCSSRule getParentRule();
}

