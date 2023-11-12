/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMCSSValue
extends nsISupports {
    public static final String NS_IDOMCSSVALUE_IID = "{009f7ea5-9e80-41be-b008-db62f10823f2}";
    public static final int CSS_INHERIT = 0;
    public static final int CSS_PRIMITIVE_VALUE = 1;
    public static final int CSS_VALUE_LIST = 2;
    public static final int CSS_CUSTOM = 3;

    public String getCssText();

    public void setCssText(String var1);

    public int getCssValueType();
}

