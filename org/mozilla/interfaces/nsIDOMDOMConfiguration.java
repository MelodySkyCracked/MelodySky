/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIVariant;

public interface nsIDOMDOMConfiguration
extends nsISupports {
    public static final String NS_IDOMDOMCONFIGURATION_IID = "{cfb5b821-9016-4a79-9d98-87b57c3ea0c7}";

    public void setParameter(String var1, nsIVariant var2);

    public nsIVariant getParameter(String var1);

    public boolean canSetParameter(String var1, nsIVariant var2);
}

