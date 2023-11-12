/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIProfileLock
extends nsISupports {
    public static final String NS_IPROFILELOCK_IID = "{50e07b0a-f338-4da3-bcdb-f4bb0db94dbe}";

    public nsILocalFile getDirectory();

    public nsILocalFile getLocalDirectory();

    public void unlock();
}

