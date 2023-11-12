/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSUIEvent
extends nsISupports {
    public static final String NS_IDOMNSUIEVENT_IID = "{a6cf90c4-15b3-11d2-932e-00805f8add32}";
    public static final int SCROLL_PAGE_UP = Short.MIN_VALUE;
    public static final int SCROLL_PAGE_DOWN = 32768;

    public boolean getPreventDefault();

    public int getLayerX();

    public int getLayerY();

    public int getPageX();

    public int getPageY();

    public long getWhich();

    public nsIDOMNode getRangeParent();

    public int getRangeOffset();

    public boolean getCancelBubble();

    public void setCancelBubble(boolean var1);

    public boolean getIsChar();
}

