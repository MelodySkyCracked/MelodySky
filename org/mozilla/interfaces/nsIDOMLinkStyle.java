/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMStyleSheet;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLinkStyle
extends nsISupports {
    public static final String NS_IDOMLINKSTYLE_IID = "{24d89a65-f598-481e-a297-23cc02599bbd}";

    public nsIDOMStyleSheet getSheet();
}

