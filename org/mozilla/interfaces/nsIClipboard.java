/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIClipboardOwner;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;
import org.mozilla.interfaces.nsITransferable;

public interface nsIClipboard
extends nsISupports {
    public static final String NS_ICLIPBOARD_IID = "{8b5314ba-db01-11d2-96ce-0060b0fb9956}";
    public static final int kSelectionClipboard = 0;
    public static final int kGlobalClipboard = 1;

    public void setData(nsITransferable var1, nsIClipboardOwner var2, int var3);

    public void getData(nsITransferable var1, int var2);

    public void emptyClipboard(int var1);

    public boolean hasDataMatchingFlavors(nsISupportsArray var1, int var2);

    public boolean supportsSelectionClipboard();
}

