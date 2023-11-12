/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedTransformList;
import org.mozilla.interfaces.nsIDOMSVGLocatable;

public interface nsIDOMSVGTransformable
extends nsIDOMSVGLocatable {
    public static final String NS_IDOMSVGTRANSFORMABLE_IID = "{b81f6e37-1842-4534-a546-1ab86e59a3c6}";

    public nsIDOMSVGAnimatedTransformList getTransform();
}

