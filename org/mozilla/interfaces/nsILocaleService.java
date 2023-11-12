/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsILocale;
import org.mozilla.interfaces.nsILocaleDefinition;
import org.mozilla.interfaces.nsISupports;

public interface nsILocaleService
extends nsISupports {
    public static final String NS_ILOCALESERVICE_IID = "{48ab1fa0-4550-11d3-91cd-00105aa3f7dc}";

    public nsILocale newLocale(String var1);

    public nsILocale newLocaleObject(nsILocaleDefinition var1);

    public nsILocale getSystemLocale();

    public nsILocale getApplicationLocale();

    public nsILocale getLocaleFromAcceptLanguage(String var1);

    public String getLocaleComponentForUserAgent();
}

