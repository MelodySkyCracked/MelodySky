/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.Loader;
import org.newdawn.slick.svg.ParsingException;
import org.newdawn.slick.svg.inkscape.ElementProcessor;
import org.newdawn.slick.svg.inkscape.Util;
import org.w3c.dom.Element;

public class GroupProcessor
implements ElementProcessor {
    @Override
    public boolean handles(Element element) {
        return element.getNodeName().equals("g");
    }

    @Override
    public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
        Transform transform2 = Util.getTransform(element);
        transform2 = new Transform(transform, transform2);
        loader.loadChildren(element, transform2);
    }
}

