/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIUploadChannel
extends nsISupports {
    public static final String NS_IUPLOADCHANNEL_IID = "{ddf633d8-e9a4-439d-ad88-de636fd9bb75}";

    public void setUploadStream(nsIInputStream var1, String var2, int var3);

    public nsIInputStream getUploadStream();
}

