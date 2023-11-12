/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

public interface nsICache {
    public static final String NS_ICACHE_IID = "{ec1c0063-197d-44bb-84ba-7525d50fc937}";
    public static final int ACCESS_NONE = 0;
    public static final int ACCESS_READ = 1;
    public static final int ACCESS_WRITE = 2;
    public static final int ACCESS_READ_WRITE = 3;
    public static final int STORE_ANYWHERE = 0;
    public static final int STORE_IN_MEMORY = 1;
    public static final int STORE_ON_DISK = 2;
    public static final int STORE_ON_DISK_AS_FILE = 3;
    public static final int NOT_STREAM_BASED = 0;
    public static final int STREAM_BASED = 1;
    public static final int NON_BLOCKING = 0;
    public static final int BLOCKING = 1;
}

