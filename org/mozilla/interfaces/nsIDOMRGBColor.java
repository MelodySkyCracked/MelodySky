/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSPrimitiveValue;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMRGBColor
extends nsISupports {
    public static final String NS_IDOMRGBCOLOR_IID = "{6aff3102-320d-4986-9790-12316bb87cf9}";

    public nsIDOMCSSPrimitiveValue getRed();

    public nsIDOMCSSPrimitiveValue getGreen();

    public nsIDOMCSSPrimitiveValue getBlue();
}

