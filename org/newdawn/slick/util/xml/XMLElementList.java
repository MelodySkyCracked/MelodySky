/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.util.xml;

import java.util.ArrayList;
import java.util.Collection;
import org.newdawn.slick.util.xml.XMLElement;

public class XMLElementList {
    private ArrayList list = new ArrayList();

    public void add(XMLElement xMLElement) {
        this.list.add(xMLElement);
    }

    public int size() {
        return this.list.size();
    }

    public XMLElement get(int n) {
        return (XMLElement)this.list.get(n);
    }

    public boolean contains(XMLElement xMLElement) {
        return this.list.contains(xMLElement);
    }

    public void addAllTo(Collection collection) {
        collection.addAll(this.list);
    }
}

