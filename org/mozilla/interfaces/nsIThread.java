/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIRunnable;
import org.mozilla.interfaces.nsISupports;

public interface nsIThread
extends nsISupports {
    public static final String NS_ITHREAD_IID = "{6be5e380-6886-11d3-9382-00104ba0fd40}";
    public static final long PRIORITY_LOW = 0L;
    public static final long PRIORITY_NORMAL = 1L;
    public static final long PRIORITY_HIGH = 2L;
    public static final long PRIORITY_URGENT = 3L;
    public static final long SCOPE_LOCAL = 0L;
    public static final long SCOPE_GLOBAL = 1L;
    public static final long SCOPE_BOUND = 2L;
    public static final long STATE_JOINABLE = 0L;
    public static final long STATE_UNJOINABLE = 1L;

    public void join();

    public void interrupt();

    public long getPriority();

    public void setPriority(long var1);

    public long getScope();

    public long getState();

    public void init(nsIRunnable var1, long var2, long var4, long var6, long var8);

    public nsIThread getCurrentThread();

    public void sleep(long var1);
}

