/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIFastLoadFileIO
extends nsISupports {
    public static final String NS_IFASTLOADFILEIO_IID = "{715577db-d9c5-464a-a32e-0a40c29b22d4}";

    public nsIInputStream getInputStream();

    public nsIOutputStream getOutputStream();
}

