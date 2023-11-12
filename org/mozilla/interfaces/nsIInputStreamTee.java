/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIOutputStream;

public interface nsIInputStreamTee
extends nsIInputStream {
    public static final String NS_IINPUTSTREAMTEE_IID = "{44e8b2c8-1ecb-4a63-8b23-3e3500c34f32}";

    public nsIInputStream getSource();

    public void setSource(nsIInputStream var1);

    public nsIOutputStream getSink();

    public void setSink(nsIOutputStream var1);
}

