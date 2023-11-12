/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIJSCID;
import org.mozilla.interfaces.nsIJSIID;
import org.mozilla.interfaces.nsISupports;

public interface nsIXPCConstructor
extends nsISupports {
    public static final String NS_IXPCCONSTRUCTOR_IID = "{c814ca20-e0dc-11d3-8f5f-0010a4e73d9a}";

    public nsIJSCID getClassID();

    public nsIJSIID getInterfaceID();

    public String getInitializer();
}

