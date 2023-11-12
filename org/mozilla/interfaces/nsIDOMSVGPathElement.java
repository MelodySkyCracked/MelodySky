/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedNumber;
import org.mozilla.interfaces.nsIDOMSVGElement;
import org.mozilla.interfaces.nsIDOMSVGPathSegArcAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegArcRel;
import org.mozilla.interfaces.nsIDOMSVGPathSegClosePath;
import org.mozilla.interfaces.nsIDOMSVGPathSegCurvetoCubicAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegCurvetoCubicRel;
import org.mozilla.interfaces.nsIDOMSVGPathSegCurvetoCubicSmoothAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegCurvetoCubicSmoothRel;
import org.mozilla.interfaces.nsIDOMSVGPathSegCurvetoQuadraticAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegCurvetoQuadraticRel;
import org.mozilla.interfaces.nsIDOMSVGPathSegCurvetoQuadraticSmoothAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegCurvetoQuadraticSmoothRel;
import org.mozilla.interfaces.nsIDOMSVGPathSegLinetoAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegLinetoHorizontalAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegLinetoHorizontalRel;
import org.mozilla.interfaces.nsIDOMSVGPathSegLinetoRel;
import org.mozilla.interfaces.nsIDOMSVGPathSegLinetoVerticalAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegLinetoVerticalRel;
import org.mozilla.interfaces.nsIDOMSVGPathSegMovetoAbs;
import org.mozilla.interfaces.nsIDOMSVGPathSegMovetoRel;
import org.mozilla.interfaces.nsIDOMSVGPoint;

public interface nsIDOMSVGPathElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGPATHELEMENT_IID = "{2b19e692-3338-440f-a998-3cb1e8474999}";

    public nsIDOMSVGAnimatedNumber getPathLength();

    public float getTotalLength();

    public nsIDOMSVGPoint getPointAtLength(float var1);

    public long getPathSegAtLength(float var1);

    public nsIDOMSVGPathSegClosePath createSVGPathSegClosePath();

    public nsIDOMSVGPathSegMovetoAbs createSVGPathSegMovetoAbs(float var1, float var2);

    public nsIDOMSVGPathSegMovetoRel createSVGPathSegMovetoRel(float var1, float var2);

    public nsIDOMSVGPathSegLinetoAbs createSVGPathSegLinetoAbs(float var1, float var2);

    public nsIDOMSVGPathSegLinetoRel createSVGPathSegLinetoRel(float var1, float var2);

    public nsIDOMSVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(float var1, float var2, float var3, float var4, float var5, float var6);

    public nsIDOMSVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(float var1, float var2, float var3, float var4, float var5, float var6);

    public nsIDOMSVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(float var1, float var2, float var3, float var4);

    public nsIDOMSVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(float var1, float var2, float var3, float var4);

    public nsIDOMSVGPathSegArcAbs createSVGPathSegArcAbs(float var1, float var2, float var3, float var4, float var5, boolean var6, boolean var7);

    public nsIDOMSVGPathSegArcRel createSVGPathSegArcRel(float var1, float var2, float var3, float var4, float var5, boolean var6, boolean var7);

    public nsIDOMSVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(float var1);

    public nsIDOMSVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(float var1);

    public nsIDOMSVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(float var1);

    public nsIDOMSVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(float var1);

    public nsIDOMSVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(float var1, float var2, float var3, float var4);

    public nsIDOMSVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(float var1, float var2, float var3, float var4);

    public nsIDOMSVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(float var1, float var2);

    public nsIDOMSVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(float var1, float var2);
}

