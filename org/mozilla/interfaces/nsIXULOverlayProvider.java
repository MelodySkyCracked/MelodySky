/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIXULOverlayProvider
extends nsISupports {
    public static final String NS_IXULOVERLAYPROVIDER_IID = "{1d5b5b94-dc47-4050-93b7-ac092e383cad}";

    public nsISimpleEnumerator getXULOverlays(nsIURI var1);

    public nsISimpleEnumerator getStyleOverlays(nsIURI var1);
}

