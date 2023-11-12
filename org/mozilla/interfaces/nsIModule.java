/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIComponentManager;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIModule
extends nsISupports {
    public static final String NS_IMODULE_IID = "{7392d032-5371-11d3-994e-00805fd26fee}";

    public nsISupports getClassObject(nsIComponentManager var1, String var2, String var3);

    public void registerSelf(nsIComponentManager var1, nsIFile var2, String var3, String var4);

    public void unregisterSelf(nsIComponentManager var1, nsIFile var2, String var3);

    public boolean canUnload(nsIComponentManager var1);
}

