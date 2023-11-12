/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIWindowsRegKey
extends nsISupports {
    public static final String NS_IWINDOWSREGKEY_IID = "{2555b930-d64f-437e-9be7-0a2cb252c1f4}";
    public static final long ROOT_KEY_CLASSES_ROOT = 0x80000000L;
    public static final long ROOT_KEY_CURRENT_USER = 0x80000001L;
    public static final long ROOT_KEY_LOCAL_MACHINE = 0x80000002L;
    public static final long ACCESS_BASIC = 131072L;
    public static final long ACCESS_QUERY_VALUE = 1L;
    public static final long ACCESS_SET_VALUE = 2L;
    public static final long ACCESS_CREATE_SUB_KEY = 4L;
    public static final long ACCESS_ENUMERATE_SUB_KEYS = 8L;
    public static final long ACCESS_NOTIFY = 16L;
    public static final long ACCESS_READ = 131097L;
    public static final long ACCESS_WRITE = 131078L;
    public static final long ACCESS_ALL = 131103L;
    public static final long TYPE_NONE = 0L;
    public static final long TYPE_STRING = 1L;
    public static final long TYPE_BINARY = 3L;
    public static final long TYPE_INT = 4L;
    public static final long TYPE_INT64 = 11L;

    public void close();

    public void open(long var1, String var3, long var4);

    public void create(long var1, String var3, long var4);

    public nsIWindowsRegKey openChild(String var1, long var2);

    public nsIWindowsRegKey createChild(String var1, long var2);

    public long getChildCount();

    public String getChildName(long var1);

    public boolean hasChild(String var1);

    public long getValueCount();

    public String getValueName(long var1);

    public boolean hasValue(String var1);

    public void removeChild(String var1);

    public void removeValue(String var1);

    public long getValueType(String var1);

    public String readStringValue(String var1);

    public long readIntValue(String var1);

    public double readInt64Value(String var1);

    public String readBinaryValue(String var1);

    public void writeStringValue(String var1, String var2);

    public void writeIntValue(String var1, long var2);

    public void writeInt64Value(String var1, double var2);

    public void writeBinaryValue(String var1, String var2);

    public void startWatching(boolean var1);

    public void stopWatching();

    public boolean isWatching();

    public boolean hasChanged();
}

