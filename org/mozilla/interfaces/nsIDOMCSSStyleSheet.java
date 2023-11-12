/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;
import org.mozilla.interfaces.nsIDOMCSSRuleList;
import org.mozilla.interfaces.nsIDOMStyleSheet;

public interface nsIDOMCSSStyleSheet
extends nsIDOMStyleSheet {
    public static final String NS_IDOMCSSSTYLESHEET_IID = "{a6cf90c2-15b3-11d2-932e-00805f8add32}";

    public nsIDOMCSSRule getOwnerRule();

    public nsIDOMCSSRuleList getCssRules();

    public long insertRule(String var1, long var2);

    public void deleteRule(long var1);
}

