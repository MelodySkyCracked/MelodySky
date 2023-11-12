/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsITransaction
extends nsISupports {
    public static final String NS_ITRANSACTION_IID = "{58e330c1-7b48-11d2-98b9-00805f297d89}";

    public void doTransaction();

    public void undoTransaction();

    public void redoTransaction();

    public boolean getIsTransient();

    public boolean merge(nsITransaction var1);
}

