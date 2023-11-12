/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDNSService;

public interface nsPIDNSService
extends nsIDNSService {
    public static final String NS_PIDNSSERVICE_IID = "{a26c5b45-7707-4412-bbc1-2462b890848d}";

    public void init();

    public void shutdown();
}

