/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg.inkscape;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.Gradient;
import org.newdawn.slick.svg.Loader;
import org.newdawn.slick.svg.ParsingException;
import org.newdawn.slick.svg.inkscape.ElementProcessor;
import org.newdawn.slick.svg.inkscape.Util;
import org.newdawn.slick.util.Log;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DefsProcessor
implements ElementProcessor {
    @Override
    public boolean handles(Element element) {
        return element.getNodeName().equals("defs");
    }

    @Override
    public void process(Loader loader, Element element, Diagram diagram, Transform transform) throws ParsingException {
        int n;
        String string;
        Object object;
        Object object2;
        Object object3;
        Object object4;
        Element element2;
        Object object5;
        NodeList nodeList = element.getElementsByTagName("pattern");
        for (int i = 0; i < nodeList.getLength(); ++i) {
            object5 = (Element)nodeList.item(i);
            NodeList nodeList2 = object5.getElementsByTagName("image");
            if (nodeList2.getLength() == 0) {
                Log.warn("Pattern 1981 does not specify an image. Only image patterns are supported.");
                continue;
            }
            element2 = (Element)nodeList2.item(0);
            object4 = object5.getAttribute("id");
            object3 = element2.getAttributeNS("http://www.w3.org/1999/xlink", "href");
            diagram.addPatternDef((String)object4, (String)object3);
        }
        NodeList nodeList3 = element.getElementsByTagName("linearGradient");
        object5 = new ArrayList();
        for (int i = 0; i < nodeList3.getLength(); ++i) {
            element2 = (Element)nodeList3.item(i);
            object4 = element2.getAttribute("id");
            object3 = new Gradient((String)object4, false);
            ((Gradient)object3).setTransform(Util.getTransform(element2, "gradientTransform"));
            if (this.stringLength(element2.getAttribute("x1")) > 0) {
                ((Gradient)object3).setX1(Float.parseFloat(element2.getAttribute("x1")));
            }
            if (this.stringLength(element2.getAttribute("x2")) > 0) {
                ((Gradient)object3).setX2(Float.parseFloat(element2.getAttribute("x2")));
            }
            if (this.stringLength(element2.getAttribute("y1")) > 0) {
                ((Gradient)object3).setY1(Float.parseFloat(element2.getAttribute("y1")));
            }
            if (this.stringLength(element2.getAttribute("y2")) > 0) {
                ((Gradient)object3).setY2(Float.parseFloat(element2.getAttribute("y2")));
            }
            if (this.stringLength((String)(object2 = element2.getAttributeNS("http://www.w3.org/1999/xlink", "href"))) > 0) {
                ((Gradient)object3).reference(((String)object2).substring(1));
                ((ArrayList)object5).add(object3);
            } else {
                object = element2.getElementsByTagName("stop");
                for (int j = 0; j < object.getLength(); ++j) {
                    Element element3 = (Element)object.item(j);
                    float f = Float.parseFloat(element3.getAttribute("offset"));
                    String string2 = Util.extractStyle(element3.getAttribute("style"), "stop-color");
                    string = Util.extractStyle(element3.getAttribute("style"), "stop-opacity");
                    int n2 = Integer.parseInt(string2.substring(1), 16);
                    Color color = new Color(n2);
                    color.a = Float.parseFloat(string);
                    ((Gradient)object3).addStep(f, color);
                }
                ((Gradient)object3).getImage();
            }
            diagram.addGradient((String)object4, (Gradient)object3);
        }
        NodeList nodeList4 = element.getElementsByTagName("radialGradient");
        for (n = 0; n < nodeList4.getLength(); ++n) {
            object4 = (Element)nodeList4.item(n);
            object3 = object4.getAttribute("id");
            object2 = new Gradient((String)object3, true);
            ((Gradient)object2).setTransform(Util.getTransform((Element)object4, "gradientTransform"));
            if (this.stringLength(object4.getAttribute("cx")) > 0) {
                ((Gradient)object2).setX1(Float.parseFloat(object4.getAttribute("cx")));
            }
            if (this.stringLength(object4.getAttribute("cy")) > 0) {
                ((Gradient)object2).setY1(Float.parseFloat(object4.getAttribute("cy")));
            }
            if (this.stringLength(object4.getAttribute("fx")) > 0) {
                ((Gradient)object2).setX2(Float.parseFloat(object4.getAttribute("fx")));
            }
            if (this.stringLength(object4.getAttribute("fy")) > 0) {
                ((Gradient)object2).setY2(Float.parseFloat(object4.getAttribute("fy")));
            }
            if (this.stringLength(object4.getAttribute("r")) > 0) {
                ((Gradient)object2).setR(Float.parseFloat(object4.getAttribute("r")));
            }
            if (this.stringLength((String)(object = object4.getAttributeNS("http://www.w3.org/1999/xlink", "href"))) > 0) {
                ((Gradient)object2).reference(((String)object).substring(1));
                ((ArrayList)object5).add(object2);
            } else {
                NodeList nodeList5 = object4.getElementsByTagName("stop");
                for (int i = 0; i < nodeList5.getLength(); ++i) {
                    Element element4 = (Element)nodeList5.item(i);
                    float f = Float.parseFloat(element4.getAttribute("offset"));
                    string = Util.extractStyle(element4.getAttribute("style"), "stop-color");
                    String string3 = Util.extractStyle(element4.getAttribute("style"), "stop-opacity");
                    int n3 = Integer.parseInt(string.substring(1), 16);
                    Color color = new Color(n3);
                    color.a = Float.parseFloat(string3);
                    ((Gradient)object2).addStep(f, color);
                }
                ((Gradient)object2).getImage();
            }
            diagram.addGradient((String)object3, (Gradient)object2);
        }
        for (n = 0; n < ((ArrayList)object5).size(); ++n) {
            ((Gradient)((ArrayList)object5).get(n)).resolve(diagram);
            ((Gradient)((ArrayList)object5).get(n)).getImage();
        }
    }

    private int stringLength(String string) {
        if (string == null) {
            return 0;
        }
        return string.length();
    }
}

