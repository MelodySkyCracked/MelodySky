/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULLabeledControlElement;

public interface nsIDOMXULCheckboxElement
extends nsIDOMXULLabeledControlElement {
    public static final String NS_IDOMXULCHECKBOXELEMENT_IID = "{5afaba88-1dd2-11b2-9249-dd65a129d0e4}";
    public static final short CHECKSTATE_UNCHECKED = 0;
    public static final short CHECKSTATE_CHECKED = 1;
    public static final short CHECKSTATE_MIXED = 2;

    public boolean getChecked();

    public void setChecked(boolean var1);

    public int getCheckState();

    public void setCheckState(int var1);

    public boolean getAutoCheck();

    public void setAutoCheck(boolean var1);
}

