/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIClassInfo
extends nsISupports {
    public static final String NS_ICLASSINFO_IID = "{986c11d0-f340-11d4-9075-0010a4e73d9a}";
    public static final long SINGLETON = 1L;
    public static final long THREADSAFE = 2L;
    public static final long MAIN_THREAD_ONLY = 4L;
    public static final long DOM_OBJECT = 8L;
    public static final long PLUGIN_OBJECT = 16L;
    public static final long EAGER_CLASSINFO = 32L;
    public static final long CONTENT_NODE = 64L;
    public static final long RESERVED = 0x80000000L;

    public String[] getInterfaces(long[] var1);

    public nsISupports getHelperForLanguage(long var1);

    public String getContractID();

    public String getClassDescription();

    public String getClassID();

    public long getImplementationLanguage();

    public long getFlags();
}

