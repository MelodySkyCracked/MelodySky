/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEventTarget;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMEvent
extends nsISupports {
    public static final String NS_IDOMEVENT_IID = "{a66b7b80-ff46-bd97-0080-5f8ae38add32}";
    public static final int CAPTURING_PHASE = 1;
    public static final int AT_TARGET = 2;
    public static final int BUBBLING_PHASE = 3;

    public String getType();

    public nsIDOMEventTarget getTarget();

    public nsIDOMEventTarget getCurrentTarget();

    public int getEventPhase();

    public boolean getBubbles();

    public boolean getCancelable();

    public double getTimeStamp();

    public void stopPropagation();

    public void preventDefault();

    public void initEvent(String var1, boolean var2, boolean var3);
}

