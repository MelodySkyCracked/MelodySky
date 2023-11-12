/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindowInternal;
import org.mozilla.interfaces.nsIObserver;
import org.mozilla.interfaces.nsIPrompt;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebProgressListener;

public interface nsIPrintProgress
extends nsIWebProgressListener {
    public static final String NS_IPRINTPROGRESS_IID = "{7e46bc35-fb7d-4b45-ab35-82fd61015380}";

    public void openProgressDialog(nsIDOMWindowInternal var1, String var2, nsISupports var3, nsIObserver var4, boolean[] var5);

    public void closeProgressDialog(boolean var1);

    public void registerListener(nsIWebProgressListener var1);

    public void unregisterListener(nsIWebProgressListener var1);

    public void doneIniting();

    public nsIPrompt getPrompter();

    public boolean getProcessCanceledByUser();

    public void setProcessCanceledByUser(boolean var1);
}

