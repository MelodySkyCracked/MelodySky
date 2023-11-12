/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMScreen
extends nsISupports {
    public static final String NS_IDOMSCREEN_IID = "{77947960-b4af-11d2-bd93-00805f8ae3f4}";

    public int getTop();

    public int getLeft();

    public int getWidth();

    public int getHeight();

    public int getPixelDepth();

    public int getColorDepth();

    public int getAvailWidth();

    public int getAvailHeight();

    public int getAvailLeft();

    public int getAvailTop();
}

