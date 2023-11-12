/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMXULDescriptionElement;

public interface nsIDOMXULLabelElement
extends nsIDOMXULDescriptionElement {
    public static final String NS_IDOMXULLABELELEMENT_IID = "{c987629e-6370-45f5-86ec-aa765fa861cd}";

    public String getAccessKey();

    public void setAccessKey(String var1);

    public String getControl();

    public void setControl(String var1);
}

