/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIPK11Token
extends nsISupports {
    public static final String NS_IPK11TOKEN_IID = "{51191434-1dd2-11b2-a17c-e49c4e99a4e3}";
    public static final int ASK_EVERY_TIME = -1;
    public static final int ASK_FIRST_TIME = 0;
    public static final int ASK_EXPIRE_TIME = 1;

    public String getTokenName();

    public String getTokenLabel();

    public String getTokenManID();

    public String getTokenHWVersion();

    public String getTokenFWVersion();

    public String getTokenSerialNumber();

    public boolean isLoggedIn();

    public void login(boolean var1);

    public void logoutSimple();

    public void logoutAndDropAuthenticatedResources();

    public void reset();

    public int getMinimumPasswordLength();

    public boolean getNeedsUserInit();

    public boolean checkPassword(String var1);

    public void initPassword(String var1);

    public void changePassword(String var1, String var2);

    public int getAskPasswordTimes();

    public int getAskPasswordTimeout();

    public void setAskPasswordDefaults(int var1, int var2);

    public boolean isHardwareToken();

    public boolean needsLogin();

    public boolean isFriendly();
}

