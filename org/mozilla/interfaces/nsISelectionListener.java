/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsISelection;
import org.mozilla.interfaces.nsISupports;

public interface nsISelectionListener
extends nsISupports {
    public static final String NS_ISELECTIONLISTENER_IID = "{a6cf90e2-15b3-11d2-932e-00805f8add32}";
    public static final short NO_REASON = 0;
    public static final short DRAG_REASON = 1;
    public static final short MOUSEDOWN_REASON = 2;
    public static final short MOUSEUP_REASON = 4;
    public static final short KEYPRESS_REASON = 8;
    public static final short SELECTALL_REASON = 16;

    public void notifySelectionChanged(nsIDOMDocument var1, nsISelection var2, short var3);
}

