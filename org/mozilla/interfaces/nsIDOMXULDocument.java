/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsIDOMXULCommandDispatcher;
import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMXULDocument
extends nsISupports {
    public static final String NS_IDOMXULDOCUMENT_IID = "{e64bb081-13ba-430e-ab70-73a9f1d3de58}";

    public nsIDOMNode getPopupNode();

    public void setPopupNode(nsIDOMNode var1);

    public nsIDOMNode getTooltipNode();

    public void setTooltipNode(nsIDOMNode var1);

    public nsIDOMXULCommandDispatcher getCommandDispatcher();

    public int getWidth();

    public int getHeight();

    public nsIDOMNodeList getElementsByAttribute(String var1, String var2);

    public void addBroadcastListenerFor(nsIDOMElement var1, nsIDOMElement var2, String var3);

    public void removeBroadcastListenerFor(nsIDOMElement var1, nsIDOMElement var2, String var3);

    public void persist(String var1, String var2);

    public void loadOverlay(String var1, nsIObserver var2);
}

