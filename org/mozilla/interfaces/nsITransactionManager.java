/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransaction;
import org.mozilla.interfaces.nsITransactionList;
import org.mozilla.interfaces.nsITransactionListener;

public interface nsITransactionManager
extends nsISupports {
    public static final String NS_ITRANSACTIONMANAGER_IID = "{58e330c2-7b48-11d2-98b9-00805f297d89}";

    public void doTransaction(nsITransaction var1);

    public void undoTransaction();

    public void redoTransaction();

    public void clear();

    public void beginBatch();

    public void endBatch();

    public int getNumberOfUndoItems();

    public int getNumberOfRedoItems();

    public int getMaxTransactionCount();

    public void setMaxTransactionCount(int var1);

    public nsITransaction peekUndoStack();

    public nsITransaction peekRedoStack();

    public nsITransactionList getUndoList();

    public nsITransactionList getRedoList();

    public void addListener(nsITransactionListener var1);

    public void removeListener(nsITransactionListener var1);
}

