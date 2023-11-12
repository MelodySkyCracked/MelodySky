/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.Figure;
import org.newdawn.slick.svg.Loader;
import org.newdawn.slick.svg.NonGeometricData;
import org.newdawn.slick.svg.ParsingException;
import org.newdawn.slick.svg.inkscape.ElementProcessor;
import org.newdawn.slick.svg.inkscape.Util;
import org.w3c.dom.Element;

public class RectProcessor
implements ElementProcessor {
    @Override
    public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
        Transform transform2 = Util.getTransform(element);
        transform2 = new Transform(transform, transform2);
        float f = Float.parseFloat(element.getAttribute("width"));
        float f2 = Float.parseFloat(element.getAttribute("height"));
        float f3 = Float.parseFloat(element.getAttribute("x"));
        float f4 = Float.parseFloat(element.getAttribute("y"));
        Rectangle rectangle = new Rectangle(f3, f4, f + 1.0f, f2 + 1.0f);
        Shape shape = rectangle.transform(transform2);
        NonGeometricData nonGeometricData = Util.getNonGeometricData(element);
        nonGeometricData.addAttribute("width", "" + f);
        nonGeometricData.addAttribute("height", "" + f2);
        nonGeometricData.addAttribute("x", "" + f3);
        nonGeometricData.addAttribute("y", "" + f4);
        diagram.addFigure(new Figure(3, shape, nonGeometricData, transform2));
    }

    @Override
    public boolean handles(Element element) {
        return element.getNodeName().equals("rect");
    }
}

