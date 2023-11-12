/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;

public interface nsIDOMCSSCharsetRule
extends nsIDOMCSSRule {
    public static final String NS_IDOMCSSCHARSETRULE_IID = "{19fe78cc-65ff-4b1d-a5d7-9ea89692cec6}";

    public String getEncoding();

    public void setEncoding(String var1);
}

