/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIRDFPropagatableDataSource
extends nsISupports {
    public static final String NS_IRDFPROPAGATABLEDATASOURCE_IID = "{5a9b4770-9fcb-4307-a12e-4b6708e78b97}";

    public boolean getPropagateChanges();

    public void setPropagateChanges(boolean var1);
}

