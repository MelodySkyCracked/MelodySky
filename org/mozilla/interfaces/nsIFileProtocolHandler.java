/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIProtocolHandler;
import org.mozilla.interfaces.nsIURI;

public interface nsIFileProtocolHandler
extends nsIProtocolHandler {
    public static final String NS_IFILEPROTOCOLHANDLER_IID = "{255602ea-c31f-4d29-8f35-905ead3f76f4}";

    public nsIURI newFileURI(nsIFile var1);

    public String getURLSpecFromFile(nsIFile var1);

    public nsIFile getFileFromURLSpec(String var1);

    public nsIURI readURLFile(nsIFile var1);
}

