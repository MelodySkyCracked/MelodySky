/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIControllerCommandGroup
extends nsISupports {
    public static final String NS_ICONTROLLERCOMMANDGROUP_IID = "{9f82c404-1c7b-11d5-a73c-eca43ca836fc}";

    public void addCommandToGroup(String var1, String var2);

    public void removeCommandFromGroup(String var1, String var2);

    public boolean isCommandInGroup(String var1, String var2);

    public nsISimpleEnumerator getGroupsEnumerator();

    public nsISimpleEnumerator getEnumeratorForGroup(String var1);
}

