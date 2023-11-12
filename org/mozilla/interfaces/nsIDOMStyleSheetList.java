/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMStyleSheet;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMStyleSheetList
extends nsISupports {
    public static final String NS_IDOMSTYLESHEETLIST_IID = "{a6cf9081-15b3-11d2-932e-00805f8add32}";

    public long getLength();

    public nsIDOMStyleSheet item(long var1);
}

