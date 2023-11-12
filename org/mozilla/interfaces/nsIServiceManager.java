/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIServiceManager
extends nsISupports {
    public static final String NS_ISERVICEMANAGER_IID = "{8bb35ed9-e332-462d-9155-4a002ab5c958}";

    public nsISupports getService(String var1, String var2);

    public nsISupports getServiceByContractID(String var1, String var2);

    public boolean isServiceInstantiated(String var1, String var2);

    public boolean isServiceInstantiatedByContractID(String var1, String var2);
}

