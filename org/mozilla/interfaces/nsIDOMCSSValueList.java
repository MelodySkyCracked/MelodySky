/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSValue;

public interface nsIDOMCSSValueList
extends nsIDOMCSSValue {
    public static final String NS_IDOMCSSVALUELIST_IID = "{8f09fa84-39b9-4dca-9b2f-db0eeb186286}";

    public long getLength();

    public nsIDOMCSSValue item(long var1);
}

