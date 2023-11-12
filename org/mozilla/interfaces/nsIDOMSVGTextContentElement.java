/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedEnumeration;
import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGElement;
import org.mozilla.interfaces.nsIDOMSVGPoint;
import org.mozilla.interfaces.nsIDOMSVGRect;

public interface nsIDOMSVGTextContentElement
extends nsIDOMSVGElement {
    public static final String NS_IDOMSVGTEXTCONTENTELEMENT_IID = "{87ad94bc-07c9-412b-b2d8-de245a2e84a5}";
    public static final int LENGTHADJUST_UNKNOWN = 0;
    public static final int LENGTHADJUST_SPACING = 1;
    public static final int LENGTHADJUST_SPACINGANDGLYPHS = 2;

    public nsIDOMSVGAnimatedLength getTextLength();

    public nsIDOMSVGAnimatedEnumeration getLengthAdjust();

    public int getNumberOfChars();

    public float getComputedTextLength();

    public float getSubStringLength(long var1, long var3);

    public nsIDOMSVGPoint getStartPositionOfChar(long var1);

    public nsIDOMSVGPoint getEndPositionOfChar(long var1);

    public nsIDOMSVGRect getExtentOfChar(long var1);

    public float getRotationOfChar(long var1);

    public int getCharNumAtPosition(nsIDOMSVGPoint var1);

    public void selectSubString(long var1, long var3);
}

