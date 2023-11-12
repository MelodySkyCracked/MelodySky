/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGPreserveAspectRatio
extends nsISupports {
    public static final String NS_IDOMSVGPRESERVEASPECTRATIO_IID = "{7ae42f27-4799-4e7c-86c6-e1dae6ad5157}";
    public static final int SVG_PRESERVEASPECTRATIO_UNKNOWN = 0;
    public static final int SVG_PRESERVEASPECTRATIO_NONE = 1;
    public static final int SVG_PRESERVEASPECTRATIO_XMINYMIN = 2;
    public static final int SVG_PRESERVEASPECTRATIO_XMIDYMIN = 3;
    public static final int SVG_PRESERVEASPECTRATIO_XMAXYMIN = 4;
    public static final int SVG_PRESERVEASPECTRATIO_XMINYMID = 5;
    public static final int SVG_PRESERVEASPECTRATIO_XMIDYMID = 6;
    public static final int SVG_PRESERVEASPECTRATIO_XMAXYMID = 7;
    public static final int SVG_PRESERVEASPECTRATIO_XMINYMAX = 8;
    public static final int SVG_PRESERVEASPECTRATIO_XMIDYMAX = 9;
    public static final int SVG_PRESERVEASPECTRATIO_XMAXYMAX = 10;
    public static final int SVG_MEETORSLICE_UNKNOWN = 0;
    public static final int SVG_MEETORSLICE_MEET = 1;
    public static final int SVG_MEETORSLICE_SLICE = 2;

    public int getAlign();

    public void setAlign(int var1);

    public int getMeetOrSlice();

    public void setMeetOrSlice(int var1);
}

