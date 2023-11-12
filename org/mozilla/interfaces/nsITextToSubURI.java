/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsITextToSubURI
extends nsISupports {
    public static final String NS_ITEXTTOSUBURI_IID = "{8b042e24-6f87-11d3-b3c8-00805f8a6670}";

    public String convertAndEscape(String var1, String var2);

    public String unEscapeAndConvert(String var1, String var2);

    public String unEscapeURIForUI(String var1, String var2);

    public String unEscapeNonAsciiURI(String var1, String var2);
}

