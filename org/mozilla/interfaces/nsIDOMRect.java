/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSPrimitiveValue;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMRect
extends nsISupports {
    public static final String NS_IDOMRECT_IID = "{71735f62-ac5c-4236-9a1f-5ffb280d531c}";

    public nsIDOMCSSPrimitiveValue getTop();

    public nsIDOMCSSPrimitiveValue getRight();

    public nsIDOMCSSPrimitiveValue getBottom();

    public nsIDOMCSSPrimitiveValue getLeft();
}

