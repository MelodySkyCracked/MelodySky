/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAccessible;
import org.mozilla.interfaces.nsISupports;

public interface nsIAccessibleProvider
extends nsISupports {
    public static final String NS_IACCESSIBLEPROVIDER_IID = "{3f0e3eb0-1dd2-11b2-9605-be5b8e76cf4b}";

    public nsIAccessible getAccessible();
}

