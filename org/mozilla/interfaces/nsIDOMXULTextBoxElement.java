/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMXULControlElement;

public interface nsIDOMXULTextBoxElement
extends nsIDOMXULControlElement {
    public static final String NS_IDOMXULTEXTBOXELEMENT_IID = "{71135b6c-294e-4634-a8e4-a72398f1e72a}";

    public nsIDOMNode getInputField();

    public int getTextLength();

    public int getMaxLength();

    public void setMaxLength(int var1);

    public int getSize();

    public void setSize(int var1);

    public int getSelectionStart();

    public void setSelectionStart(int var1);

    public int getSelectionEnd();

    public void setSelectionEnd(int var1);

    public String getValue();

    public void setValue(String var1);

    public String getType();

    public void setType(String var1);

    public void select();

    public void setSelectionRange(int var1, int var2);
}

