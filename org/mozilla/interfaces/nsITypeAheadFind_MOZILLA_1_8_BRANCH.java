/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsITypeAheadFind;

public interface nsITypeAheadFind_MOZILLA_1_8_BRANCH
extends nsITypeAheadFind {
    public static final String NS_ITYPEAHEADFIND_MOZILLA_1_8_BRANCH_IID = "{6e934f17-1975-49c2-880e-4f9fa79a1b2e}";

    public void setSelectionModeAndRepaint(short var1);

    public void collapseSelection();

    public nsIDOMElement getFoundEditable();
}

