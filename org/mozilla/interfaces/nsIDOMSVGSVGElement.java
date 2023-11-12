/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsIDOMSVGAngle;
import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGElement;
import org.mozilla.interfaces.nsIDOMSVGLength;
import org.mozilla.interfaces.nsIDOMSVGMatrix;
import org.mozilla.interfaces.nsIDOMSVGNumber;
import org.mozilla.interfaces.nsIDOMSVGPoint;
import org.mozilla.interfaces.nsIDOMSVGRect;
import org.mozilla.interfaces.nsIDOMSVGTransform;
import org.mozilla.interfaces.nsIDOMSVGViewSpec;

public interface nsIDOMSVGSVGElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGSVGELEMENT_IID = "{67b8f41e-3577-4c8a-b1de-bef51186fe08}";

    public nsIDOMSVGAnimatedLength getX();

    public nsIDOMSVGAnimatedLength getY();

    public nsIDOMSVGAnimatedLength getWidth();

    public nsIDOMSVGAnimatedLength getHeight();

    public String getContentScriptType();

    public void setContentScriptType(String var1);

    public String getContentStyleType();

    public void setContentStyleType(String var1);

    public nsIDOMSVGRect getViewport();

    public float getPixelUnitToMillimeterX();

    public float getPixelUnitToMillimeterY();

    public float getScreenPixelToMillimeterX();

    public float getScreenPixelToMillimeterY();

    public boolean getUseCurrentView();

    public void setUseCurrentView(boolean var1);

    public nsIDOMSVGViewSpec getCurrentView();

    public float getCurrentScale();

    public void setCurrentScale(float var1);

    public nsIDOMSVGPoint getCurrentTranslate();

    public long suspendRedraw(long var1);

    public void unsuspendRedraw(long var1);

    public void unsuspendRedrawAll();

    public void forceRedraw();

    public void pauseAnimations();

    public void unpauseAnimations();

    public boolean animationsPaused();

    public float getCurrentTime();

    public void setCurrentTime(float var1);

    public nsIDOMNodeList getIntersectionList(nsIDOMSVGRect var1, nsIDOMSVGElement var2);

    public nsIDOMNodeList getEnclosureList(nsIDOMSVGRect var1, nsIDOMSVGElement var2);

    public boolean checkIntersection(nsIDOMSVGElement var1, nsIDOMSVGRect var2);

    public boolean checkEnclosure(nsIDOMSVGElement var1, nsIDOMSVGRect var2);

    public void deSelectAll();

    public nsIDOMSVGNumber createSVGNumber();

    public nsIDOMSVGLength createSVGLength();

    public nsIDOMSVGAngle createSVGAngle();

    public nsIDOMSVGPoint createSVGPoint();

    public nsIDOMSVGMatrix createSVGMatrix();

    public nsIDOMSVGRect createSVGRect();

    public nsIDOMSVGTransform createSVGTransform();

    public nsIDOMSVGTransform createSVGTransformFromMatrix(nsIDOMSVGMatrix var1);

    public String createSVGString();

    public nsIDOMElement getElementById(String var1);

    public nsIDOMSVGMatrix getViewboxToViewportTransform();
}

