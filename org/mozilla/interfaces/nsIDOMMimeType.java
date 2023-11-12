/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMPlugin;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMMimeType
extends nsISupports {
    public static final String NS_IDOMMIMETYPE_IID = "{f6134682-f28b-11d2-8360-c90899049c3c}";

    public String getDescription();

    public nsIDOMPlugin getEnabledPlugin();

    public String getSuffixes();

    public String getType();
}

