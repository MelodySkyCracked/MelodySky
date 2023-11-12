/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import java.util.ArrayList;
import java.util.StringTokenizer;
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

public class PolygonProcessor
implements ElementProcessor {
    private static int processPoly(Polygon polygon, Element element, StringTokenizer stringTokenizer) throws ParsingException {
        int n = 0;
        ArrayList arrayList = new ArrayList();
        boolean bl = false;
        boolean bl2 = false;
        while (stringTokenizer.hasMoreTokens()) {
            String string = stringTokenizer.nextToken();
            if (string.equals("L")) continue;
            if (string.equals("z")) {
                bl2 = true;
                break;
            }
            if (string.equals("M")) {
                if (!bl) {
                    bl = true;
                    continue;
                }
                return 0;
            }
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
        polygon.setClosed(bl2);
        return n;
    }

    @Override
    public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
        Transform transform2 = Util.getTransform(element);
        transform2 = new Transform(transform, transform2);
        String string = element.getAttribute("points");
        if (element.getNodeName().equals("path")) {
            string = element.getAttribute("d");
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string, ", ");
        Polygon polygon = new Polygon();
        int n = PolygonProcessor.processPoly(polygon, element, stringTokenizer);
        NonGeometricData nonGeometricData = Util.getNonGeometricData(element);
        if (n > 3) {
            Shape shape = polygon.transform(transform2);
            diagram.addFigure(new Figure(5, shape, nonGeometricData, transform2));
        }
    }

    @Override
    public boolean handles(Element element) {
        if (element.getNodeName().equals("polygon")) {
            return true;
        }
        return element.getNodeName().equals("path") && !"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type"));
    }
}

