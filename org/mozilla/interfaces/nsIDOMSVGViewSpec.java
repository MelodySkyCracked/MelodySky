/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGElement;
import org.mozilla.interfaces.nsIDOMSVGTransformList;
import org.mozilla.interfaces.nsIDOMSVGZoomAndPan;

public interface nsIDOMSVGViewSpec
extends nsIDOMSVGZoomAndPan {
    public static final String NS_IDOMSVGVIEWSPEC_IID = "{ede34b03-57b6-45bf-a259-3550b5697286}";

    public nsIDOMSVGTransformList getTransform();

    public nsIDOMSVGElement getViewTarget();

    public String getViewBoxString();

    public String getPreserveAspectRatioString();

    public String getTransformString();

    public String getViewTargetString();
}

