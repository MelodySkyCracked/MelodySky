/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebServiceErrorHandler;

public interface nsISchemaComponent
extends nsISupports {
    public static final String NS_ISCHEMACOMPONENT_IID = "{5caaa64e-e191-11d8-842a-000393b6661a}";

    public String getTargetNamespace();

    public void resolve(nsIWebServiceErrorHandler var1);

    public void clear();
}

