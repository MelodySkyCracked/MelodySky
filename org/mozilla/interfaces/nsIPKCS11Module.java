/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEnumerator;
import org.mozilla.interfaces.nsIPKCS11Slot;
import org.mozilla.interfaces.nsISupports;

public interface nsIPKCS11Module
extends nsISupports {
    public static final String NS_IPKCS11MODULE_IID = "{8a44bdf9-d1a5-4734-bd5a-34ed7fe564c2}";

    public String getName();

    public String getLibName();

    public nsIPKCS11Slot findSlotByName(String var1);

    public nsIEnumerator listSlots();
}

