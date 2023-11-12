/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFactory;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIComponentRegistrar
extends nsISupports {
    public static final String NS_ICOMPONENTREGISTRAR_IID = "{2417cbfe-65ad-48a6-b4b6-eb84db174392}";

    public void autoRegister(nsIFile var1);

    public void autoUnregister(nsIFile var1);

    public void registerFactory(String var1, String var2, String var3, nsIFactory var4);

    public void unregisterFactory(String var1, nsIFactory var2);

    public void registerFactoryLocation(String var1, String var2, String var3, nsIFile var4, String var5, String var6);

    public void unregisterFactoryLocation(String var1, nsIFile var2);

    public boolean isCIDRegistered(String var1);

    public boolean isContractIDRegistered(String var1);

    public nsISimpleEnumerator enumerateCIDs();

    public nsISimpleEnumerator enumerateContractIDs();

    public String cIDToContractID(String var1);

    public String contractIDToCID(String var1);
}

