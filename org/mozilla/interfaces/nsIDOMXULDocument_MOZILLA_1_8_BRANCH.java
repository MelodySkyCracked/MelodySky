/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMXULDocument_MOZILLA_1_8_BRANCH
extends nsISupports {
    public static final String NS_IDOMXULDOCUMENT_MOZILLA_1_8_BRANCH_IID = "{8cc6316d-ecbc-4c5f-8fda-28c91fb56e3d}";

    public nsIDOMNode getPopupRangeParent();

    public int getPopupRangeOffset();
}

