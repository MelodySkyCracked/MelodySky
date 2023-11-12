/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIUUIDGenerator
extends nsISupports {
    public static final String NS_IUUIDGENERATOR_IID = "{138ad1b2-c694-41cc-b201-333ce936d8b8}";

    public String generateUUID();
}

