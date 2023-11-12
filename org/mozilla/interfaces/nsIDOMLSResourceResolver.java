/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMLSInput;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLSResourceResolver
extends nsISupports {
    public static final String NS_IDOMLSRESOURCERESOLVER_IID = "{9e61c7c8-8698-4477-9971-0923513919bd}";

    public nsIDOMLSInput resolveResource(String var1, String var2, String var3, String var4, String var5);
}

