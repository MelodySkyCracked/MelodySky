/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICommandParams;
import org.mozilla.interfaces.nsISupports;

public interface nsIControllerCommand
extends nsISupports {
    public static final String NS_ICONTROLLERCOMMAND_IID = "{0eae9a46-1dd2-11b2-aca0-9176f05fe9db}";

    public boolean isCommandEnabled(String var1, nsISupports var2);

    public void getCommandStateParams(String var1, nsICommandParams var2, nsISupports var3);

    public void doCommand(String var1, nsISupports var2);

    public void doCommandParams(String var1, nsICommandParams var2, nsISupports var3);
}

