/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIExceptionManager;
import org.mozilla.interfaces.nsIExceptionProvider;

public interface nsIExceptionService
extends nsIExceptionManager {
    public static final String NS_IEXCEPTIONSERVICE_IID = "{35a88f54-f267-4414-92a7-191f6454ab52}";

    public nsIExceptionManager getCurrentExceptionManager();

    public void registerExceptionProvider(nsIExceptionProvider var1, long var2);

    public void unregisterExceptionProvider(nsIExceptionProvider var1, long var2);
}

