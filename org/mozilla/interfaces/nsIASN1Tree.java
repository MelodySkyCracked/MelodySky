/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIASN1Object;
import org.mozilla.interfaces.nsITreeView;

public interface nsIASN1Tree
extends nsITreeView {
    public static final String NS_IASN1TREE_IID = "{c727b2f2-1dd1-11b2-95df-f63c15b4cd35}";

    public void loadASN1Structure(nsIASN1Object var1);

    public String getDisplayData(long var1);
}

