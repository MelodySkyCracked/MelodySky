/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISAXAttributes;
import org.mozilla.interfaces.nsISupports;

public interface nsISAXContentHandler
extends nsISupports {
    public static final String NS_ISAXCONTENTHANDLER_IID = "{2a99c757-dfee-4806-bff3-f721440412e0}";

    public void startDocument();

    public void endDocument();

    public void startElement(String var1, String var2, String var3, nsISAXAttributes var4);

    public void endElement(String var1, String var2, String var3);

    public void characters(String var1);

    public void processingInstruction(String var1, String var2);

    public void ignorableWhitespace(String var1);

    public void startPrefixMapping(String var1, String var2);

    public void endPrefixMapping(String var1);
}

