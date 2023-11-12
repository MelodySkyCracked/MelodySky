/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIAuthPrompt
extends nsISupports {
    public static final String NS_IAUTHPROMPT_IID = "{2f977d45-5485-11d4-87e2-0010a4e75ef2}";
    public static final long SAVE_PASSWORD_NEVER = 0L;
    public static final long SAVE_PASSWORD_FOR_SESSION = 1L;
    public static final long SAVE_PASSWORD_PERMANENTLY = 2L;

    public boolean prompt(String var1, String var2, String var3, long var4, String var6, String[] var7);

    public boolean promptUsernameAndPassword(String var1, String var2, String var3, long var4, String[] var6, String[] var7);

    public boolean promptPassword(String var1, String var2, String var3, long var4, String[] var6);
}

