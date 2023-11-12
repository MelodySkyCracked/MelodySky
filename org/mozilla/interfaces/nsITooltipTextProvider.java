/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsITooltipTextProvider
extends nsISupports {
    public static final String NS_ITOOLTIPTEXTPROVIDER_IID = "{b128a1e6-44f3-4331-8fbe-5af360ff21ee}";

    public boolean getNodeText(nsIDOMNode var1, String[] var2);
}

