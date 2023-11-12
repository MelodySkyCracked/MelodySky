/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGNumber
extends nsISupports {
    public static final String NS_IDOMSVGNUMBER_IID = "{98575762-a936-4ecf-a226-b74c3a2981b4}";

    public float getValue();

    public void setValue(float var1);
}

