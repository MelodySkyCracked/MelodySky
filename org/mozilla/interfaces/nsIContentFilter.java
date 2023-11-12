/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURL;

public interface nsIContentFilter
extends nsISupports {
    public static final String NS_ICONTENTFILTER_IID = "{c18c49a8-62f0-4045-9884-4aa91e388f14}";

    public void notifyOfInsertion(String var1, nsIURL var2, nsIDOMDocument var3, boolean var4, nsIDOMNode[] var5, nsIDOMNode[] var6, int[] var7, nsIDOMNode[] var8, int[] var9, nsIDOMNode[] var10, int[] var11, boolean[] var12);
}

