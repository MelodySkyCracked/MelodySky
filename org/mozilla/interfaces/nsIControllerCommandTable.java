/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICommandParams;
import org.mozilla.interfaces.nsIControllerCommand;
import org.mozilla.interfaces.nsISupports;

public interface nsIControllerCommandTable
extends nsISupports {
    public static final String NS_ICONTROLLERCOMMANDTABLE_IID = "{d1a47834-6ad4-11d7-bfad-000393636592}";

    public void makeImmutable();

    public void registerCommand(String var1, nsIControllerCommand var2);

    public void unregisterCommand(String var1, nsIControllerCommand var2);

    public nsIControllerCommand findCommandHandler(String var1);

    public boolean isCommandEnabled(String var1, nsISupports var2);

    public void updateCommandState(String var1, nsISupports var2);

    public boolean supportsCommand(String var1, nsISupports var2);

    public void doCommand(String var1, nsISupports var2);

    public void doCommandParams(String var1, nsICommandParams var2, nsISupports var3);

    public void getCommandState(String var1, nsICommandParams var2, nsISupports var3);
}

