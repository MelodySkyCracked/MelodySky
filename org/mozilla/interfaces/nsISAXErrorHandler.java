/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISAXLocator;
import org.mozilla.interfaces.nsISupports;

public interface nsISAXErrorHandler
extends nsISupports {
    public static final String NS_ISAXERRORHANDLER_IID = "{e02b6693-6cca-11da-be43-001422106990}";

    public void error(nsISAXLocator var1, String var2);

    public void fatalError(nsISAXLocator var1, String var2);

    public void ignorableWarning(nsISAXLocator var1, String var2);
}

