/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface mozIStorageValueArray
extends nsISupports {
    public static final String MOZISTORAGEVALUEARRAY_IID = "{07b5b93e-113c-4150-863c-d247b003a55d}";
    public static final int VALUE_TYPE_NULL = 0;
    public static final int VALUE_TYPE_INTEGER = 1;
    public static final int VALUE_TYPE_FLOAT = 2;
    public static final int VALUE_TYPE_TEXT = 3;
    public static final int VALUE_TYPE_BLOB = 4;

    public long getNumEntries();

    public int getTypeOfIndex(long var1);

    public int getInt32(long var1);

    public long getInt64(long var1);

    public double getDouble(long var1);

    public String getUTF8String(long var1);

    public String getString(long var1);

    public void getBlob(long var1, long[] var3, byte[][] var4);

    public boolean getIsNull(long var1);
}

