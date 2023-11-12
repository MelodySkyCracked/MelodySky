/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDocumentStateListener
extends nsISupports {
    public static final String NS_IDOCUMENTSTATELISTENER_IID = "{050cdc00-3b8e-11d3-9ce4-a458f454fcbc}";

    public void notifyDocumentCreated();

    public void notifyDocumentWillBeDestroyed();

    public void notifyDocumentStateChanged(boolean var1);
}

