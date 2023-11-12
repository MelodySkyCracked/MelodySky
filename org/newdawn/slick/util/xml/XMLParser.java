/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.xml;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.util.xml.SlickXMLException;
import org.newdawn.slick.util.xml.XMLElement;
import org.w3c.dom.Document;

public class XMLParser {
    private static DocumentBuilderFactory factory;

    public XMLElement parse(String string) throws SlickException {
        return this.parse(string, ResourceLoader.getResourceAsStream(string));
    }

    public XMLElement parse(String string, InputStream inputStream) throws SlickXMLException {
        try {
            if (factory == null) {
                factory = DocumentBuilderFactory.newInstance();
            }
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            return new XMLElement(document.getDocumentElement());
        }
        catch (Exception exception) {
            throw new SlickXMLException("Failed to parse document: " + string, exception);
        }
    }
}

