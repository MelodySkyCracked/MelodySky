/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISOAPEncoding;
import org.mozilla.interfaces.nsISOAPHeaderBlock;
import org.mozilla.interfaces.nsISOAPParameter;
import org.mozilla.interfaces.nsISupports;

public interface nsISOAPMessage
extends nsISupports {
    public static final String NS_ISOAPMESSAGE_IID = "{3970815e-1dd2-11b2-a475-db4dac6826f1}";
    public static final int VERSION_1_1 = 0;
    public static final int VERSION_1_2 = 1;
    public static final int VERSION_UNKNOWN = 65535;

    public nsIDOMDocument getMessage();

    public void setMessage(nsIDOMDocument var1);

    public nsIDOMElement getEnvelope();

    public int getVersion();

    public nsIDOMElement getHeader();

    public nsIDOMElement getBody();

    public String getMethodName();

    public String getTargetObjectURI();

    public void encode(int var1, String var2, String var3, long var4, nsISOAPHeaderBlock[] var6, long var7, nsISOAPParameter[] var9);

    public nsISOAPHeaderBlock[] getHeaderBlocks(long[] var1);

    public nsISOAPParameter[] getParameters(boolean var1, long[] var2);

    public nsISOAPEncoding getEncoding();

    public void setEncoding(nsISOAPEncoding var1);

    public String getActionURI();

    public void setActionURI(String var1);
}

