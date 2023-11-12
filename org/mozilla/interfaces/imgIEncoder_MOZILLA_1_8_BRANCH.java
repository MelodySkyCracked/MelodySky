/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;

public interface imgIEncoder_MOZILLA_1_8_BRANCH
extends nsIInputStream {
    public static final String IMGIENCODER_MOZILLA_1_8_BRANCH_IID = "{b1b0b493-3369-44e0-878d-f7c56d937680}";
    public static final long INPUT_FORMAT_RGB = 0L;
    public static final long INPUT_FORMAT_RGBA = 1L;
    public static final long INPUT_FORMAT_HOSTARGB = 2L;

    public void initFromData(short[] var1, long var2, long var4, long var6, long var8, long var10, String var12);
}

