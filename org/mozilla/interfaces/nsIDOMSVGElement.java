/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMSVGSVGElement;

public interface nsIDOMSVGElement
extends nsIDOMElement {
    public static final String NS_IDOMSVGELEMENT_IID = "{e0be7cbb-81c1-4663-8f95-109d96a60b6b}";

    public String getId();

    public void setId(String var1);

    public nsIDOMSVGSVGElement getOwnerSVGElement();

    public nsIDOMSVGElement getViewportElement();
}

