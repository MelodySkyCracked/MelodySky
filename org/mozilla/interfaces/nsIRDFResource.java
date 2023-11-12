/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFResource
extends nsIRDFNode {
    public static final String NS_IRDFRESOURCE_IID = "{fb9686a7-719a-49dc-9107-10dea5739341}";

    public String getValue();

    public String getValueUTF8();

    public void init(String var1);

    public boolean equalsString(String var1);

    public nsISupports getDelegate(String var1, String var2);

    public void releaseDelegate(String var1);
}

