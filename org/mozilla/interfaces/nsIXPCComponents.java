/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIComponentManager;
import org.mozilla.interfaces.nsIScriptableInterfaces;
import org.mozilla.interfaces.nsIScriptableInterfacesByID;
import org.mozilla.interfaces.nsIStackFrame;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIXPCComponents_Classes;
import org.mozilla.interfaces.nsIXPCComponents_ClassesByID;
import org.mozilla.interfaces.nsIXPCComponents_Constructor;
import org.mozilla.interfaces.nsIXPCComponents_Exception;
import org.mozilla.interfaces.nsIXPCComponents_ID;
import org.mozilla.interfaces.nsIXPCComponents_Results;
import org.mozilla.interfaces.nsIXPCComponents_Utils;

public interface nsIXPCComponents
extends nsISupports {
    public static final String NS_IXPCCOMPONENTS_IID = "{155809f1-71f1-47c5-be97-d812ba560405}";

    public nsIScriptableInterfaces getInterfaces();

    public nsIScriptableInterfacesByID getInterfacesByID();

    public nsIXPCComponents_Classes getClasses();

    public nsIXPCComponents_ClassesByID getClassesByID();

    public nsIStackFrame getStack();

    public nsIXPCComponents_Results getResults();

    public nsIComponentManager getManager();

    public nsIXPCComponents_Utils getUtils();

    public nsIXPCComponents_ID getID();

    public nsIXPCComponents_Exception getException();

    public nsIXPCComponents_Constructor getConstructor();

    public boolean isSuccessCode(long var1);

    public void lookupMethod();

    public void reportError();
}

