/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAccessNode;
import org.mozilla.interfaces.nsIAccessible;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIAccessibleRetrieval
extends nsISupports {
    public static final String NS_IACCESSIBLERETRIEVAL_IID = "{663ca4a8-d219-4000-925d-d8f66406b626}";

    public nsIAccessible getAccessibleFor(nsIDOMNode var1);

    public nsIAccessible getAccessibleInWindow(nsIDOMNode var1, nsIDOMWindow var2);

    public nsIAccessible getAccessibleInWeakShell(nsIDOMNode var1, nsISupports var2);

    public nsIAccessible getAccessibleInShell(nsIDOMNode var1, nsISupports var2);

    public nsIAccessNode getCachedAccessNode(nsIDOMNode var1, nsISupports var2);

    public nsIAccessible getCachedAccessible(nsIDOMNode var1, nsISupports var2);
}

