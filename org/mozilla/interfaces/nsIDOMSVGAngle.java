/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAngle
extends nsISupports {
    public static final String NS_IDOMSVGANGLE_IID = "{58b6190e-37b3-412a-ba02-1d5ad6c6ea7c}";
    public static final int SVG_ANGLETYPE_UNKNOWN = 0;
    public static final int SVG_ANGLETYPE_UNSPECIFIED = 1;
    public static final int SVG_ANGLETYPE_DEG = 2;
    public static final int SVG_ANGLETYPE_RAD = 3;
    public static final int SVG_ANGLETYPE_GRAD = 4;

    public int getUnitType();

    public float getValue();

    public void setValue(float var1);

    public float getValueInSpecifiedUnits();

    public void setValueInSpecifiedUnits(float var1);

    public String getValueAsString();

    public void setValueAsString(String var1);

    public void newValueSpecifiedUnits(int var1, float var2);

    public void convertToSpecifiedUnits(int var1);
}

