/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISOAPEncodingRegistry;
import org.mozilla.interfaces.nsISOAPService;

public interface nsISOAPServiceRegistry {
    public static final String NS_ISOAPSERVICEREGISTRY_IID = "{9790d6bc-1dd1-11b2-afe0-bcb310c078bf}";

    public boolean addConfiguration(nsIDOMElement var1);

    public void addSource(String var1, boolean var2);

    public void addService(nsISOAPService var1);

    public nsISOAPEncodingRegistry getEncodings();

    public void setEncodings(nsISOAPEncodingRegistry var1);
}

