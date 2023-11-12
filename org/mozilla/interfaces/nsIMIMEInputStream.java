/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;

public interface nsIMIMEInputStream
extends nsIInputStream {
    public static final String NS_IMIMEINPUTSTREAM_IID = "{dcbce63c-1dd1-11b2-b94d-91f6d49a3161}";

    public boolean getAddContentLength();

    public void setAddContentLength(boolean var1);

    public void addHeader(String var1, String var2);

    public void setData(nsIInputStream var1);
}

