/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMLSInput
extends nsISupports {
    public static final String NS_IDOMLSINPUT_IID = "{165e7f61-5048-4c2c-b4bf-6b44bb617ee4}";

    public nsISupports getCharacterStream();

    public void setCharacterStream(nsISupports var1);

    public nsIInputStream getByteStream();

    public void setByteStream(nsIInputStream var1);

    public String getStringData();

    public void setStringData(String var1);

    public String getSystemId();

    public void setSystemId(String var1);

    public String getPublicId();

    public void setPublicId(String var1);

    public String getBaseURI();

    public void setBaseURI(String var1);

    public String getEncoding();

    public void setEncoding(String var1);

    public boolean getCertifiedText();

    public void setCertifiedText(boolean var1);
}

