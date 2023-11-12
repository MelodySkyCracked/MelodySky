/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMHTMLOptionElement;
import org.mozilla.interfaces.nsISupports;

public interface nsISelectElement
extends nsISupports {
    public static final String NS_ISELECTELEMENT_IID = "{35bd8ed5-5f34-4126-8c4f-38ba01681836}";

    public boolean isOptionDisabled(int var1);

    public boolean setOptionsSelectedByIndex(int var1, int var2, boolean var3, boolean var4, boolean var5, boolean var6);

    public int getOptionIndex(nsIDOMHTMLOptionElement var1, int var2, boolean var3);

    public boolean getHasOptGroups();
}

