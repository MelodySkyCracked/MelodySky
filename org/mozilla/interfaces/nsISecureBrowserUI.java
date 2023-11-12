/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsISecureBrowserUI
extends nsISupports {
    public static final String NS_ISECUREBROWSERUI_IID = "{081e31e0-a144-11d3-8c7c-00609792278c}";

    public void init(nsIDOMWindow var1);

    public long getState();

    public String getTooltipText();
}

