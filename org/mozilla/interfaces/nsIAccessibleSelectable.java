/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAccessible;
import org.mozilla.interfaces.nsIArray;
import org.mozilla.interfaces.nsISupports;

public interface nsIAccessibleSelectable
extends nsISupports {
    public static final String NS_IACCESSIBLESELECTABLE_IID = "{34d268d6-1dd2-11b2-9d63-83a5e0ada290}";

    public nsIArray getSelectedChildren();

    public int getSelectionCount();

    public void addChildToSelection(int var1);

    public void removeChildFromSelection(int var1);

    public void clearSelection();

    public nsIAccessible refSelection(int var1);

    public boolean isChildSelected(int var1);

    public boolean selectAllSelection();
}

