/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.mozIStorageStatement;
import org.mozilla.interfaces.mozIStorageStatementParams;
import org.mozilla.interfaces.mozIStorageStatementRow;
import org.mozilla.interfaces.nsISupports;

public interface mozIStorageStatementWrapper
extends nsISupports {
    public static final String MOZISTORAGESTATEMENTWRAPPER_IID = "{eee6f7c9-5586-4eaf-b35c-dca987c4ffd1}";

    public void initialize(mozIStorageStatement var1);

    public mozIStorageStatement getStatement();

    public void reset();

    public boolean step();

    public void execute();

    public mozIStorageStatementRow getRow();

    public mozIStorageStatementParams getParams();
}

