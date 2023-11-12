/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransferable;

public interface nsIClipboardOwner
extends nsISupports {
    public static final String NS_ICLIPBOARDOWNER_IID = "{5a31c7a1-e122-11d2-9a57-000064657374}";

    public void losingOwnership(nsITransferable var1);
}

