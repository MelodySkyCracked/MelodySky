/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWSDLLoadListener;
import org.mozilla.interfaces.nsIWSDLPort;

public interface nsIWSDLLoader
extends nsISupports {
    public static final String NS_IWSDLLOADER_IID = "{0458dac5-65de-11d5-9b42-00104bdf5339}";

    public nsIWSDLPort load(String var1, String var2);

    public void loadAsync(String var1, String var2, nsIWSDLLoadListener var3);
}

