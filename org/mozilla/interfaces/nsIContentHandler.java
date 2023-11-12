/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;

public interface nsIContentHandler
extends nsISupports {
    public static final String NS_ICONTENTHANDLER_IID = "{49439df2-b3d2-441c-bf62-866bdaf56fd2}";

    public void handleContent(String var1, nsIInterfaceRequestor var2, nsIRequest var3);
}

