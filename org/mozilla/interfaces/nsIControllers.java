/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIController;
import org.mozilla.interfaces.nsIDOMXULCommandDispatcher;
import org.mozilla.interfaces.nsISupports;

public interface nsIControllers
extends nsISupports {
    public static final String NS_ICONTROLLERS_IID = "{a5ed3a01-7cc7-11d3-bf87-00105a1b0627}";

    public nsIDOMXULCommandDispatcher getCommandDispatcher();

    public void setCommandDispatcher(nsIDOMXULCommandDispatcher var1);

    public nsIController getControllerForCommand(String var1);

    public void insertControllerAt(long var1, nsIController var3);

    public nsIController removeControllerAt(long var1);

    public nsIController getControllerAt(long var1);

    public void appendController(nsIController var1);

    public void removeController(nsIController var1);

    public long getControllerId(nsIController var1);

    public nsIController getControllerById(long var1);

    public long getControllerCount();
}

