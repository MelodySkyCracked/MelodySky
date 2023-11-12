/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIMIMEHeaderParam
extends nsISupports {
    public static final String NS_IMIMEHEADERPARAM_IID = "{ddbbdfb8-a1c0-4dd5-a31b-5d2a7a3bb6ec}";

    public String getParameter(String var1, String var2, String var3, boolean var4, String[] var5);
}

