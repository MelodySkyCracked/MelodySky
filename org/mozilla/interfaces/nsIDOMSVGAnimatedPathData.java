/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGPathSegList;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSVGAnimatedPathData
extends nsISupports {
    public static final String NS_IDOMSVGANIMATEDPATHDATA_IID = "{6ef2b400-dbf4-4c12-8787-fe15caac5648}";

    public nsIDOMSVGPathSegList getPathSegList();

    public nsIDOMSVGPathSegList getNormalizedPathSegList();

    public nsIDOMSVGPathSegList getAnimatedPathSegList();

    public nsIDOMSVGPathSegList getAnimatedNormalizedPathSegList();
}

