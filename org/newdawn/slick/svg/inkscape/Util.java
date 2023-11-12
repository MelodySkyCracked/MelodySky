/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import java.util.StringTokenizer;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.NonGeometricData;
import org.newdawn.slick.svg.ParsingException;
import org.newdawn.slick.svg.inkscape.InkscapeNonGeometricData;
import org.w3c.dom.Element;

public class Util {
    public static final String INKSCAPE = "http://www.inkscape.org/namespaces/inkscape";
    public static final String SODIPODI = "http://sodipodi.sourceforge.net/DTD/sodipodi-0.dtd";
    public static final String XLINK = "http://www.w3.org/1999/xlink";

    static NonGeometricData getNonGeometricData(Element element) {
        String string = Util.getMetaData(element);
        InkscapeNonGeometricData inkscapeNonGeometricData = new InkscapeNonGeometricData(string, element);
        inkscapeNonGeometricData.addAttribute("id", element.getAttribute("id"));
        inkscapeNonGeometricData.addAttribute("fill", Util.getStyle(element, "fill"));
        inkscapeNonGeometricData.addAttribute("stroke", Util.getStyle(element, "stroke"));
        inkscapeNonGeometricData.addAttribute("opacity", Util.getStyle(element, "opacity"));
        inkscapeNonGeometricData.addAttribute("stroke-dasharray", Util.getStyle(element, "stroke-dasharray"));
        inkscapeNonGeometricData.addAttribute("stroke-dashoffset", Util.getStyle(element, "stroke-dashoffset"));
        inkscapeNonGeometricData.addAttribute("stroke-miterlimit", Util.getStyle(element, "stroke-miterlimit"));
        inkscapeNonGeometricData.addAttribute("stroke-opacity", Util.getStyle(element, "stroke-opacity"));
        inkscapeNonGeometricData.addAttribute("stroke-width", Util.getStyle(element, "stroke-width"));
        return inkscapeNonGeometricData;
    }

    static String getMetaData(Element element) {
        String string = element.getAttributeNS(INKSCAPE, "label");
        if (string != null && !string.equals("")) {
            return string;
        }
        return element.getAttribute("id");
    }

    static String getStyle(Element element, String string) {
        String string2 = element.getAttribute(string);
        if (string2 != null && string2.length() > 0) {
            return string2;
        }
        String string3 = element.getAttribute("style");
        return Util.extractStyle(string3, string);
    }

    static String extractStyle(String string, String string2) {
        if (string == null) {
            return "";
        }
        StringTokenizer stringTokenizer = new StringTokenizer(string, ";");
        while (stringTokenizer.hasMoreTokens()) {
            String string3 = stringTokenizer.nextToken();
            String string4 = string3.substring(0, string3.indexOf(58));
            if (!string4.equals(string2)) continue;
            return string3.substring(string3.indexOf(58) + 1);
        }
        return "";
    }

    static Transform getTransform(Element element) {
        return Util.getTransform(element, "transform");
    }

    static Transform getTransform(Element element, String string) {
        String string2 = element.getAttribute(string);
        if (string2 == null) {
            return new Transform();
        }
        if (string2.equals("")) {
            return new Transform();
        }
        if (string2.startsWith("translate")) {
            string2 = string2.substring(0, string2.length() - 1);
            string2 = string2.substring(10);
            StringTokenizer stringTokenizer = new StringTokenizer(string2, ", ");
            float f = Float.parseFloat(stringTokenizer.nextToken());
            float f2 = Float.parseFloat(stringTokenizer.nextToken());
            return Transform.createTranslateTransform(f, f2);
        }
        if (string2.startsWith("matrix")) {
            float[] fArray = new float[6];
            string2 = string2.substring(0, string2.length() - 1);
            string2 = string2.substring(7);
            StringTokenizer stringTokenizer = new StringTokenizer(string2, ", ");
            float[] fArray2 = new float[6];
            for (int i = 0; i < fArray2.length; ++i) {
                fArray2[i] = Float.parseFloat(stringTokenizer.nextToken());
            }
            fArray[0] = fArray2[0];
            fArray[1] = fArray2[2];
            fArray[2] = fArray2[4];
            fArray[3] = fArray2[1];
            fArray[4] = fArray2[3];
            fArray[5] = fArray2[5];
            return new Transform(fArray);
        }
        return new Transform();
    }

    static float getFloatAttribute(Element element, String string) throws ParsingException {
        String string2 = element.getAttribute(string);
        if (string2 == null || string2.equals("")) {
            string2 = element.getAttributeNS(SODIPODI, string);
        }
        try {
            return Float.parseFloat(string2);
        }
        catch (NumberFormatException numberFormatException) {
            throw new ParsingException(element, "Invalid value for: " + string, (Throwable)numberFormatException);
        }
    }

    public static String getAsReference(String string) {
        if (string.length() < 2) {
            return "";
        }
        string = string.substring(1, string.length());
        return string;
    }
}

