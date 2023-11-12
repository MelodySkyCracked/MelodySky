/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIHttpHeaderVisitor
extends nsISupports {
    public static final String NS_IHTTPHEADERVISITOR_IID = "{0cf40717-d7c1-4a94-8c1e-d6c9734101bb}";

    public void visitHeader(String var1, String var2);
}

