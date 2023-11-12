/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGMatrix;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGLength
extends nsISupports {
    public static final String NS_IDOMSVGLENGTH_IID = "{10231b04-7482-4960-bada-3dced0d586fc}";
    public static final int SVG_LENGTHTYPE_UNKNOWN = 0;
    public static final int SVG_LENGTHTYPE_NUMBER = 1;
    public static final int SVG_LENGTHTYPE_PERCENTAGE = 2;
    public static final int SVG_LENGTHTYPE_EMS = 3;
    public static final int SVG_LENGTHTYPE_EXS = 4;
    public static final int SVG_LENGTHTYPE_PX = 5;
    public static final int SVG_LENGTHTYPE_CM = 6;
    public static final int SVG_LENGTHTYPE_MM = 7;
    public static final int SVG_LENGTHTYPE_IN = 8;
    public static final int SVG_LENGTHTYPE_PT = 9;
    public static final int SVG_LENGTHTYPE_PC = 10;

    public int getUnitType();

    public float getValue();

    public void setValue(float var1);

    public float getValueInSpecifiedUnits();

    public void setValueInSpecifiedUnits(float var1);

    public String getValueAsString();

    public void setValueAsString(String var1);

    public void newValueSpecifiedUnits(int var1, float var2);

    public void convertToSpecifiedUnits(int var1);

    public float getTransformedValue(nsIDOMSVGMatrix var1);
}

