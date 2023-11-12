/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsILoadGroup;
import org.mozilla.interfaces.nsISupports;

public interface nsIDocumentLoader
extends nsISupports {
    public static final String NS_IDOCUMENTLOADER_IID = "{bbe961ee-59e9-42bb-be50-0331979bb79f}";

    public void stop();

    public nsISupports getContainer();

    public nsILoadGroup getLoadGroup();

    public nsIChannel getDocumentChannel();
}

