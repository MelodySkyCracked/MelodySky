/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.imgIRequest;
import org.mozilla.interfaces.nsISupports;

public interface imgIDecoderObserver_MOZILLA_1_8_BRANCH
extends nsISupports {
    public static final String IMGIDECODEROBSERVER_MOZILLA_1_8_BRANCH_IID = "{d3ab9070-b5d2-410f-977d-36b1788de1e5}";

    public void onStartRequest(imgIRequest var1);

    public void onStopRequest(imgIRequest var1, boolean var2);
}

