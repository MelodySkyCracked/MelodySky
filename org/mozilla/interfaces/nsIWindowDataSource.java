/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindowInternal;
import org.mozilla.interfaces.nsISupports;

public interface nsIWindowDataSource
extends nsISupports {
    public static final String NS_IWINDOWDATASOURCE_IID = "{3722a5b9-5323-4ed0-bb1a-8299f27a4e89}";

    public nsIDOMWindowInternal getWindowForResource(String var1);
}

