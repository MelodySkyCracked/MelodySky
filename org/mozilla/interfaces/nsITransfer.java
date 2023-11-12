/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIMIMEInfo;
import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIWebProgressListener2;

public interface nsITransfer
extends nsIWebProgressListener2 {
    public static final String NS_ITRANSFER_IID = "{23c51569-e9a1-4a92-adeb-3723db82ef7c}";

    public void init(nsIURI var1, nsIURI var2, String var3, nsIMIMEInfo var4, double var5, nsILocalFile var7, nsICancelable var8);
}

