/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransaction;
import org.mozilla.interfaces.nsITransactionManager;

public interface nsITransactionListener
extends nsISupports {
    public static final String NS_ITRANSACTIONLISTENER_IID = "{58e330c4-7b48-11d2-98b9-00805f297d89}";

    public boolean willDo(nsITransactionManager var1, nsITransaction var2);

    public void didDo(nsITransactionManager var1, nsITransaction var2, long var3);

    public boolean willUndo(nsITransactionManager var1, nsITransaction var2);

    public void didUndo(nsITransactionManager var1, nsITransaction var2, long var3);

    public boolean willRedo(nsITransactionManager var1, nsITransaction var2);

    public void didRedo(nsITransactionManager var1, nsITransaction var2, long var3);

    public boolean willBeginBatch(nsITransactionManager var1);

    public void didBeginBatch(nsITransactionManager var1, long var2);

    public boolean willEndBatch(nsITransactionManager var1);

    public void didEndBatch(nsITransactionManager var1, long var2);

    public boolean willMerge(nsITransactionManager var1, nsITransaction var2, nsITransaction var3);

    public void didMerge(nsITransactionManager var1, nsITransaction var2, nsITransaction var3, boolean var4, long var5);
}

