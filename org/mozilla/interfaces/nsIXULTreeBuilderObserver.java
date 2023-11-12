/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIXULTreeBuilderObserver
extends nsISupports {
    public static final String NS_IXULTREEBUILDEROBSERVER_IID = "{a5480e0d-ac7c-42e5-aca5-d7f0bbffa207}";
    public static final int DROP_BEFORE = -1;
    public static final int DROP_ON = 0;
    public static final int DROP_AFTER = 1;

    public boolean canDrop(int var1, int var2);

    public void onDrop(int var1, int var2);

    public void onToggleOpenState(int var1);

    public void onCycleHeader(String var1, nsIDOMElement var2);

    public void onCycleCell(int var1, String var2);

    public void onSelectionChanged();

    public void onPerformAction(String var1);

    public void onPerformActionOnRow(String var1, int var2);

    public void onPerformActionOnCell(String var1, int var2, String var3);
}

