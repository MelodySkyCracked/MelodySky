/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMCSSValue;
import org.mozilla.interfaces.nsIDOMCounter;
import org.mozilla.interfaces.nsIDOMRGBColor;
import org.mozilla.interfaces.nsIDOMRect;

public interface nsIDOMCSSPrimitiveValue
extends nsIDOMCSSValue {
    public static final String NS_IDOMCSSPRIMITIVEVALUE_IID = "{e249031f-8df9-4e7a-b644-18946dce0019}";
    public static final int CSS_UNKNOWN = 0;
    public static final int CSS_NUMBER = 1;
    public static final int CSS_PERCENTAGE = 2;
    public static final int CSS_EMS = 3;
    public static final int CSS_EXS = 4;
    public static final int CSS_PX = 5;
    public static final int CSS_CM = 6;
    public static final int CSS_MM = 7;
    public static final int CSS_IN = 8;
    public static final int CSS_PT = 9;
    public static final int CSS_PC = 10;
    public static final int CSS_DEG = 11;
    public static final int CSS_RAD = 12;
    public static final int CSS_GRAD = 13;
    public static final int CSS_MS = 14;
    public static final int CSS_S = 15;
    public static final int CSS_HZ = 16;
    public static final int CSS_KHZ = 17;
    public static final int CSS_DIMENSION = 18;
    public static final int CSS_STRING = 19;
    public static final int CSS_URI = 20;
    public static final int CSS_IDENT = 21;
    public static final int CSS_ATTR = 22;
    public static final int CSS_COUNTER = 23;
    public static final int CSS_RECT = 24;
    public static final int CSS_RGBCOLOR = 25;

    public int getPrimitiveType();

    public void setFloatValue(int var1, float var2);

    public float getFloatValue(int var1);

    public void setStringValue(int var1, String var2);

    public String getStringValue();

    public nsIDOMCounter getCounterValue();

    public nsIDOMRect getRectValue();

    public nsIDOMRGBColor getRGBColorValue();
}

