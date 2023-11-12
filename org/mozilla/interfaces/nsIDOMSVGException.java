/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGException
extends nsISupports {
    public static final String NS_IDOMSVGEXCEPTION_IID = "{64e6f0e1-af99-4bb9-ab25-7e56012f0021}";
    public static final int SVG_WRONG_TYPE_ERR = 0;
    public static final int SVG_INVALID_VALUE_ERR = 1;
    public static final int SVG_MATRIX_NOT_INVERTABLE = 2;

    public int getCode();
}

