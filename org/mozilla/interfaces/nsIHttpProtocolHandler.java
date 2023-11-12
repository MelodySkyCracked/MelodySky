/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIProxiedProtocolHandler;

public interface nsIHttpProtocolHandler
extends nsIProxiedProtocolHandler {
    public static final String NS_IHTTPPROTOCOLHANDLER_IID = "{122c91c0-2485-40ba-89c9-b895934921bc}";

    public String getUserAgent();

    public String getAppName();

    public String getAppVersion();

    public String getVendor();

    public void setVendor(String var1);

    public String getVendorSub();

    public void setVendorSub(String var1);

    public String getVendorComment();

    public void setVendorComment(String var1);

    public String getProduct();

    public void setProduct(String var1);

    public String getProductSub();

    public void setProductSub(String var1);

    public String getProductComment();

    public void setProductComment(String var1);

    public String getPlatform();

    public String getOscpu();

    public String getLanguage();

    public void setLanguage(String var1);

    public String getMisc();

    public void setMisc(String var1);
}

