/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIControllers;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSHTMLInputElement
extends nsISupports {
    public static final String NS_IDOMNSHTMLINPUTELEMENT_IID = "{993d2efc-a768-11d3-bccd-0060b0fc76bd}";

    public nsIControllers getControllers();

    public int getTextLength();

    public int getSelectionStart();

    public void setSelectionStart(int var1);

    public int getSelectionEnd();

    public void setSelectionEnd(int var1);

    public void setSelectionRange(int var1, int var2);
}

