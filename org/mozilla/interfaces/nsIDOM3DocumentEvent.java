/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEventGroup;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOM3DocumentEvent
extends nsISupports {
    public static final String NS_IDOM3DOCUMENTEVENT_IID = "{090ecc19-b7cb-4f47-ae47-ed68d4926249}";

    public nsIDOMEventGroup createEventGroup();
}

