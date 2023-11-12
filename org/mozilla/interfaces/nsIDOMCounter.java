/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMCounter
extends nsISupports {
    public static final String NS_IDOMCOUNTER_IID = "{31adb439-0055-402d-9b1d-d5ca94f3f55b}";

    public String getIdentifier();

    public String getListStyle();

    public String getSeparator();
}

