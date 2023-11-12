/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedEnumeration;
import org.mozilla.interfaces.nsIDOMSVGAnimatedTransformList;
import org.mozilla.interfaces.nsIDOMSVGElement;

public interface nsIDOMSVGGradientElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGGRADIENTELEMENT_IID = "{988b2de4-137b-4bb8-a15e-fe94038e9cf3}";
    public static final int SVG_SPREADMETHOD_UNKNOWN = 0;
    public static final int SVG_SPREADMETHOD_PAD = 1;
    public static final int SVG_SPREADMETHOD_REFLECT = 2;
    public static final int SVG_SPREADMETHOD_REPEAT = 3;
    public static final int SVG_GRUNITS_UNKNOWN = 0;
    public static final int SVG_GRUNITS_OBJECTBOUNDINGBOX = 1;
    public static final int SVG_GRUNITS_USERSPACEONUSE = 2;

    public nsIDOMSVGAnimatedEnumeration getGradientUnits();

    public nsIDOMSVGAnimatedTransformList getGradientTransform();

    public nsIDOMSVGAnimatedEnumeration getSpreadMethod();
}

