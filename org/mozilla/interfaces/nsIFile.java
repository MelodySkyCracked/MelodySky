/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIFile
extends nsISupports {
    public static final String NS_IFILE_IID = "{c8c0a080-0868-11d3-915f-d9d889d48e3c}";
    public static final long NORMAL_FILE_TYPE = 0L;
    public static final long DIRECTORY_TYPE = 1L;

    public void append(String var1);

    public void normalize();

    public void create(long var1, long var3);

    public String getLeafName();

    public void setLeafName(String var1);

    public void copyTo(nsIFile var1, String var2);

    public void copyToFollowingLinks(nsIFile var1, String var2);

    public void moveTo(nsIFile var1, String var2);

    public void remove(boolean var1);

    public long getPermissions();

    public void setPermissions(long var1);

    public long getPermissionsOfLink();

    public void setPermissionsOfLink(long var1);

    public long getLastModifiedTime();

    public void setLastModifiedTime(long var1);

    public long getLastModifiedTimeOfLink();

    public void setLastModifiedTimeOfLink(long var1);

    public long getFileSize();

    public void setFileSize(long var1);

    public long getFileSizeOfLink();

    public String getTarget();

    public String getPath();

    public boolean exists();

    public boolean isWritable();

    public boolean isReadable();

    public boolean isExecutable();

    public boolean isHidden();

    public boolean isDirectory();

    public boolean isFile();

    public boolean isSymlink();

    public boolean isSpecial();

    public void createUnique(long var1, long var3);

    public nsIFile _clone();

    public boolean _equals(nsIFile var1);

    public boolean contains(nsIFile var1, boolean var2);

    public nsIFile getParent();

    public nsISimpleEnumerator getDirectoryEntries();
}

