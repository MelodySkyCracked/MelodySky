/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIBoxLayoutManager;
import org.mozilla.interfaces.nsIBoxPaintManager;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIBoxObject
extends nsISupports {
    public static final String NS_IBOXOBJECT_IID = "{caabf76f-9d35-401f-beac-3955817c645c}";

    public nsIDOMElement getElement();

    public nsIBoxLayoutManager getLayoutManager();

    public void setLayoutManager(nsIBoxLayoutManager var1);

    public nsIBoxPaintManager getPaintManager();

    public void setPaintManager(nsIBoxPaintManager var1);

    public int getX();

    public int getY();

    public int getScreenX();

    public int getScreenY();

    public int getWidth();

    public int getHeight();

    public nsISupports getPropertyAsSupports(String var1);

    public void setPropertyAsSupports(String var1, nsISupports var2);

    public String getProperty(String var1);

    public void setProperty(String var1, String var2);

    public void removeProperty(String var1);

    public nsIDOMElement getParentBox();

    public nsIDOMElement getFirstChild();

    public nsIDOMElement getLastChild();

    public nsIDOMElement getNextSibling();

    public nsIDOMElement getPreviousSibling();

    public String getLookAndFeelMetric(String var1);
}

