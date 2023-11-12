/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXTFElement;

public interface nsIXTFElementFactory
extends nsISupports {
    public static final String NS_IXTFELEMENTFACTORY_IID = "{27c10dca-2efc-416b-ae36-9794380a661e}";

    public nsIXTFElement createElement(String var1);
}

