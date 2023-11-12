/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIExternalHelperAppService
extends nsISupports {
    public static final String NS_IEXTERNALHELPERAPPSERVICE_IID = "{0ea90cf3-2dd9-470f-8f76-f141743c5678}";

    public nsIStreamListener doContent(String var1, nsIRequest var2, nsIInterfaceRequestor var3);

    public boolean applyDecodingForExtension(String var1, String var2);
}

