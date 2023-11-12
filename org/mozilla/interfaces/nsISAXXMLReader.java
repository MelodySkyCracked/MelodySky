/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIRequestObserver;
import org.mozilla.interfaces.nsISAXContentHandler;
import org.mozilla.interfaces.nsISAXDTDHandler;
import org.mozilla.interfaces.nsISAXErrorHandler;
import org.mozilla.interfaces.nsISAXLexicalHandler;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsISAXXMLReader
extends nsIStreamListener {
    public static final String NS_ISAXXMLREADER_IID = "{5556997e-d816-4218-8b54-803d4261206e}";

    public nsIURI getBaseURI();

    public void setBaseURI(nsIURI var1);

    public nsISAXContentHandler getContentHandler();

    public void setContentHandler(nsISAXContentHandler var1);

    public nsISAXDTDHandler getDtdHandler();

    public void setDtdHandler(nsISAXDTDHandler var1);

    public nsISAXErrorHandler getErrorHandler();

    public void setErrorHandler(nsISAXErrorHandler var1);

    public nsISAXLexicalHandler getLexicalHandler();

    public void setLexicalHandler(nsISAXLexicalHandler var1);

    public void setFeature(String var1, boolean var2);

    public boolean getFeature(String var1);

    public void setProperty(String var1, nsISupports var2);

    public boolean getProperty(String var1);

    public void parseFromString(String var1, String var2);

    public void parseFromStream(nsIInputStream var1, String var2, String var3);

    public void parseAsync(nsIRequestObserver var1);
}

