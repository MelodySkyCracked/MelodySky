/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITimerCallback;

public interface nsITimer
extends nsISupports {
    public static final String NS_ITIMER_IID = "{436a83fa-b396-11d9-bcfa-00112478d626}";
    public static final short TYPE_ONE_SHOT = 0;
    public static final short TYPE_REPEATING_SLACK = 1;
    public static final short TYPE_REPEATING_PRECISE = 2;

    public void init(nsIObserver var1, long var2, long var4);

    public void initWithCallback(nsITimerCallback var1, long var2, long var4);

    public void cancel();

    public long getDelay();

    public void setDelay(long var1);

    public long getType();

    public void setType(long var1);

    public nsITimerCallback getCallback();
}

