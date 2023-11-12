/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIHTMLObjectResizeListener
extends nsISupports {
    public static final String NS_IHTMLOBJECTRESIZELISTENER_IID = "{27b00295-349c-429f-ad0c-87b859e77130}";

    public void onStartResizing(nsIDOMElement var1);

    public void onEndResizing(nsIDOMElement var1, int var2, int var3, int var4, int var5);
}

