/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFXMLSinkObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFXMLSink
extends nsISupports {
    public static final String NS_IRDFXMLSINK_IID = "{eb1a5d31-ab33-11d2-8ec6-00805f29f370}";

    public boolean getReadOnly();

    public void setReadOnly(boolean var1);

    public void beginLoad();

    public void interrupt();

    public void resume();

    public void endLoad();

    public void addXMLSinkObserver(nsIRDFXMLSinkObserver var1);

    public void removeXMLSinkObserver(nsIRDFXMLSinkObserver var1);
}

