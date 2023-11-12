/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAngle;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedAngle
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDANGLE_IID = "{c6ab8b9e-32db-464a-ae33-8691d44bc60a}";

    public nsIDOMSVGAngle getBaseVal();

    public nsIDOMSVGAngle getAnimVal();
}

