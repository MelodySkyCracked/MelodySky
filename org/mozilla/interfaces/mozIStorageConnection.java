/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.mozIStorageFunction;
import org.mozilla.interfaces.mozIStorageStatement;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface mozIStorageConnection
extends nsISupports {
    public static final String MOZISTORAGECONNECTION_IID = "{77015f88-bfc2-4669-b1c3-cc19fb07cd4e}";
    public static final int TRANSACTION_DEFERRED = 0;
    public static final int TRANSACTION_IMMEDIATE = 1;
    public static final int TRANSACTION_EXCLUSIVE = 2;

    public boolean getConnectionReady();

    public nsIFile getDatabaseFile();

    public long getLastInsertRowID();

    public int getLastError();

    public String getLastErrorString();

    public mozIStorageStatement createStatement(String var1);

    public void executeSimpleSQL(String var1);

    public boolean tableExists(String var1);

    public boolean indexExists(String var1);

    public boolean getTransactionInProgress();

    public void beginTransaction();

    public void beginTransactionAs(int var1);

    public void commitTransaction();

    public void rollbackTransaction();

    public void createTable(String var1, String var2);

    public void createFunction(String var1, int var2, mozIStorageFunction var3);

    public void preload();
}

