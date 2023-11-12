/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGPathSeg;

public interface nsIDOMSVGPathSegArcRel
extends nsIDOMSVGPathSeg {
    public static final String NS_IDOMSVGPATHSEGARCREL_IID = "{a685997e-fb47-47c0-a34c-5da11cb66537}";

    public float getX();

    public void setX(float var1);

    public float getY();

    public void setY(float var1);

    public float getR1();

    public void setR1(float var1);

    public float getR2();

    public void setR2(float var1);

    public float getAngle();

    public void setAngle(float var1);

    public boolean getLargeArcFlag();

    public void setLargeArcFlag(boolean var1);

    public boolean getSweepFlag();

    public void setSweepFlag(boolean var1);
}

