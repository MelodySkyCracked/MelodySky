/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSFeatureFactory
extends nsISupports {
    public static final String NS_IDOMNSFEATUREFACTORY_IID = "{dc5ba787-b648-4b01-a8e7-b293ffb044ef}";

    public boolean hasFeature(nsISupports var1, String var2, String var3);

    public nsISupports getFeature(nsISupports var1, String var2, String var3);
}

