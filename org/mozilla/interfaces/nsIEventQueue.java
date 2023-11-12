/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIEventTarget;

public interface nsIEventQueue
extends nsIEventTarget {
    public static final String NS_IEVENTQUEUE_IID = "{176afb41-00a4-11d3-9f2a-00400553eef0}";

    public boolean pendingEvents();

    public void processPendingEvents();

    public void eventLoop();

    public void init(boolean var1);

    public void enterMonitor();

    public void exitMonitor();

    public boolean isQueueNative();

    public void stopAcceptingEvents();
}

