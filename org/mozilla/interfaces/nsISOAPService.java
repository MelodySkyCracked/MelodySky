/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISOAPMessage;
import org.mozilla.interfaces.nsISOAPResponseListener;
import org.mozilla.interfaces.nsISupports;

public interface nsISOAPService
extends nsISupports {
    public static final String NS_ISOAPSERVICE_IID = "{9927fa40-1dd1-11b2-a8d1-857ad21b872c}";

    public nsISupports getConfiguration();

    public void setConfiguration(nsISupports var1);

    public boolean process(nsISOAPMessage var1, nsISOAPResponseListener var2);
}

