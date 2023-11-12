/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIDOMNSHTMLElement
extends nsISupports {
    public static final String NS_IDOMNSHTMLELEMENT_IID = "{da83b2ec-8264-4410-8496-ada3acd2ae42}";

    public int getOffsetTop();

    public int getOffsetLeft();

    public int getOffsetWidth();

    public int getOffsetHeight();

    public nsIDOMElement getOffsetParent();

    public String getInnerHTML();

    public void setInnerHTML(String var1);

    public int getScrollTop();

    public void setScrollTop(int var1);

    public int getScrollLeft();

    public void setScrollLeft(int var1);

    public int getScrollHeight();

    public int getScrollWidth();

    public int getClientHeight();

    public int getClientWidth();

    public int getTabIndex();

    public void setTabIndex(int var1);

    public void blur();

    public void focus();

    public void scrollIntoView(boolean var1);
}

