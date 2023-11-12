/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULElement;
import org.mozilla.interfaces.nsIDOMXULSelectControlElement;

public interface nsIDOMXULSelectControlItemElement
extends nsIDOMXULElement {
    public static final String NS_IDOMXULSELECTCONTROLITEMELEMENT_IID = "{6aaaa30d-54ab-434a-8ae8-6d29a566d870}";

    public boolean getDisabled();

    public void setDisabled(boolean var1);

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

    public String getValue();

    public void setValue(String var1);

    public boolean getSelected();

    public nsIDOMXULSelectControlElement getControl();
}

