/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdate;
import org.mozilla.interfaces.nsIXMLHttpRequest;

public interface nsIUpdateCheckListener
extends nsISupports {
    public static final String NS_IUPDATECHECKLISTENER_IID = "{8cbceb6e-8e27-46f2-8808-444c6499f836}";

    public void onProgress(nsIXMLHttpRequest var1, long var2, long var4);

    public void onCheckComplete(nsIXMLHttpRequest var1, nsIUpdate[] var2, long var3);

    public void onError(nsIXMLHttpRequest var1, nsIUpdate var2);
}

