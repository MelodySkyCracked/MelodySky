/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.mozIStorageConnection;
import org.mozilla.interfaces.mozIStorageValueArray;

public interface mozIStorageStatement
extends mozIStorageValueArray {
    public static final String MOZISTORAGESTATEMENT_IID = "{1f39bc95-090d-40a5-9dee-6d5a591e48bf}";
    public static final int MOZ_STORAGE_STATEMENT_INVALID = 0;
    public static final int MOZ_STORAGE_STATEMENT_READY = 1;
    public static final int MOZ_STORAGE_STATEMENT_EXECUTING = 2;

    public void initialize(mozIStorageConnection var1, String var2);

    public mozIStorageStatement _clone();

    public long getParameterCount();

    public String getParameterName(long var1);

    public long[] getParameterIndexes(String var1, long[] var2);

    public long getColumnCount();

    public String getColumnName(long var1);

    public void reset();

    public void bindUTF8StringParameter(long var1, String var3);

    public void bindStringParameter(long var1, String var3);

    public void bindDoubleParameter(long var1, double var3);

    public void bindInt32Parameter(long var1, int var3);

    public void bindInt64Parameter(long var1, long var3);

    public void bindNullParameter(long var1);

    public void bindBlobParameter(long var1, byte[] var3, long var4);

    public void execute();

    public boolean executeStep();

    public int getState();
}

