/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindowInternal;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWindowMediatorListener;

public interface nsIWindowMediator
extends nsISupports {
    public static final String NS_IWINDOWMEDIATOR_IID = "{0659cb81-faad-11d2-8e19-b206620a657c}";
    public static final long zLevelTop = 1L;
    public static final long zLevelBottom = 2L;
    public static final long zLevelBelow = 3L;

    public nsISimpleEnumerator getEnumerator(String var1);

    public nsISimpleEnumerator getXULWindowEnumerator(String var1);

    public nsISimpleEnumerator getZOrderDOMWindowEnumerator(String var1, boolean var2);

    public nsISimpleEnumerator getZOrderXULWindowEnumerator(String var1, boolean var2);

    public nsIDOMWindowInternal getMostRecentWindow(String var1);

    public void addListener(nsIWindowMediatorListener var1);

    public void removeListener(nsIWindowMediatorListener var1);
}

