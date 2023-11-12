/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventQueue;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIThread;

public interface nsIEventQueueService
extends nsISupports {
    public static final String NS_IEVENTQUEUESERVICE_IID = "{a6cf90dc-15b3-11d2-932e-00805f8add32}";
    public static final int CURRENT_THREAD_EVENT_QUEUE = 0;
    public static final int UI_THREAD_EVENT_QUEUE = 1;

    public void createThreadEventQueue();

    public void createMonitoredThreadEventQueue();

    public void destroyThreadEventQueue();

    public nsIEventQueue createFromIThread(nsIThread var1, boolean var2);

    public nsIEventQueue pushThreadEventQueue();

    public void popThreadEventQueue(nsIEventQueue var1);

    public nsIEventQueue getSpecialEventQueue(int var1);
}

