/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLSOutput
extends nsISupports {
    public static final String NS_IDOMLSOUTPUT_IID = "{757e9971-8890-478d-a53a-07f9f6f6e0d3}";

    public nsISupports getCharacterStream();

    public void setCharacterStream(nsISupports var1);

    public nsIOutputStream getByteStream();

    public void setByteStream(nsIOutputStream var1);

    public String getSystemId();

    public void setSystemId(String var1);

    public String getEncoding();

    public void setEncoding(String var1);
}

