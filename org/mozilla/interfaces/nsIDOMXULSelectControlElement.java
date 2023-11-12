/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULControlElement;
import org.mozilla.interfaces.nsIDOMXULSelectControlItemElement;

public interface nsIDOMXULSelectControlElement
extends nsIDOMXULControlElement {
    public static final String NS_IDOMXULSELECTCONTROLELEMENT_IID = "{59fec127-2a0e-445b-84b5-a66dc90245db}";

    public nsIDOMXULSelectControlItemElement getSelectedItem();

    public void setSelectedItem(nsIDOMXULSelectControlItemElement var1);

    public int getSelectedIndex();

    public void setSelectedIndex(int var1);

    public String getValue();

    public void setValue(String var1);

    public nsIDOMXULSelectControlItemElement appendItem(String var1, String var2);

    public nsIDOMXULSelectControlItemElement insertItemAt(int var1, String var2, String var3);

    public nsIDOMXULSelectControlItemElement removeItemAt(int var1);
}

