/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIComponentManager
extends nsISupports {
    public static final String NS_ICOMPONENTMANAGER_IID = "{a88e5a60-205a-4bb1-94e1-2628daf51eae}";

    public nsISupports getClassObject(String var1, String var2);

    public nsISupports getClassObjectByContractID(String var1, String var2);

    public nsISupports createInstance(String var1, nsISupports var2, String var3);

    public nsISupports createInstanceByContractID(String var1, nsISupports var2, String var3);
}

