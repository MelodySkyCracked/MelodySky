/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMBarProp;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsISelection;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMWindow
extends nsISupports {
    public static final String NS_IDOMWINDOW_IID = "{a6cf906b-15b3-11d2-932e-00805f8add32}";

    public nsIDOMDocument getDocument();

    public nsIDOMWindow getParent();

    public nsIDOMWindow getTop();

    public nsIDOMBarProp getScrollbars();

    public String getName();

    public void setName(String var1);

    public int getScrollX();

    public int getScrollY();

    public void scrollTo(int var1, int var2);

    public void scrollBy(int var1, int var2);

    public nsISelection getSelection();

    public void scrollByLines(int var1);

    public void scrollByPages(int var1);

    public void sizeToContent();
}

