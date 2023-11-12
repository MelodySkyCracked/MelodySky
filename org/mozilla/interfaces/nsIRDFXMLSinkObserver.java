/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFXMLSink;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFXMLSinkObserver
extends nsISupports {
    public static final String NS_IRDFXMLSINKOBSERVER_IID = "{eb1a5d30-ab33-11d2-8ec6-00805f29f370}";

    public void onBeginLoad(nsIRDFXMLSink var1);

    public void onInterrupt(nsIRDFXMLSink var1);

    public void onResume(nsIRDFXMLSink var1);

    public void onEndLoad(nsIRDFXMLSink var1);

    public void onError(nsIRDFXMLSink var1, long var2, String var4);
}

