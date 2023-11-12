/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsITimelineService
extends nsISupports {
    public static final String NS_ITIMELINESERVICE_IID = "{93276790-3daf-11d5-b67d-000064657374}";

    public void mark(String var1);

    public void indent();

    public void outdent();

    public void enter(String var1);

    public void leave(String var1);

    public void startTimer(String var1);

    public void stopTimer(String var1);

    public void markTimer(String var1);

    public void resetTimer(String var1);

    public void markTimerWithComment(String var1, String var2);
}

