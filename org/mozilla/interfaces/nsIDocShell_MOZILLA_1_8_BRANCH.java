/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIDOMStorage;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIDocShell_MOZILLA_1_8_BRANCH
extends nsISupports {
    public static final String NS_IDOCSHELL_MOZILLA_1_8_BRANCH_IID = "{45988a14-b240-4d07-ae64-50ecca26e6d8}";

    public nsIDOMStorage getSessionStorageForURI(nsIURI var1);

    public void addSessionStorage(String var1, nsIDOMStorage var2);

    public nsIChannel getCurrentDocumentChannel();
}

