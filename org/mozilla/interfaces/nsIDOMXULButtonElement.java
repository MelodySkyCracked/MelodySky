/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULLabeledControlElement;

public interface nsIDOMXULButtonElement
extends nsIDOMXULLabeledControlElement {
    public static final String NS_IDOMXULBUTTONELEMENT_IID = "{6852d9a6-1dd2-11b2-a29d-cd7977a91b1b}";
    public static final short CHECKSTATE_UNCHECKED = 0;
    public static final short CHECKSTATE_CHECKED = 1;
    public static final short CHECKSTATE_MIXED = 2;

    public String getType();

    public void setType(String var1);

    public String getDlgType();

    public void setDlgType(String var1);

    public boolean getOpen();

    public void setOpen(boolean var1);

    public boolean getChecked();

    public void setChecked(boolean var1);

    public int getCheckState();

    public void setCheckState(int var1);

    public boolean getAutoCheck();

    public void setAutoCheck(boolean var1);

    public String getGroup();

    public void setGroup(String var1);
}

