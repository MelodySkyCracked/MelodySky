/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.tests.xml;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.xml.XMLElement;
import org.newdawn.slick.util.xml.XMLParser;

public class XMLTest {
    private static void fail(String string) {
        throw new RuntimeException(string);
    }

    private static void assertNotNull(Object object) {
        if (object == null) {
            throw new RuntimeException("TEST FAILS: " + object + " must not be null");
        }
    }

    private static void assertEquals(float f, float f2) {
        if (f != f2) {
            throw new RuntimeException("TEST FAILS: " + f + " should be " + f2);
        }
    }

    private static void assertEquals(int n, int n2) {
        if (n != n2) {
            throw new RuntimeException("TEST FAILS: " + n + " should be " + n2);
        }
    }

    private static void assertEquals(Object object, Object object2) {
        if (!object.equals(object2)) {
            throw new RuntimeException("TEST FAILS: " + object + " should be " + object2);
        }
    }

    public static void main(String[] stringArray) throws SlickException {
        XMLParser xMLParser = new XMLParser();
        XMLElement xMLElement = xMLParser.parse("testdata/test.xml");
        XMLTest.assertEquals(xMLElement.getName(), "testRoot");
        System.out.println(xMLElement);
        XMLTest.assertNotNull(xMLElement.getChildrenByName("simple").get(0).getContent());
        System.out.println(xMLElement.getChildrenByName("simple").get(0).getContent());
        XMLElement xMLElement2 = xMLElement.getChildrenByName("parent").get(0);
        XMLTest.assertEquals(xMLElement2.getChildrenByName("grandchild").size(), 0);
        XMLTest.assertEquals(xMLElement2.getChildrenByName("child").size(), 2);
        XMLTest.assertEquals(xMLElement2.getChildrenByName("child").get(0).getChildren().size(), 2);
        XMLElement xMLElement3 = xMLElement2.getChildrenByName("child").get(0).getChildren().get(0);
        String string = xMLElement3.getAttribute("name");
        String string2 = xMLElement3.getAttribute("nothere", "defaultValue");
        int n = xMLElement3.getIntAttribute("age");
        XMLTest.assertEquals(string, "bob");
        XMLTest.assertEquals(string2, "defaultValue");
        XMLTest.assertEquals(n, 1);
        XMLElement xMLElement4 = xMLElement.getChildrenByName("other").get(0);
        float f = (float)xMLElement4.getDoubleAttribute("x");
        float f2 = (float)xMLElement4.getDoubleAttribute("y", 1.0);
        float f3 = (float)xMLElement4.getDoubleAttribute("z", 83.0);
        XMLTest.assertEquals(f, 5.3f);
        XMLTest.assertEquals(f2, 5.4f);
        XMLTest.assertEquals(f3, 83.0f);
        try {
            f3 = (float)xMLElement4.getDoubleAttribute("z");
            XMLTest.fail("Attribute z as a double should fail");
        }
        catch (SlickException slickException) {
            // empty catch block
        }
    }
}

