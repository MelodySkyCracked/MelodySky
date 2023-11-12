/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSXPathExpression
extends nsISupports {
    public static final String NS_IDOMNSXPATHEXPRESSION_IID = "{ce600ca8-e98a-4419-ad61-2f6d0cb0ecc8}";

    public nsISupports evaluateWithContext(nsIDOMNode var1, long var2, long var4, int var6, nsISupports var7);
}

