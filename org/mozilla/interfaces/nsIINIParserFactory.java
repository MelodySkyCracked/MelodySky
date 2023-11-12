/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIINIParser;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIINIParserFactory
extends nsISupports {
    public static final String NS_IINIPARSERFACTORY_IID = "{ccae7ea5-1218-4b51-aecb-c2d8ecd46af9}";

    public nsIINIParser createINIParser(nsILocalFile var1);
}

