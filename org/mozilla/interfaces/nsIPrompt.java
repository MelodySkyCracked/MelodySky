/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPrompt
extends nsISupports {
    public static final String NS_IPROMPT_IID = "{a63f70c0-148b-11d3-9333-00104ba0fd40}";
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

    public void alert(String var1, String var2);

    public void alertCheck(String var1, String var2, String var3, boolean[] var4);

    public boolean confirm(String var1, String var2);

    public boolean confirmCheck(String var1, String var2, String var3, boolean[] var4);

    public int confirmEx(String var1, String var2, long var3, String var5, String var6, String var7, String var8, boolean[] var9);

    public boolean prompt(String var1, String var2, String[] var3, String var4, boolean[] var5);

    public boolean promptPassword(String var1, String var2, String[] var3, String var4, boolean[] var5);

    public boolean promptUsernameAndPassword(String var1, String var2, String[] var3, String[] var4, String var5, boolean[] var6);

    public boolean select(String var1, String var2, long var3, String[] var5, int[] var6);
}

