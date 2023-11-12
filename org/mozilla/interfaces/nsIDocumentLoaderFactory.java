/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIContentViewer;
import org.mozilla.interfaces.nsILoadGroup;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIDocumentLoaderFactory
extends nsISupports {
    public static final String NS_IDOCUMENTLOADERFACTORY_IID = "{df15f850-5d98-11d4-9f4d-0010a4053fd0}";

    public nsIContentViewer createInstance(String var1, nsIChannel var2, nsILoadGroup var3, String var4, nsISupports var5, nsISupports var6, nsIStreamListener[] var7);

    public nsIContentViewer createInstanceForDocument(nsISupports var1, nsISupports var2, String var3);

    public nsISupports createBlankDocument(nsILoadGroup var1);
}

