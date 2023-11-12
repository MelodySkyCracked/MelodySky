/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIHTMLObjectResizeListener;
import org.mozilla.interfaces.nsISupports;

public interface nsIHTMLObjectResizer
extends nsISupports {
    public static final String NS_IHTMLOBJECTRESIZER_IID = "{b0338f6c-ded3-4c39-a953-56e8bae494f5}";
    public static final short eTopLeft = 0;
    public static final short eTop = 1;
    public static final short eTopRight = 2;
    public static final short eLeft = 3;
    public static final short eRight = 4;
    public static final short eBottomLeft = 5;
    public static final short eBottom = 6;
    public static final short eBottomRight = 7;

    public nsIDOMElement getResizedObject();

    public boolean getObjectResizingEnabled();

    public void setObjectResizingEnabled(boolean var1);

    public void showResizers(nsIDOMElement var1);

    public void hideResizers();

    public void refreshResizers();

    public void mouseDown(int var1, int var2, nsIDOMElement var3);

    public void mouseUp(int var1, int var2, nsIDOMElement var3);

    public void mouseMove(nsIDOMEvent var1);

    public void addObjectResizeEventListener(nsIHTMLObjectResizeListener var1);

    public void removeObjectResizeEventListener(nsIHTMLObjectResizeListener var1);
}

