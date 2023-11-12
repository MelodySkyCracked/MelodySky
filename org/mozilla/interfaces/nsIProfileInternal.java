/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIProfile;

public interface nsIProfileInternal
extends nsIProfile {
    public static final String NS_IPROFILEINTERNAL_IID = "{2f977d42-5485-11d4-87e2-0010a4e75ef2}";
    public static final long LIST_ONLY_NEW = 1L;
    public static final long LIST_ONLY_OLD = 2L;
    public static final long LIST_ALL = 3L;
    public static final long LIST_FOR_IMPORT = 4L;

    public int get4xProfileCount();

    public String[] getProfileListX(long var1, long[] var3);

    public void migrateProfileInfo();

    public void migrateAllProfiles();

    public void migrateProfile(String var1);

    public void remigrateProfile(String var1);

    public void forgetCurrentProfile();

    public void createDefaultProfile();

    public nsIFile getProfileDir(String var1);

    public String getProfilePath(String var1);

    public nsILocalFile getOriginalProfileDir(String var1);

    public long getProfileLastModTime(String var1);

    public boolean getAutomigrate();

    public void setAutomigrate(boolean var1);

    public nsIFile getDefaultProfileParentDir();

    public String getFirstProfile();

    public boolean getStartWithLastUsedProfile();

    public void setStartWithLastUsedProfile(boolean var1);

    public void createNewProfileWithLocales(String var1, String var2, String var3, String var4, boolean var5);

    public boolean isCurrentProfileAvailable();
}

