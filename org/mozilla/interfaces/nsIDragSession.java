/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsITransferable;

public interface nsIDragSession
extends nsISupports {
    public static final String NS_IDRAGSESSION_IID = "{cba22c53-fcce-11d2-96d4-0060b0fb9956}";

    public boolean getCanDrop();

    public void setCanDrop(boolean var1);

    public long getDragAction();

    public void setDragAction(long var1);

    public long getNumDropItems();

    public nsIDOMDocument getSourceDocument();

    public nsIDOMNode getSourceNode();

    public void getData(nsITransferable var1, long var2);

    public boolean isDataFlavorSupported(String var1);
}

