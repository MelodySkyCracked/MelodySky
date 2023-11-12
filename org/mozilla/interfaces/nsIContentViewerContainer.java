/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIContentViewer;
import org.mozilla.interfaces.nsISupports;

public interface nsIContentViewerContainer
extends nsISupports {
    public static final String NS_ICONTENTVIEWERCONTAINER_IID = "{ea2ce7a0-5c3d-11d4-90c2-0050041caf44}";

    public void embed(nsIContentViewer var1, String var2, nsISupports var3);

    public void setIsPrinting(boolean var1);
}

