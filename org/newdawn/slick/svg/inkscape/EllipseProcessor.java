/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import org.newdawn.slick.geom.Ellipse;
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

public class EllipseProcessor
implements ElementProcessor {
    @Override
    public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
        Transform transform2 = Util.getTransform(element);
        transform2 = new Transform(transform, transform2);
        float f = Util.getFloatAttribute(element, "cx");
        float f2 = Util.getFloatAttribute(element, "cy");
        float f3 = Util.getFloatAttribute(element, "rx");
        float f4 = Util.getFloatAttribute(element, "ry");
        Ellipse ellipse = new Ellipse(f, f2, f3, f4);
        Shape shape = ellipse.transform(transform2);
        NonGeometricData nonGeometricData = Util.getNonGeometricData(element);
        nonGeometricData.addAttribute("cx", "" + f);
        nonGeometricData.addAttribute("cy", "" + f2);
        nonGeometricData.addAttribute("rx", "" + f3);
        nonGeometricData.addAttribute("ry", "" + f4);
        diagram.addFigure(new Figure(1, shape, nonGeometricData, transform2));
    }

    @Override
    public boolean handles(Element element) {
        if (element.getNodeName().equals("ellipse")) {
            return true;
        }
        return element.getNodeName().equals("path") && "arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type"));
    }
}

