/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULElement;

public interface nsIDOMXULControlElement
extends nsIDOMXULElement {
    public static final String NS_IDOMXULCONTROLELEMENT_IID = "{007b8358-1dd2-11b2-8924-d209efc3f124}";

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public int getTabIndex();

    public void setTabIndex(int var1);
}

