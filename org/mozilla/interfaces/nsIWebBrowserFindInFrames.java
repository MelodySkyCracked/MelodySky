/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsISupports;

public interface nsIWebBrowserFindInFrames
extends nsISupports {
    public static final String NS_IWEBBROWSERFINDINFRAMES_IID = "{e0f5d182-34bc-11d5-be5b-b760676c6ebc}";

    public nsIDOMWindow getCurrentSearchFrame();

    public void setCurrentSearchFrame(nsIDOMWindow var1);

    public nsIDOMWindow getRootSearchFrame();

    public void setRootSearchFrame(nsIDOMWindow var1);

    public boolean getSearchSubframes();

    public void setSearchSubframes(boolean var1);

    public boolean getSearchParentFrames();

    public void setSearchParentFrames(boolean var1);
}

