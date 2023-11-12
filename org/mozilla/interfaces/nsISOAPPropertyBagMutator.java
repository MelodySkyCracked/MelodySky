/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPropertyBag;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsISOAPPropertyBagMutator
extends nsISupports {
    public static final String NS_ISOAPPROPERTYBAGMUTATOR_IID = "{f34cb3c8-1dd1-11b2-8a18-a93a99d92c08}";

    public nsIPropertyBag getPropertyBag();

    public void addProperty(String var1, nsIVariant var2);
}

