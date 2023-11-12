/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDirectoryServiceProvider;
import org.mozilla.interfaces.nsISimpleEnumerator;

public interface nsIDirectoryServiceProvider2
extends nsIDirectoryServiceProvider {
    public static final String NS_IDIRECTORYSERVICEPROVIDER2_IID = "{2f977d4b-5485-11d4-87e2-0010a4e75ef2}";

    public nsISimpleEnumerator getFiles(String var1);
}

