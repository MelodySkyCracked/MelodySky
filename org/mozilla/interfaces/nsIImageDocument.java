/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.imgIRequest;
import org.mozilla.interfaces.nsISupports;

public interface nsIImageDocument
extends nsISupports {
    public static final String NS_IIMAGEDOCUMENT_IID = "{7b80eebc-c98e-4461-8bdb-6e3b6e828890}";

    public boolean getImageResizingEnabled();

    public boolean getImageIsOverflowing();

    public boolean getImageIsResized();

    public imgIRequest getImageRequest();

    public void shrinkToFit();

    public void restoreImage();

    public void restoreImageTo(int var1, int var2);

    public void toggleImageSize();
}

