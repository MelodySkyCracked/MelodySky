/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISocketTransportService;

public interface nsPISocketTransportService
extends nsISocketTransportService {
    public static final String NS_PISOCKETTRANSPORTSERVICE_IID = "{6f704e69-a5fb-11d9-8ce8-0011246ecd24}";

    public void init();

    public void shutdown();

    public boolean getAutodialEnabled();

    public void setAutodialEnabled(boolean var1);
}

