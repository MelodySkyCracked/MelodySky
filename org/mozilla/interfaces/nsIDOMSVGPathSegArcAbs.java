/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGPathSeg;

public interface nsIDOMSVGPathSegArcAbs
extends nsIDOMSVGPathSeg {
    public static final String NS_IDOMSVGPATHSEGARCABS_IID = "{c26e1779-604b-4bad-8a29-02d2a2113769}";

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

