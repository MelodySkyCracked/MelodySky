/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMMediaList;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMStyleSheet
extends nsISupports {
    public static final String NS_IDOMSTYLESHEET_IID = "{a6cf9080-15b3-11d2-932e-00805f8add32}";

    public String getType();

    public boolean getDisabled();

    public void setDisabled(boolean var1);

    public nsIDOMNode getOwnerNode();

    public nsIDOMStyleSheet getParentStyleSheet();

    public String getHref();

    public String getTitle();

    public nsIDOMMediaList getMedia();
}

