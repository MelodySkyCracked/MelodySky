/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUpdateItem
extends nsISupports {
    public static final String NS_IUPDATEITEM_IID = "{7f952767-427f-402b-8114-f80c95d1980d}";
    public static final long TYPE_APP = 1L;
    public static final long TYPE_EXTENSION = 2L;
    public static final long TYPE_THEME = 4L;
    public static final long TYPE_LOCALE = 8L;
    public static final long TYPE_PLUGIN = 16L;
    public static final long TYPE_MULTI_XPI = 32L;
    public static final long TYPE_ADDON = 30L;
    public static final long TYPE_ANY = 31L;

    public String getId();

    public String getVersion();

    public String getMinAppVersion();

    public String getMaxAppVersion();

    public String getInstallLocationKey();

    public String getName();

    public String getXpiURL();

    public String getXpiHash();

    public String getIconURL();

    public String getUpdateRDF();

    public int getType();

    public void init(String var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9, String var10, int var11);

    public String getObjectSource();
}

