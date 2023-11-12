/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMDocumentEvent
extends nsISupports {
    public static final String NS_IDOMDOCUMENTEVENT_IID = "{46b91d66-28e2-11d4-ab1e-0010830123b4}";

    public nsIDOMEvent createEvent(String var1);
}

