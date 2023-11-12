/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGZoomAndPan
extends nsISupports {
    public static final String NS_IDOMSVGZOOMANDPAN_IID = "{18967370-921a-4245-8158-a279b190abca}";
    public static final int SVG_ZOOMANDPAN_UNKNOWN = 0;
    public static final int SVG_ZOOMANDPAN_DISABLE = 1;
    public static final int SVG_ZOOMANDPAN_MAGNIFY = 2;

    public int getZoomAndPan();

    public void setZoomAndPan(int var1);
}

