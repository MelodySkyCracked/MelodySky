/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISecurityCheckedComponent
extends nsISupports {
    public static final String NS_ISECURITYCHECKEDCOMPONENT_IID = "{0dad9e8c-a12d-4dcb-9a6f-7d09839356e1}";

    public String canCreateWrapper(String var1);

    public String canCallMethod(String var1, String var2);

    public String canGetProperty(String var1, String var2);

    public String canSetProperty(String var1, String var2);
}

