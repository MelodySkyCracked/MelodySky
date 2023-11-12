/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIController;
import org.mozilla.interfaces.nsIControllers;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMXULCommandDispatcher
extends nsISupports {
    public static final String NS_IDOMXULCOMMANDDISPATCHER_IID = "{f3c50361-14fe-11d3-bf87-00105a1b0627}";

    public nsIDOMElement getFocusedElement();

    public void setFocusedElement(nsIDOMElement var1);

    public nsIDOMWindow getFocusedWindow();

    public void setFocusedWindow(nsIDOMWindow var1);

    public void addCommandUpdater(nsIDOMElement var1, String var2, String var3);

    public void removeCommandUpdater(nsIDOMElement var1);

    public void updateCommands(String var1);

    public nsIController getControllerForCommand(String var1);

    public nsIControllers getControllers();

    public void advanceFocus();

    public void rewindFocus();

    public void advanceFocusIntoSubtree(nsIDOMElement var1);

    public boolean getSuppressFocusScroll();

    public void setSuppressFocusScroll(boolean var1);
}

