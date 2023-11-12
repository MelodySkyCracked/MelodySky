/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISAXLocator
extends nsISupports {
    public static final String NS_ISAXLOCATOR_IID = "{7a307c6c-6cc9-11da-be43-001422106990}";

    public int getColumnNumber();

    public int getLineNumber();

    public String getPublicId();

    public String getSystemId();
}

