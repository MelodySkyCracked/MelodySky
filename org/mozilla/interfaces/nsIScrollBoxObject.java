/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;

public interface nsIScrollBoxObject
extends nsISupports {
    public static final String NS_ISCROLLBOXOBJECT_IID = "{56e2ada8-4631-11d4-ba11-001083023c1e}";

    public void scrollTo(int var1, int var2);

    public void scrollBy(int var1, int var2);

    public void scrollByLine(int var1);

    public void scrollByIndex(int var1);

    public void scrollToLine(int var1);

    public void scrollToElement(nsIDOMElement var1);

    public void scrollToIndex(int var1);

    public void getPosition(int[] var1, int[] var2);

    public void getScrolledSize(int[] var1, int[] var2);

    public void ensureElementIsVisible(nsIDOMElement var1);

    public void ensureIndexIsVisible(int var1);

    public void ensureLineIsVisible(int var1);
}

