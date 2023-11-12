/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMMimeTypeArray;
import org.mozilla.interfaces.nsIDOMPluginArray;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNavigator
extends nsISupports {
    public static final String NS_IDOMNAVIGATOR_IID = "{8758b72b-63d4-4685-b908-4275126410fb}";

    public String getAppCodeName();

    public String getAppName();

    public String getAppVersion();

    public String getLanguage();

    public nsIDOMMimeTypeArray getMimeTypes();

    public String getPlatform();

    public String getOscpu();

    public String getVendor();

    public String getVendorSub();

    public String getProduct();

    public String getProductSub();

    public nsIDOMPluginArray getPlugins();

    public String getSecurityPolicy();

    public String getUserAgent();

    public boolean getCookieEnabled();

    public boolean getOnLine();

    public boolean javaEnabled();

    public boolean taintEnabled();
}

