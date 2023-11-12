/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULSelectControlElement;
import org.mozilla.interfaces.nsIDOMXULTextBoxElement;

public interface nsIDOMXULMenuListElement
extends nsIDOMXULSelectControlElement {
    public static final String NS_IDOMXULMENULISTELEMENT_IID = "{3d49950e-04f9-4e35-a9a0-ffd51356a674}";

    public boolean getEditable();

    public void setEditable(boolean var1);

    public boolean getOpen();

    public void setOpen(boolean var1);

    public String getLabel();

    public String getCrop();

    public void setCrop(String var1);

    public String getImage();

    public void setImage(String var1);

    public nsIDOMXULTextBoxElement getInputField();
}

