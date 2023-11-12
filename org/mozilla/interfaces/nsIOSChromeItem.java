/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIOSChromeItem
extends nsISupports {
    public static final String NS_IOSCHROMEITEM_IID = "{ddd6790a-1dd1-11b2-a804-b522643903b9}";

    public String getName();

    public boolean getHidden();

    public void setHidden(boolean var1);

    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();
}

