/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import java.util.StringTokenizer;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Polygon;
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

public class LineProcessor
implements ElementProcessor {
    private static int processPoly(Polygon polygon, Element element, StringTokenizer stringTokenizer) throws ParsingException {
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String string = stringTokenizer.nextToken();
            if (string.equals("L")) continue;
            if (string.equals("z")) break;
            if (string.equals("M")) continue;
            if (string.equals("C")) {
                return 0;
            }
            String string2 = string;
            String string3 = stringTokenizer.nextToken();
            try {
                float f = Float.parseFloat(string2);
                float f2 = Float.parseFloat(string3);
                polygon.addPoint(f, f2);
                ++n;
            }
            catch (NumberFormatException numberFormatException) {
                throw new ParsingException(element.getAttribute("id"), "Invalid token in points list", (Throwable)numberFormatException);
            }
        }
        return n;
    }

    @Override
    public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
        Object object;
        Object object2;
        Shape shape;
        float f;
        float f2;
        float f3;
        float f4;
        Transform transform2 = Util.getTransform(element);
        transform2 = new Transform(transform, transform2);
        if (element.getNodeName().equals("line")) {
            f4 = Float.parseFloat(element.getAttribute("x1"));
            f3 = Float.parseFloat(element.getAttribute("x2"));
            f2 = Float.parseFloat(element.getAttribute("y1"));
            f = Float.parseFloat(element.getAttribute("y2"));
        } else {
            shape = new Polygon();
            object2 = element.getAttribute("d");
            object = new StringTokenizer((String)object2, ", ");
            if (LineProcessor.processPoly(shape, element, (StringTokenizer)object) == 2) {
                f4 = shape.getPoint(0)[0];
                f2 = shape.getPoint(0)[1];
                f3 = shape.getPoint(1)[0];
                f = shape.getPoint(1)[1];
            } else {
                return;
            }
        }
        object2 = new float[]{f4, f2, f3, f};
        object = new float[4];
        transform2.transform((float[])object2, 0, (float[])object, 0, 2);
        shape = new Line((float)object[0], (float)object[1], (float)object[2], (float)object[3]);
        NonGeometricData nonGeometricData = Util.getNonGeometricData(element);
        nonGeometricData.addAttribute("x1", "" + f4);
        nonGeometricData.addAttribute("x2", "" + f3);
        nonGeometricData.addAttribute("y1", "" + f2);
        nonGeometricData.addAttribute("y2", "" + f);
        diagram.addFigure(new Figure(2, shape, nonGeometricData, transform2));
    }

    @Override
    public boolean handles(Element element) {
        if (element.getNodeName().equals("line")) {
            return true;
        }
        return element.getNodeName().equals("path") && !"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type"));
    }
}

