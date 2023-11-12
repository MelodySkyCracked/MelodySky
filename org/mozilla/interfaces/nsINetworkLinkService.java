/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsINetworkLinkService
extends nsISupports {
    public static final String NS_INETWORKLINKSERVICE_IID = "{61618a52-ea91-4277-a4ab-ebe10d7b9a64}";

    public boolean getIsLinkUp();

    public boolean getLinkStatusKnown();
}

