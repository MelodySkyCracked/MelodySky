/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEnumerator;
import org.mozilla.interfaces.nsIPKCS11Module;
import org.mozilla.interfaces.nsIPKCS11Slot;
import org.mozilla.interfaces.nsISupports;

public interface nsIPKCS11ModuleDB
extends nsISupports {
    public static final String NS_IPKCS11MODULEDB_IID = "{ff9fbcd7-9517-4334-b97a-ceed78909974}";

    public nsIPKCS11Module getInternal();

    public nsIPKCS11Module getInternalFIPS();

    public nsIPKCS11Module findModuleByName(String var1);

    public nsIPKCS11Slot findSlotByName(String var1);

    public nsIEnumerator listModules();

    public boolean getCanToggleFIPS();

    public void toggleFIPSMode();

    public boolean getIsFIPSEnabled();
}

