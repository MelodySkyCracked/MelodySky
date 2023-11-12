/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICacheEntryInfo
extends nsISupports {
    public static final String NS_ICACHEENTRYINFO_IID = "{fab51c92-95c3-4468-b317-7de4d7588254}";

    public String getClientID();

    public String getDeviceID();

    public String getKey();

    public int getFetchCount();

    public long getLastFetched();

    public long getLastModified();

    public long getExpirationTime();

    public long getDataSize();

    public boolean isStreamBased();
}

