/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIAccessibleCaret
extends nsISupports {
    public static final String NS_IACCESSIBLECARET_IID = "{9124c666-6133-4be6-b3ed-dd0ec35f1e64}";

    public void attachNewSelectionListener(nsIDOMNode var1);

    public void removeSelectionListener();
}

