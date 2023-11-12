/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

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

public class UseProcessor
implements ElementProcessor {
    @Override
    public boolean handles(Element element) {
        return element.getNodeName().equals("use");
    }

    @Override
    public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
        String string = element.getAttributeNS("http://www.w3.org/1999/xlink", "href");
        String string2 = Util.getAsReference(string);
        Figure figure = diagram.getFigureByID(string2);
        if (figure == null) {
            throw new ParsingException(element, "Unable to locate referenced element: " + string2);
        }
        Transform transform2 = Util.getTransform(element);
        Transform transform3 = transform2.concatenate(figure.getTransform());
        NonGeometricData nonGeometricData = Util.getNonGeometricData(element);
        Shape shape = figure.getShape().transform(transform3);
        nonGeometricData.addAttribute("fill", figure.getData().getAttribute("fill"));
        nonGeometricData.addAttribute("stroke", figure.getData().getAttribute("stroke"));
        nonGeometricData.addAttribute("opacity", figure.getData().getAttribute("opacity"));
        nonGeometricData.addAttribute("stroke-width", figure.getData().getAttribute("stroke-width"));
        Figure figure2 = new Figure(figure.getType(), shape, nonGeometricData, transform3);
        diagram.addFigure(figure2);
    }
}

