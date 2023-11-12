/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsISelection;

public interface nsISelection2
extends nsISelection {
    public static final String NS_ISELECTION2_IID = "{eab4ae76-efdc-4e09-828c-bd921e9a662f}";

    public nsIDOMRange[] getRangesForInterval(nsIDOMNode var1, int var2, nsIDOMNode var3, int var4, boolean var5, long[] var6);
}

