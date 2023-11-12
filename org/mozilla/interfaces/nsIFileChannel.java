/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIFile;

public interface nsIFileChannel
extends nsIChannel {
    public static final String NS_IFILECHANNEL_IID = "{68a26506-f947-11d3-8cda-0060b0fc14a3}";

    public nsIFile getFile();
}

