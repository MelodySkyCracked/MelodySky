/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEventListener;
import org.mozilla.interfaces.nsIOnReadyStateChangeHandler;
import org.mozilla.interfaces.nsISupports;

public interface nsIJSXMLHttpRequest
extends nsISupports {
    public static final String NS_IJSXMLHTTPREQUEST_IID = "{9deabc90-28d5-41d3-a660-474f2254f4ba}";

    public nsIDOMEventListener getOnload();

    public void setOnload(nsIDOMEventListener var1);

    public nsIDOMEventListener getOnerror();

    public void setOnerror(nsIDOMEventListener var1);

    public nsIDOMEventListener getOnprogress();

    public void setOnprogress(nsIDOMEventListener var1);

    public nsIOnReadyStateChangeHandler getOnreadystatechange();

    public void setOnreadystatechange(nsIOnReadyStateChangeHandler var1);
}

