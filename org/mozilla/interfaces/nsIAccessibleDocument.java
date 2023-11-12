/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAccessible;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIAccessibleDocument
extends nsISupports {
    public static final String NS_IACCESSIBLEDOCUMENT_IID = "{8781fc88-355f-4439-881f-6504a0a1ceb6}";

    public String getURL();

    public String getTitle();

    public String getMimeType();

    public String getDocType();

    public boolean getIsEditable();

    public nsIDOMDocument getDocument();

    public nsIDOMWindow getWindow();

    public nsIAccessible getCaretAccessible();

    public String getNameSpaceURIForID(short var1);

    public nsIAccessible getAccessibleInParentChain(nsIDOMNode var1);
}

