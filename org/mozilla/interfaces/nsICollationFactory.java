/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICollation;
import org.mozilla.interfaces.nsILocale;
import org.mozilla.interfaces.nsISupports;

public interface nsICollationFactory
extends nsISupports {
    public static final String NS_ICOLLATIONFACTORY_IID = "{04971e14-d6b3-4ada-8cbb-c3a13842b349}";

    public nsICollation createCollation(nsILocale var1);
}

