/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGMatrix;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGTransform
extends nsISupports {
    public static final String NS_IDOMSVGTRANSFORM_IID = "{29cc2e14-6d18-4710-bda9-a88d9d3bc8dc}";
    public static final int SVG_TRANSFORM_UNKNOWN = 0;
    public static final int SVG_TRANSFORM_MATRIX = 1;
    public static final int SVG_TRANSFORM_TRANSLATE = 2;
    public static final int SVG_TRANSFORM_SCALE = 3;
    public static final int SVG_TRANSFORM_ROTATE = 4;
    public static final int SVG_TRANSFORM_SKEWX = 5;
    public static final int SVG_TRANSFORM_SKEWY = 6;

    public int getType();

    public nsIDOMSVGMatrix getMatrix();

    public float getAngle();

    public void setMatrix(nsIDOMSVGMatrix var1);

    public void setTranslate(float var1, float var2);

    public void setScale(float var1, float var2);

    public void setRotate(float var1, float var2, float var3);

    public void setSkewX(float var1);

    public void setSkewY(float var1);
}

