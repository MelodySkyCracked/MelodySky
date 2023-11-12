/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICacheDeviceInfo
extends nsISupports {
    public static final String NS_ICACHEDEVICEINFO_IID = "{31d1c294-1dd2-11b2-be3a-c79230dca297}";

    public String getDescription();

    public String getUsageReport();

    public long getEntryCount();

    public long getTotalSize();

    public long getMaximumSize();
}

