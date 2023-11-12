/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIURLParser
extends nsISupports {
    public static final String NS_IURLPARSER_IID = "{7281076d-cf37-464a-815e-698235802604}";

    public void parseURL(String var1, int var2, long[] var3, int[] var4, long[] var5, int[] var6, long[] var7, int[] var8);

    public void parseAuthority(String var1, int var2, long[] var3, int[] var4, long[] var5, int[] var6, long[] var7, int[] var8, int[] var9);

    public void parseUserInfo(String var1, int var2, long[] var3, int[] var4, long[] var5, int[] var6);

    public void parseServerInfo(String var1, int var2, long[] var3, int[] var4, int[] var5);

    public void parsePath(String var1, int var2, long[] var3, int[] var4, long[] var5, int[] var6, long[] var7, int[] var8, long[] var9, int[] var10);

    public void parseFilePath(String var1, int var2, long[] var3, int[] var4, long[] var5, int[] var6, long[] var7, int[] var8);

    public void parseFileName(String var1, int var2, long[] var3, int[] var4, long[] var5, int[] var6);
}

