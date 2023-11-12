/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIStreamConverter;

public interface mozITXTToHTMLConv
extends nsIStreamConverter {
    public static final String MOZITXTTOHTMLCONV_IID = "{77c0e42a-1dd2-11b2-8ebf-edc6606f2f4b}";
    public static final long kEntities = 0L;
    public static final long kURLs = 2L;
    public static final long kGlyphSubstitution = 4L;
    public static final long kStructPhrase = 8L;

    public String scanTXT(String var1, long var2);

    public String scanHTML(String var1, long var2);

    public long citeLevelTXT(String var1, long[] var2);

    public void findURLInPlaintext(String var1, int var2, int var3, int[] var4, int[] var5);
}

