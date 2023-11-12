/*
 * Decompiled with CFR 0.152.
 */
package org.newdawn.slick.svg;

import org.newdawn.slick.SlickException;
import org.w3c.dom.Element;

public class ParsingException
extends SlickException {
    public ParsingException(String string, String string2, Throwable throwable) {
        super("(" + string + ") " + string2, throwable);
    }

    public ParsingException(Element element, String string, Throwable throwable) {
        super("(" + element.getAttribute("id") + ") " + string, throwable);
    }

    public ParsingException(String string, String string2) {
        super("(" + string + ") " + string2);
    }

    public ParsingException(Element element, String string) {
        super("(" + element.getAttribute("id") + ") " + string);
    }
}

