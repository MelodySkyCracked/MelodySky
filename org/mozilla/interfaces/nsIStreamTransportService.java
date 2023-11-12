/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransport;

public interface nsIStreamTransportService
extends nsISupports {
    public static final String NS_ISTREAMTRANSPORTSERVICE_IID = "{8268d474-efbf-494f-a152-e8a8616f4e52}";

    public nsITransport createInputTransport(nsIInputStream var1, long var2, long var4, boolean var6);

    public nsITransport createOutputTransport(nsIOutputStream var1, long var2, long var4, boolean var6);
}

