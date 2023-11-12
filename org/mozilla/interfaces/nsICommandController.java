/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICommandParams;
import org.mozilla.interfaces.nsISupports;

public interface nsICommandController
extends nsISupports {
    public static final String NS_ICOMMANDCONTROLLER_IID = "{ebe55080-c8a9-11d5-a73c-dd620d6e04bc}";

    public void getCommandStateWithParams(String var1, nsICommandParams var2);

    public void doCommandWithParams(String var1, nsICommandParams var2);
}

