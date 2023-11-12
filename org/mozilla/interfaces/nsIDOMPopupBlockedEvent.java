/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIURI;

public interface nsIDOMPopupBlockedEvent
extends nsIDOMEvent {
    public static final String NS_IDOMPOPUPBLOCKEDEVENT_IID = "{9e201104-78e9-4cb3-aff5-7f0a9cf446c0}";

    public nsIURI getRequestingWindowURI();

    public nsIURI getPopupWindowURI();

    public String getPopupWindowFeatures();

    public void initPopupBlockedEvent(String var1, boolean var2, boolean var3, nsIURI var4, nsIURI var5, String var6);
}

