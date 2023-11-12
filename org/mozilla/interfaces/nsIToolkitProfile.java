/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIProfileLock;
import org.mozilla.interfaces.nsIProfileUnlocker;
import org.mozilla.interfaces.nsISupports;

public interface nsIToolkitProfile
extends nsISupports {
    public static final String NS_ITOOLKITPROFILE_IID = "{7422b090-4a86-4407-972e-75468a625388}";

    public nsILocalFile getRootDir();

    public nsILocalFile getLocalDir();

    public String getName();

    public void setName(String var1);

    public void remove(boolean var1);

    public nsIProfileLock lock(nsIProfileUnlocker[] var1);
}

