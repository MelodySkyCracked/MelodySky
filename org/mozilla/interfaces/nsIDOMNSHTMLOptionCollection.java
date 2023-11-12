/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSHTMLOptionCollection
extends nsISupports {
    public static final String NS_IDOMNSHTMLOPTIONCOLLECTION_IID = "{1181207b-2337-41a7-8ddf-fbe96461256f}";

    public int getSelectedIndex();

    public void setSelectedIndex(int var1);
}

