/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMXPathExpression
extends nsISupports {
    public static final String NS_IDOMXPATHEXPRESSION_IID = "{75506f82-b504-11d5-a7f2-ca108ab8b6fc}";

    public nsISupports evaluate(nsIDOMNode var1, int var2, nsISupports var3);
}

