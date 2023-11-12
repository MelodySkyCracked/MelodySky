/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;
import org.mozilla.interfaces.nsIDOMCSSRuleList;

public interface nsIDOMCSSMozDocumentRule
extends nsIDOMCSSRule {
    public static final String NS_IDOMCSSMOZDOCUMENTRULE_IID = "{4eb9adac-afaf-4b8a-8640-7340863c1587}";

    public nsIDOMCSSRuleList getCssRules();

    public long insertRule(String var1, long var2);

    public void deleteRule(long var1);
}

