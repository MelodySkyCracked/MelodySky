/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRDFResource;
import org.mozilla.interfaces.nsISupports;

public interface nsIRDFDelegateFactory
extends nsISupports {
    public static final String NS_IRDFDELEGATEFACTORY_IID = "{a1b89470-a124-11d3-be59-0020a6361667}";

    public nsISupports createDelegate(nsIRDFResource var1, String var2, String var3);
}

