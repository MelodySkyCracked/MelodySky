/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDragSession;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransferable;

public interface nsIClipboardDragDropHooks
extends nsISupports {
    public static final String NS_ICLIPBOARDDRAGDROPHOOKS_IID = "{e03e6c5e-0d84-4c0b-8739-e6b8d51922de}";

    public boolean allowStartDrag(nsIDOMEvent var1);

    public boolean allowDrop(nsIDOMEvent var1, nsIDragSession var2);

    public boolean onCopyOrDrag(nsIDOMEvent var1, nsITransferable var2);

    public boolean onPasteOrDrop(nsIDOMEvent var1, nsITransferable var2);
}

