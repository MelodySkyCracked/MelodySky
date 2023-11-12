/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISessionRoaming
extends nsISupports {
    public static final String NS_ISESSIONROAMING_IID = "{ab62465c-494c-446e-b671-930bb98a7bc4}";

    public void beginSession();

    public void endSession();

    public boolean isRoaming();
}

