/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.imgILoad;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface imgIDecoder
extends nsISupports {
    public static final String IMGIDECODER_IID = "{9eebf43a-1dd1-11b2-953e-f1782f4cbad3}";

    public void init(imgILoad var1);

    public void close();

    public void flush();

    public long writeFrom(nsIInputStream var1, long var2);
}

