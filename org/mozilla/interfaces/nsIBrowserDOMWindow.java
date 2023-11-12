/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIBrowserDOMWindow
extends nsISupports {
    public static final String NS_IBROWSERDOMWINDOW_IID = "{af25c296-aaec-4f7f-8885-dd37a1cc0a13}";
    public static final short OPEN_DEFAULTWINDOW = 0;
    public static final short OPEN_CURRENTWINDOW = 1;
    public static final short OPEN_NEWWINDOW = 2;
    public static final short OPEN_NEWTAB = 3;
    public static final short OPEN_EXTERNAL = 1;
    public static final short OPEN_NEW = 2;

    public nsIDOMWindow openURI(nsIURI var1, nsIDOMWindow var2, short var3, short var4);

    public boolean isTabContentWindow(nsIDOMWindow var1);
}

