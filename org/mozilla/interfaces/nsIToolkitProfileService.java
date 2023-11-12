/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIProfileLock;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIToolkitProfile;

public interface nsIToolkitProfileService
extends nsISupports {
    public static final String NS_ITOOLKITPROFILESERVICE_IID = "{9b434f48-438c-4f85-89de-b7f321a45341}";

    public boolean getStartWithLastProfile();

    public void setStartWithLastProfile(boolean var1);

    public boolean getStartOffline();

    public void setStartOffline(boolean var1);

    public nsISimpleEnumerator getProfiles();

    public nsIToolkitProfile getSelectedProfile();

    public void setSelectedProfile(nsIToolkitProfile var1);

    public nsIToolkitProfile getProfileByName(String var1);

    public nsIProfileLock lockProfilePath(nsILocalFile var1, nsILocalFile var2);

    public nsIToolkitProfile createProfile(nsILocalFile var1, nsILocalFile var2, String var3);

    public long getProfileCount();

    public void flush();
}

