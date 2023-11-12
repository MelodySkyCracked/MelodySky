/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIClipboardDragDropHooks;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIClipboardDragDropHookList
extends nsISupports {
    public static final String NS_ICLIPBOARDDRAGDROPHOOKLIST_IID = "{876a2015-6b66-11d7-8f18-0003938a9d96}";

    public void addClipboardDragDropHooks(nsIClipboardDragDropHooks var1);

    public void removeClipboardDragDropHooks(nsIClipboardDragDropHooks var1);

    public nsISimpleEnumerator getHookEnumerator();
}

