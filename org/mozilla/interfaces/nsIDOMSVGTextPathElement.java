/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMSVGAnimatedEnumeration;
import org.mozilla.interfaces.nsIDOMSVGAnimatedLength;
import org.mozilla.interfaces.nsIDOMSVGTextContentElement;

public interface nsIDOMSVGTextPathElement
extends nsIDOMSVGTextContentElement {
    public static final String NS_IDOMSVGTEXTPATHELEMENT_IID = "{5c29a76c-3489-48fe-b9ea-ea0f5b196dff}";
    public static final int TEXTPATH_METHODTYPE_UNKNOWN = 0;
    public static final int TEXTPATH_METHODTYPE_ALIGN = 1;
    public static final int TEXTPATH_METHODTYPE_STRETCH = 2;
    public static final int TEXTPATH_SPACINGTYPE_UNKNOWN = 0;
    public static final int TEXTPATH_SPACINGTYPE_AUTO = 1;
    public static final int TEXTPATH_SPACINGTYPE_EXACT = 2;

    public nsIDOMSVGAnimatedLength getStartOffset();

    public nsIDOMSVGAnimatedEnumeration getMethod();

    public nsIDOMSVGAnimatedEnumeration getSpacing();
}

