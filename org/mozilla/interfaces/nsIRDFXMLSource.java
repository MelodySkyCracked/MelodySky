/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFXMLSource
extends nsISupports {
    public static final String NS_IRDFXMLSOURCE_IID = "{4da56f10-99fe-11d2-8ebb-00805f29f370}";

    public void serialize(nsIOutputStream var1);
}

