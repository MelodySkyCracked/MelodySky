/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULElement;

public interface nsIDOMXULDescriptionElement
extends nsIDOMXULElement {
    public static final String NS_IDOMXULDESCRIPTIONELEMENT_IID = "{c7b0b43c-1dd1-11b2-9e1c-ce5f6a660630}";

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public boolean getCrop();

    public void setCrop(boolean var1);

    public String getValue();

    public void setValue(String var1);
}

