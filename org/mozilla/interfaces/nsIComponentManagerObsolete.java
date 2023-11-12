/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEnumerator;
import org.mozilla.interfaces.nsIFactory;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIComponentManagerObsolete
extends nsISupports {
    public static final String NS_ICOMPONENTMANAGEROBSOLETE_IID = "{8458a740-d5dc-11d2-92fb-00e09805570f}";
    public static final int NS_Startup = 0;
    public static final int NS_Script = 1;
    public static final int NS_Timer = 2;
    public static final int NS_Shutdown = 3;

    public nsIFactory findFactory(String var1);

    public String cLSIDToContractID(String var1, String[] var2);

    public String registryLocationForSpec(nsIFile var1);

    public nsIFile specForRegistryLocation(String var1);

    public void registerFactory(String var1, String var2, String var3, nsIFactory var4, boolean var5);

    public void registerComponent(String var1, String var2, String var3, String var4, boolean var5, boolean var6);

    public void registerComponentWithType(String var1, String var2, String var3, nsIFile var4, String var5, boolean var6, boolean var7, String var8);

    public void registerComponentSpec(String var1, String var2, String var3, nsIFile var4, boolean var5, boolean var6);

    public void registerComponentLib(String var1, String var2, String var3, String var4, boolean var5, boolean var6);

    public void unregisterFactory(String var1, nsIFactory var2);

    public void unregisterComponent(String var1, String var2);

    public void unregisterComponentSpec(String var1, nsIFile var2);

    public void freeLibraries();

    public void autoRegister(int var1, nsIFile var2);

    public void autoRegisterComponent(int var1, nsIFile var2);

    public void autoUnregisterComponent(int var1, nsIFile var2);

    public boolean isRegistered(String var1);

    public nsIEnumerator enumerateCLSIDs();

    public nsIEnumerator enumerateContractIDs();
}

