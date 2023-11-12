/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIPropertyBag
extends nsISupports {
    public static final String NS_IPROPERTYBAG_IID = "{bfcd37b0-a49f-11d5-910d-0010a4e73d9a}";

    public nsISimpleEnumerator getEnumerator();

    public nsIVariant getProperty(String var1);
}

