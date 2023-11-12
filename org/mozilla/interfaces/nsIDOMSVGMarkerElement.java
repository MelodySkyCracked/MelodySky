/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAngle;
import org.mozilla.interfaces.nsIDOMSVGAnimatedAngle;
import org.mozilla.interfaces.nsIDOMSVGAnimatedEnumeration;
import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGElement;

public interface nsIDOMSVGMarkerElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGMARKERELEMENT_IID = "{7d89ceb8-f985-4095-8f24-421910704e5e}";
    public static final int SVG_MARKERUNITS_UNKNOWN = 0;
    public static final int SVG_MARKERUNITS_USERSPACEONUSE = 1;
    public static final int SVG_MARKERUNITS_STROKEWIDTH = 2;
    public static final int SVG_MARKER_ORIENT_UNKNOWN = 0;
    public static final int SVG_MARKER_ORIENT_AUTO = 1;
    public static final int SVG_MARKER_ORIENT_ANGLE = 2;

    public nsIDOMSVGAnimatedLength getRefX();

    public nsIDOMSVGAnimatedLength getRefY();

    public nsIDOMSVGAnimatedEnumeration getMarkerUnits();

    public nsIDOMSVGAnimatedLength getMarkerWidth();

    public nsIDOMSVGAnimatedLength getMarkerHeight();

    public nsIDOMSVGAnimatedEnumeration getOrientType();

    public nsIDOMSVGAnimatedAngle getOrientAngle();

    public void setOrientToAuto();

    public void setOrientToAngle(nsIDOMSVGAngle var1);
}

