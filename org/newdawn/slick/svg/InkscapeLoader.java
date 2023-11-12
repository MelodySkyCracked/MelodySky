/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.svg.Diagram;
import org.newdawn.slick.svg.I;
import org.newdawn.slick.svg.Loader;
import org.newdawn.slick.svg.ParsingException;
import org.newdawn.slick.svg.inkscape.DefsProcessor;
import org.newdawn.slick.svg.inkscape.ElementProcessor;
import org.newdawn.slick.svg.inkscape.EllipseProcessor;
import org.newdawn.slick.svg.inkscape.GroupProcessor;
import org.newdawn.slick.svg.inkscape.LineProcessor;
import org.newdawn.slick.svg.inkscape.PathProcessor;
import org.newdawn.slick.svg.inkscape.PolygonProcessor;
import org.newdawn.slick.svg.inkscape.RectProcessor;
import org.newdawn.slick.svg.inkscape.UseProcessor;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class InkscapeLoader
implements Loader {
    public static int RADIAL_TRIANGULATION_LEVEL = 1;
    private static ArrayList processors = new ArrayList();
    private Diagram diagram;

    public static void addElementProcessor(ElementProcessor elementProcessor) {
        processors.add(elementProcessor);
    }

    public static Diagram load(String string, boolean bl) throws SlickException {
        return InkscapeLoader.load(ResourceLoader.getResourceAsStream(string), bl);
    }

    public static Diagram load(String string) throws SlickException {
        return InkscapeLoader.load(ResourceLoader.getResourceAsStream(string), false);
    }

    public static Diagram load(InputStream inputStream, boolean bl) throws SlickException {
        return new InkscapeLoader().loadDiagram(inputStream, bl);
    }

    private InkscapeLoader() {
    }

    private Diagram loadDiagram(InputStream inputStream) throws SlickException {
        return this.loadDiagram(inputStream, false);
    }

    private Diagram loadDiagram(InputStream inputStream, boolean bl) throws SlickException {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            documentBuilderFactory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            documentBuilder.setEntityResolver(new I(this));
            Document document = documentBuilder.parse(inputStream);
            Element element = document.getDocumentElement();
            String string = element.getAttribute("width");
            while (Character.isLetter(string.charAt(string.length() - 1))) {
                string = string.substring(0, string.length() - 1);
            }
            String string2 = element.getAttribute("height");
            while (Character.isLetter(string2.charAt(string2.length() - 1))) {
                string2 = string2.substring(0, string2.length() - 1);
            }
            float f = Float.parseFloat(string);
            float f2 = Float.parseFloat(string2);
            this.diagram = new Diagram(f, f2);
            if (!bl) {
                f2 = 0.0f;
            }
            this.loadChildren(element, Transform.createTranslateTransform(0.0f, -f2));
            return this.diagram;
        }
        catch (Exception exception) {
            throw new SlickException("Failed to load inkscape document", exception);
        }
    }

    @Override
    public void loadChildren(Element element, Transform transform) throws ParsingException {
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            if (!(nodeList.item(i) instanceof Element)) continue;
            this.loadElement((Element)nodeList.item(i), transform);
        }
    }

    private void loadElement(Element element, Transform transform) throws ParsingException {
        for (int i = 0; i < processors.size(); ++i) {
            ElementProcessor elementProcessor = (ElementProcessor)processors.get(i);
            if (!elementProcessor.handles(element)) continue;
            elementProcessor.process(this, element, this.diagram, transform);
        }
    }

    static {
        InkscapeLoader.addElementProcessor(new RectProcessor());
        InkscapeLoader.addElementProcessor(new EllipseProcessor());
        InkscapeLoader.addElementProcessor(new PolygonProcessor());
        InkscapeLoader.addElementProcessor(new PathProcessor());
        InkscapeLoader.addElementProcessor(new LineProcessor());
        InkscapeLoader.addElementProcessor(new GroupProcessor());
        InkscapeLoader.addElementProcessor(new DefsProcessor());
        InkscapeLoader.addElementProcessor(new UseProcessor());
    }
}

