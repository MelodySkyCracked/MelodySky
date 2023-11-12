/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIPK11Token;
import org.mozilla.interfaces.nsISupports;

public interface nsIPKCS11Slot
extends nsISupports {
    public static final String NS_IPKCS11SLOT_IID = "{c2d4f296-ee60-11d4-998b-00b0d02354a0}";
    public static final long SLOT_DISABLED = 0L;
    public static final long SLOT_NOT_PRESENT = 1L;
    public static final long SLOT_UNINITIALIZED = 2L;
    public static final long SLOT_NOT_LOGGED_IN = 3L;
    public static final long SLOT_LOGGED_IN = 4L;
    public static final long SLOT_READY = 5L;

    public String getName();

    public String getDesc();

    public String getManID();

    public String getHWVersion();

    public String getFWVersion();

    public long getStatus();

    public nsIPK11Token getToken();

    public String getTokenName();
}

