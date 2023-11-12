/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.imgIContainer;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIContextMenuInfo
extends nsISupports {
    public static final String NS_ICONTEXTMENUINFO_IID = "{2f977d56-5485-11d4-87e2-0010a4e75ef2}";

    public nsIDOMEvent getMouseEvent();

    public nsIDOMNode getTargetNode();

    public String getAssociatedLink();

    public imgIContainer getImageContainer();

    public nsIURI getImageSrc();

    public imgIContainer getBackgroundImageContainer();

    public nsIURI getBackgroundImageSrc();
}

