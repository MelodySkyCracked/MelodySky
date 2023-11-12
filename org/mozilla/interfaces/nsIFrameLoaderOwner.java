/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFrameLoader;
import org.mozilla.interfaces.nsISupports;

public interface nsIFrameLoaderOwner
extends nsISupports {
    public static final String NS_IFRAMELOADEROWNER_IID = "{feaf9285-05ac-4898-a69f-c3bd350767e4}";

    public nsIFrameLoader getFrameLoader();
}

