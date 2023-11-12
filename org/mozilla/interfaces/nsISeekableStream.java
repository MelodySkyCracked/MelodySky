/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISeekableStream
extends nsISupports {
    public static final String NS_ISEEKABLESTREAM_IID = "{8429d350-1040-4661-8b71-f2a6ba455980}";
    public static final int NS_SEEK_SET = 0;
    public static final int NS_SEEK_CUR = 1;
    public static final int NS_SEEK_END = 2;

    public void seek(int var1, long var2);

    public long tell();

    public void setEOF();
}

