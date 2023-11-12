/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import java.util.ArrayList;
import java.util.StringTokenizer;
import org.newdawn.slick.geom.Path;
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

public class PathProcessor
implements ElementProcessor {
    private static Path processPoly(Element element, StringTokenizer stringTokenizer) throws ParsingException {
        boolean bl = false;
        ArrayList arrayList = new ArrayList();
        boolean bl2 = false;
        boolean bl3 = false;
        Path path = null;
        while (stringTokenizer.hasMoreTokens()) {
            try {
                float f;
                float f2;
                String string = stringTokenizer.nextToken();
                if (string.equals("L")) {
                    f2 = Float.parseFloat(stringTokenizer.nextToken());
                    f = Float.parseFloat(stringTokenizer.nextToken());
                    path.lineTo(f2, f);
                    continue;
                }
                if (string.equals("z")) {
                    path.close();
                    continue;
                }
                if (string.equals("M")) {
                    if (!bl2) {
                        bl2 = true;
                        f2 = Float.parseFloat(stringTokenizer.nextToken());
                        f = Float.parseFloat(stringTokenizer.nextToken());
                        path = new Path(f2, f);
                        continue;
                    }
                    bl3 = true;
                    f2 = Float.parseFloat(stringTokenizer.nextToken());
                    f = Float.parseFloat(stringTokenizer.nextToken());
                    path.startHole(f2, f);
                    continue;
                }
                if (!string.equals("C")) continue;
                bl3 = true;
                f2 = Float.parseFloat(stringTokenizer.nextToken());
                f = Float.parseFloat(stringTokenizer.nextToken());
                float f3 = Float.parseFloat(stringTokenizer.nextToken());
                float f4 = Float.parseFloat(stringTokenizer.nextToken());
                float f5 = Float.parseFloat(stringTokenizer.nextToken());
                float f6 = Float.parseFloat(stringTokenizer.nextToken());
                path.curveTo(f5, f6, f2, f, f3, f4);
            }
            catch (NumberFormatException numberFormatException) {
                throw new ParsingException(element.getAttribute("id"), "Invalid token in points list", (Throwable)numberFormatException);
            }
        }
        if (!bl3) {
            return null;
        }
        return path;
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
        Path path = PathProcessor.processPoly(element, stringTokenizer);
        NonGeometricData nonGeometricData = Util.getNonGeometricData(element);
        if (path != null) {
            Shape shape = path.transform(transform2);
            diagram.addFigure(new Figure(4, shape, nonGeometricData, transform2));
        }
    }

    @Override
    public boolean handles(Element element) {
        return element.getNodeName().equals("path") && !"arc".equals(element.getAttributeNS("http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd", "type"));
    }
}

