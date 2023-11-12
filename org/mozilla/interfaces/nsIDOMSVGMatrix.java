/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGMatrix
extends nsISupports {
    public static final String NS_IDOMSVGMATRIX_IID = "{ec2da3ef-5a99-49ed-aaef-b5af916c14ac}";

    public float getA();

    public void setA(float var1);

    public float getB();

    public void setB(float var1);

    public float getC();

    public void setC(float var1);

    public float getD();

    public void setD(float var1);

    public float getE();

    public void setE(float var1);

    public float getF();

    public void setF(float var1);

    public nsIDOMSVGMatrix multiply(nsIDOMSVGMatrix var1);

    public nsIDOMSVGMatrix inverse();

    public nsIDOMSVGMatrix translate(float var1, float var2);

    public nsIDOMSVGMatrix scale(float var1);

    public nsIDOMSVGMatrix scaleNonUniform(float var1, float var2);

    public nsIDOMSVGMatrix rotate(float var1);

    public nsIDOMSVGMatrix rotateFromVector(float var1, float var2);

    public nsIDOMSVGMatrix flipX();

    public nsIDOMSVGMatrix flipY();

    public nsIDOMSVGMatrix skewX(float var1);

    public nsIDOMSVGMatrix skewY(float var1);
}

