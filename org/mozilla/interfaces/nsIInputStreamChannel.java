/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIURI;

public interface nsIInputStreamChannel
extends nsIChannel {
    public static final String NS_IINPUTSTREAMCHANNEL_IID = "{560a64ce-6d66-44db-b38e-864469c52d03}";

    public void setURI(nsIURI var1);

    public nsIInputStream getContentStream();

    public void setContentStream(nsIInputStream var1);
}

