/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIFullScreen
extends nsISupports {
    public static final String NS_IFULLSCREEN_IID = "{9854976e-1dd1-11b2-8350-e6d35099fbce}";

    public void hideAllOSChrome();

    public void showAllOSChrome();

    public nsISimpleEnumerator getChromeItems();
}

