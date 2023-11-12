/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMSerializer
extends nsISupports {
    public static final String NS_IDOMSERIALIZER_IID = "{9fd4ba15-e67c-4c98-b52c-7715f62c9196}";

    public String serializeToString(nsIDOMNode var1);

    public void serializeToStream(nsIDOMNode var1, nsIOutputStream var2, String var3);
}

