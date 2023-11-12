/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIXTFVisual;
import org.mozilla.interfaces.nsIXTFXULVisualWrapper;

public interface nsIXTFXULVisual
extends nsIXTFVisual {
    public static final String NS_IXTFXULVISUAL_IID = "{a1173d91-4428-4829-8e3e-fe66e558f161}";

    public void onCreated(nsIXTFXULVisualWrapper var1);
}

