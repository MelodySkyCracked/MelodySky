/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIFastLoadFileControl
extends nsISupports {
    public static final String NS_IFASTLOADFILECONTROL_IID = "{8a1e2c63-af50-4147-af7e-26289dc180dd}";

    public long getChecksum();

    public void setChecksum(long var1);

    public void startMuxedDocument(nsISupports var1, String var2);

    public nsISupports selectMuxedDocument(nsISupports var1);

    public void endMuxedDocument(nsISupports var1);

    public boolean hasMuxedDocument(String var1);
}

