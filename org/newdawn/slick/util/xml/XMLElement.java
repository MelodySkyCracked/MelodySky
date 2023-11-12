/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.xml;

import org.newdawn.slick.util.xml.SlickXMLException;
import org.newdawn.slick.util.xml.XMLElementList;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class XMLElement {
    private Element dom;
    private XMLElementList children;
    private String name;

    XMLElement(Element element) {
        this.dom = element;
        this.name = this.dom.getTagName();
    }

    public String[] getAttributeNames() {
        NamedNodeMap namedNodeMap = this.dom.getAttributes();
        String[] stringArray = new String[namedNodeMap.getLength()];
        for (int i = 0; i < stringArray.length; ++i) {
            stringArray[i] = namedNodeMap.item(i).getNodeName();
        }
        return stringArray;
    }

    public String getName() {
        return this.name;
    }

    public String getAttribute(String string) {
        return this.dom.getAttribute(string);
    }

    public String getAttribute(String string, String string2) {
        String string3 = this.dom.getAttribute(string);
        if (string3 == null || string3.length() == 0) {
            return string2;
        }
        return string3;
    }

    public int getIntAttribute(String string) throws SlickXMLException {
        try {
            return Integer.parseInt(this.getAttribute(string));
        }
        catch (NumberFormatException numberFormatException) {
            throw new SlickXMLException("Value read: '" + this.getAttribute(string) + "' is not an integer", numberFormatException);
        }
    }

    public int getIntAttribute(String string, int n) throws SlickXMLException {
        try {
            return Integer.parseInt(this.getAttribute(string, "" + n));
        }
        catch (NumberFormatException numberFormatException) {
            throw new SlickXMLException("Value read: '" + this.getAttribute(string, "" + n) + "' is not an integer", numberFormatException);
        }
    }

    public double getDoubleAttribute(String string) throws SlickXMLException {
        try {
            return Double.parseDouble(this.getAttribute(string));
        }
        catch (NumberFormatException numberFormatException) {
            throw new SlickXMLException("Value read: '" + this.getAttribute(string) + "' is not a double", numberFormatException);
        }
    }

    public double getDoubleAttribute(String string, double d) throws SlickXMLException {
        try {
            return Double.parseDouble(this.getAttribute(string, "" + d));
        }
        catch (NumberFormatException numberFormatException) {
            throw new SlickXMLException("Value read: '" + this.getAttribute(string, "" + d) + "' is not a double", numberFormatException);
        }
    }

    public boolean getBooleanAttribute(String string) throws SlickXMLException {
        String string2 = this.getAttribute(string);
        if (string2.equalsIgnoreCase("true")) {
            return true;
        }
        if (string2.equalsIgnoreCase("false")) {
            return false;
        }
        throw new SlickXMLException("Value read: '" + this.getAttribute(string) + "' is not a boolean");
    }

    public boolean getBooleanAttribute(String string, boolean bl) throws SlickXMLException {
        String string2 = this.getAttribute(string, "" + bl);
        if (string2.equalsIgnoreCase("true")) {
            return true;
        }
        if (string2.equalsIgnoreCase("false")) {
            return false;
        }
        throw new SlickXMLException("Value read: '" + this.getAttribute(string, "" + bl) + "' is not a boolean");
    }

    public String getContent() {
        String string = "";
        NodeList nodeList = this.dom.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            if (!(nodeList.item(i) instanceof Text)) continue;
            string = string + nodeList.item(i).getNodeValue();
        }
        return string;
    }

    public XMLElementList getChildren() {
        if (this.children != null) {
            return this.children;
        }
        NodeList nodeList = this.dom.getChildNodes();
        this.children = new XMLElementList();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            if (!(nodeList.item(i) instanceof Element)) continue;
            this.children.add(new XMLElement((Element)nodeList.item(i)));
        }
        return this.children;
    }

    public XMLElementList getChildrenByName(String string) {
        XMLElementList xMLElementList = new XMLElementList();
        XMLElementList xMLElementList2 = this.getChildren();
        for (int i = 0; i < xMLElementList2.size(); ++i) {
            if (!xMLElementList2.get(i).getName().equals(string)) continue;
            xMLElementList.add(xMLElementList2.get(i));
        }
        return xMLElementList;
    }

    public String toString() {
        String string = "[XML " + this.getName();
        String[] stringArray = this.getAttributeNames();
        for (int i = 0; i < stringArray.length; ++i) {
            string = string + " " + stringArray[i] + "=" + this.getAttribute(stringArray[i]);
        }
        string = string + "]";
        return string;
    }
}

