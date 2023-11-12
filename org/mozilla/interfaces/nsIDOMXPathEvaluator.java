/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMXPathExpression;
import org.mozilla.interfaces.nsIDOMXPathNSResolver;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMXPathEvaluator
extends nsISupports {
    public static final String NS_IDOMXPATHEVALUATOR_IID = "{75506f8a-b504-11d5-a7f2-ca108ab8b6fc}";

    public nsIDOMXPathExpression createExpression(String var1, nsIDOMXPathNSResolver var2);

    public nsIDOMXPathNSResolver createNSResolver(nsIDOMNode var1);

    public nsISupports evaluate(String var1, nsIDOMNode var2, nsIDOMXPathNSResolver var3, int var4, nsISupports var5);
}

