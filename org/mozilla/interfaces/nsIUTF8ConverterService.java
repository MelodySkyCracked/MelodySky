/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUTF8ConverterService
extends nsISupports {
    public static final String NS_IUTF8CONVERTERSERVICE_IID = "{249f52a3-2599-4b00-ba40-0481364831a2}";

    public String convertStringToUTF8(String var1, String var2, boolean var3);

    public String convertURISpecToUTF8(String var1, String var2);
}

