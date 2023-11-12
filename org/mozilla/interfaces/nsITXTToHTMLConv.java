/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIStreamConverter;

public interface nsITXTToHTMLConv
extends nsIStreamConverter {
    public static final String NS_ITXTTOHTMLCONV_IID = "{933355f6-1dd2-11b2-a9b0-d335b9e35983}";

    public void setTitle(String var1);

    public void preFormatHTML(boolean var1);
}

