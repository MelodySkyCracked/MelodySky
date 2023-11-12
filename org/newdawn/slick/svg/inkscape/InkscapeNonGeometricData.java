/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import org.newdawn.slick.svg.NonGeometricData;
import org.w3c.dom.Element;

public class InkscapeNonGeometricData
extends NonGeometricData {
    private Element element;

    public InkscapeNonGeometricData(String string, Element element) {
        super(string);
        this.element = element;
    }

    @Override
    public String getAttribute(String string) {
        String string2 = super.getAttribute(string);
        if (string2 == null) {
            string2 = this.element.getAttribute(string);
        }
        return string2;
    }

    public Element getElement() {
        return this.element;
    }
}

