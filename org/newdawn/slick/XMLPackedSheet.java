/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick;

import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLPackedSheet {
    private Image image;
    private HashMap sprites = new HashMap();

    public XMLPackedSheet(String string, String string2) throws SlickException {
        this.image = new Image(string, false, 2);
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(ResourceLoader.getResourceAsStream(string2));
            NodeList nodeList = document.getElementsByTagName("sprite");
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element = (Element)nodeList.item(i);
                String string3 = element.getAttribute("name");
                int n = Integer.parseInt(element.getAttribute("x"));
                int n2 = Integer.parseInt(element.getAttribute("y"));
                int n3 = Integer.parseInt(element.getAttribute("width"));
                int n4 = Integer.parseInt(element.getAttribute("height"));
                this.sprites.put(string3, this.image.getSubImage(n, n2, n3, n4));
            }
        }
        catch (Exception exception) {
            throw new SlickException("Failed to parse sprite sheet XML", exception);
        }
    }

    public Image getSprite(String string) {
        return (Image)this.sprites.get(string);
    }
}

