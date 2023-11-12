/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIStreamListener;

public interface nsISyncStreamListener
extends nsIStreamListener {
    public static final String NS_ISYNCSTREAMLISTENER_IID = "{7e1aa658-6e3f-4521-9946-9685a169f764}";

    public nsIInputStream getInputStream();
}

