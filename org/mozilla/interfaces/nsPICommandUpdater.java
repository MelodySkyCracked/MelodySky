/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsPICommandUpdater
extends nsISupports {
    public static final String NS_PICOMMANDUPDATER_IID = "{b135f602-0bfe-11d5-a73c-f0e420e8293c}";

    public void init(nsIDOMWindow var1);

    public void commandStatusChanged(String var1);
}

