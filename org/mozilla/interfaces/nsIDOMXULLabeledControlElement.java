/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULControlElement;

public interface nsIDOMXULLabeledControlElement
extends nsIDOMXULControlElement {
    public static final String NS_IDOMXULLABELEDCONTROLELEMENT_IID = "{a457ea70-1dd1-11b2-9089-8fd894122084}";

    public String getCrop();

    public void setCrop(String var1);

    public String getImage();

    public void setImage(String var1);

    public String getLabel();

    public void setLabel(String var1);

    public String getAccessKey();

    public void setAccessKey(String var1);

    public String getCommand();

    public void setCommand(String var1);
}

