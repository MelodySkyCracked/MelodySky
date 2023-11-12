/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPropertyBag;
import org.mozilla.interfaces.nsIVariant;

public interface nsIWritablePropertyBag
extends nsIPropertyBag {
    public static final String NS_IWRITABLEPROPERTYBAG_IID = "{96fc4671-eeb4-4823-9421-e50fb70ad353}";

    public void setProperty(String var1, nsIVariant var2);

    public void deleteProperty(String var1);
}

