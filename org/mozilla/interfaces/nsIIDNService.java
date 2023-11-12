/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIIDNService
extends nsISupports {
    public static final String NS_IIDNSERVICE_IID = "{7b67747e-a8c4-4832-80c7-39ebb0c11f94}";

    public String convertUTF8toACE(String var1);

    public String convertACEtoUTF8(String var1);

    public boolean isACE(String var1);

    public String normalize(String var1);
}

