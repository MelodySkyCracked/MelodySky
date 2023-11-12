/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests.xml;

import org.newdawn.slick.tests.xml.GameData;
import org.newdawn.slick.tests.xml.ItemContainer;
import org.newdawn.slick.util.xml.ObjectTreeParser;
import org.newdawn.slick.util.xml.SlickXMLException;

public class ObjectParserTest {
    public static void main(String[] stringArray) throws SlickXMLException {
        ObjectTreeParser objectTreeParser = new ObjectTreeParser("org.newdawn.slick.tests.xml");
        objectTreeParser.addElementMapping("Bag", ItemContainer.class);
        GameData gameData = (GameData)objectTreeParser.parse("testdata/objxmltest.xml");
        gameData.dump("");
    }
}

