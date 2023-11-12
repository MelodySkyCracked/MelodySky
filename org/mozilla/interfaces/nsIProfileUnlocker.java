/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIProfileUnlocker
extends nsISupports {
    public static final String NS_IPROFILEUNLOCKER_IID = "{08923af1-e7a3-4fae-ba02-128502193994}";
    public static final long ATTEMPT_QUIT = 0L;
    public static final long FORCE_QUIT = 1L;

    public void unlock(long var1);
}

