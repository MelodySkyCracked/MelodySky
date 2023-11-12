/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIMIMEInfo;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIMIMEService
extends nsISupports {
    public static final String NS_IMIMESERVICE_IID = "{5b3675a1-02db-4f8f-a560-b34736635f47}";

    public nsIMIMEInfo getFromTypeAndExtension(String var1, String var2);

    public String getTypeFromExtension(String var1);

    public String getTypeFromURI(nsIURI var1);

    public String getTypeFromFile(nsIFile var1);

    public String getPrimaryExtension(String var1, String var2);
}

