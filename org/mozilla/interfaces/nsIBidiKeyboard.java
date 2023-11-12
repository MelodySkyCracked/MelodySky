/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIBidiKeyboard
extends nsISupports {
    public static final String NS_IBIDIKEYBOARD_IID = "{bb961ae1-7432-11d4-b77a-00104b4119f8}";

    public void isLangRTL(boolean[] var1);

    public void setLangFromBidiLevel(short var1);
}

