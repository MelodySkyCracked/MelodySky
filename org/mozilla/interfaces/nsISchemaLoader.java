/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISchema;
import org.mozilla.interfaces.nsISchemaLoadListener;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebServiceErrorHandler;

public interface nsISchemaLoader
extends nsISupports {
    public static final String NS_ISCHEMALOADER_IID = "{9b2f0b4a-8f00-4a78-961a-7e84ed49b0b6}";

    public nsISchema load(String var1);

    public void loadAsync(String var1, nsISchemaLoadListener var2);

    public nsISchema processSchemaElement(nsIDOMElement var1, nsIWebServiceErrorHandler var2);
}

