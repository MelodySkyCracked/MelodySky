/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsIJVMConfig;
import org.mozilla.interfaces.nsISupports;

public interface nsIJVMConfigManager
extends nsISupports {
    public static final String NS_IJVMCONFIGMANAGER_IID = "{ca29fff1-a677-493c-9d80-3dc60432212b}";

    public nsIArray getJVMConfigList();

    public void setCurrentJVMConfig(nsIJVMConfig var1);
}

