/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;
import org.mozilla.interfaces.nsIDOMCSSRuleList;
import org.mozilla.interfaces.nsIDOMMediaList;

public interface nsIDOMCSSMediaRule
extends nsIDOMCSSRule {
    public static final String NS_IDOMCSSMEDIARULE_IID = "{a6cf90bc-15b3-11d2-932e-00805f8add32}";

    public nsIDOMMediaList getMedia();

    public nsIDOMCSSRuleList getCssRules();

    public long insertRule(String var1, long var2);

    public void deleteRule(long var1);
}

