/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsIDOMXULSelectControlElement;
import org.mozilla.interfaces.nsIDOMXULSelectControlItemElement;

public interface nsIDOMXULMultiSelectControlElement
extends nsIDOMXULSelectControlElement {
    public static final String NS_IDOMXULMULTISELECTCONTROLELEMENT_IID = "{82c72eca-9886-473e-94cd-9de5694b3f88}";

    public String getSelType();

    public void setSelType(String var1);

    public nsIDOMXULSelectControlItemElement getCurrentItem();

    public void setCurrentItem(nsIDOMXULSelectControlItemElement var1);

    public int getCurrentIndex();

    public void setCurrentIndex(int var1);

    public nsIDOMNodeList getSelectedItems();

    public void addItemToSelection(nsIDOMXULSelectControlItemElement var1);

    public void removeItemFromSelection(nsIDOMXULSelectControlItemElement var1);

    public void toggleItemSelection(nsIDOMXULSelectControlItemElement var1);

    public void selectItem(nsIDOMXULSelectControlItemElement var1);

    public void selectItemRange(nsIDOMXULSelectControlItemElement var1, nsIDOMXULSelectControlItemElement var2);

    public void selectAll();

    public void invertSelection();

    public void clearSelection();

    public int getSelectedCount();

    public nsIDOMXULSelectControlItemElement getSelectedItem(int var1);
}

