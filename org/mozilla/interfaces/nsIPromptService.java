/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIPromptService
extends nsISupports {
    public static final String NS_IPROMPTSERVICE_IID = "{1630c61a-325e-49ca-8759-a31b16c47aa5}";
    public static final long BUTTON_POS_0 = 1L;
    public static final long BUTTON_POS_1 = 256L;
    public static final long BUTTON_POS_2 = 65536L;
    public static final long BUTTON_TITLE_OK = 1L;
    public static final long BUTTON_TITLE_CANCEL = 2L;
    public static final long BUTTON_TITLE_YES = 3L;
    public static final long BUTTON_TITLE_NO = 4L;
    public static final long BUTTON_TITLE_SAVE = 5L;
    public static final long BUTTON_TITLE_DONT_SAVE = 6L;
    public static final long BUTTON_TITLE_REVERT = 7L;
    public static final long BUTTON_TITLE_IS_STRING = 127L;
    public static final long BUTTON_POS_0_DEFAULT = 0L;
    public static final long BUTTON_POS_1_DEFAULT = 0x1000000L;
    public static final long BUTTON_POS_2_DEFAULT = 0x2000000L;
    public static final long BUTTON_DELAY_ENABLE = 0x4000000L;
    public static final long STD_OK_CANCEL_BUTTONS = 513L;
    public static final long STD_YES_NO_BUTTONS = 1027L;

    public void alert(nsIDOMWindow var1, String var2, String var3);

    public void alertCheck(nsIDOMWindow var1, String var2, String var3, String var4, boolean[] var5);

    public boolean confirm(nsIDOMWindow var1, String var2, String var3);

    public boolean confirmCheck(nsIDOMWindow var1, String var2, String var3, String var4, boolean[] var5);

    public int confirmEx(nsIDOMWindow var1, String var2, String var3, long var4, String var6, String var7, String var8, String var9, boolean[] var10);

    public boolean prompt(nsIDOMWindow var1, String var2, String var3, String[] var4, String var5, boolean[] var6);

    public boolean promptUsernameAndPassword(nsIDOMWindow var1, String var2, String var3, String[] var4, String[] var5, String var6, boolean[] var7);

    public boolean promptPassword(nsIDOMWindow var1, String var2, String var3, String[] var4, String var5, boolean[] var6);

    public boolean select(nsIDOMWindow var1, String var2, String var3, long var4, String[] var6, int[] var7);
}

