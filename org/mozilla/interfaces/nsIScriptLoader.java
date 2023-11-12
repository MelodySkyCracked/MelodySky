/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIScriptLoaderObserver;
import org.mozilla.interfaces.nsISupports;

public interface nsIScriptLoader
extends nsISupports {
    public static final String NS_ISCRIPTLOADER_IID = "{339a4eb5-dac6-4034-8c43-f4f8c645ce57}";

    public void init(nsISupports var1);

    public void dropDocumentReference();

    public void addObserver(nsIScriptLoaderObserver var1);

    public void removeObserver(nsIScriptLoaderObserver var1);

    public void processScriptElement(nsISupports var1, nsIScriptLoaderObserver var2);

    public nsISupports getCurrentScript();

    public boolean getEnabled();

    public void setEnabled(boolean var1);
}

