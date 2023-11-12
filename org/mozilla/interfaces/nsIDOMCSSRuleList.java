/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSRule;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMCSSRuleList
extends nsISupports {
    public static final String NS_IDOMCSSRULELIST_IID = "{a6cf90c0-15b3-11d2-932e-00805f8add32}";

    public long getLength();

    public nsIDOMCSSRule item(long var1);
}

