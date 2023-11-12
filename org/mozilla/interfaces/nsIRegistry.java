/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEnumerator;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIRegistry
extends nsISupports {
    public static final String NS_IREGISTRY_IID = "{5d41a440-8e37-11d2-8059-00600811a9c3}";
    public static final int None = 0;
    public static final int Users = 1;
    public static final int Common = 2;
    public static final int CurrentUser = 3;
    public static final int ApplicationComponentRegistry = 1;
    public static final int ApplicationRegistry = 2;
    public static final int ApplicationCustomRegistry = -1;
    public static final long String = 1L;
    public static final long Int32 = 2L;
    public static final long Bytes = 3L;
    public static final long File = 4L;

    public void open(nsIFile var1);

    public void openWellKnownRegistry(int var1);

    public void flush();

    public boolean isOpen();

    public long addKey(long var1, String var3);

    public long getKey(long var1, String var3);

    public void removeKey(long var1, String var3);

    public String getString(long var1, String var3);

    public void setString(long var1, String var3, String var4);

    public String getStringUTF8(long var1, String var3);

    public void setStringUTF8(long var1, String var3, String var4);

    public short[] getBytesUTF8(long var1, String var3, long[] var4);

    public void setBytesUTF8(long var1, String var3, long var4, short[] var6);

    public int getInt(long var1, String var3);

    public void setInt(long var1, String var3, int var4);

    public long getLongLong(long var1, String var3);

    public void setLongLong(long var1, String var3, long[] var4);

    public long addSubtree(long var1, String var3);

    public void removeSubtree(long var1, String var3);

    public long getSubtree(long var1, String var3);

    public long addSubtreeRaw(long var1, String var3);

    public void removeSubtreeRaw(long var1, String var3);

    public long getSubtreeRaw(long var1, String var3);

    public nsIEnumerator enumerateSubtrees(long var1);

    public nsIEnumerator enumerateAllSubtrees(long var1);

    public nsIEnumerator enumerateValues(long var1);

    public long getValueType(long var1, String var3);

    public long getValueLength(long var1, String var3);

    public void deleteValue(long var1, String var3);

    public short[] escapeKey(short[] var1, long var2, long[] var4);

    public short[] unescapeKey(short[] var1, long var2, long[] var4);

    public String getCurrentUserName();

    public void setCurrentUserName(String var1);

    public void pack();
}

