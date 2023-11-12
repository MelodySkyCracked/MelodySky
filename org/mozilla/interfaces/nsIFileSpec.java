/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsIFileSpec
extends nsISupports {
    public static final String NS_IFILESPEC_IID = "{37ef2e71-edef-46c7-acd9-f0b6e0b15083}";

    public void fromFileSpec(nsIFileSpec var1);

    public String getURLString();

    public void setURLString(String var1);

    public String getUnixStyleFilePath();

    public void setUnixStyleFilePath(String var1);

    public String getPersistentDescriptorString();

    public void setPersistentDescriptorString(String var1);

    public String getNativePath();

    public void setNativePath(String var1);

    public String getNSPRPath();

    public void error();

    public boolean isValid();

    public boolean failed();

    public String getLeafName();

    public void setLeafName(String var1);

    public nsIFileSpec getParent();

    public nsIInputStream getInputStream();

    public nsIOutputStream getOutputStream();

    public boolean isChildOf(nsIFileSpec var1);

    public String getFileContents();

    public void setFileContents(String var1);

    public void makeUnique();

    public void makeUniqueWithSuggestedName(String var1);

    public void makeUniqueDir();

    public void makeUniqueDirWithSuggestedName(String var1);

    public long getModDate();

    public boolean modDateChanged(long var1);

    public boolean isDirectory();

    public boolean isFile();

    public boolean exists();

    public boolean isHidden();

    public boolean _equals(nsIFileSpec var1);

    public long getFileSize();

    public long getDiskSpaceAvailable();

    public void appendRelativeUnixPath(String var1);

    public void createDir();

    public void touch();

    public boolean isSymlink();

    public void resolveSymlink();

    public void delete(boolean var1);

    public void truncate(int var1);

    public void rename(String var1);

    public void copyToDir(nsIFileSpec var1);

    public void moveToDir(nsIFileSpec var1);

    public void execute(String var1);

    public void openStreamForReading();

    public void openStreamForWriting();

    public void openStreamForReadingAndWriting();

    public void closeStream();

    public boolean isStreamOpen();

    public boolean eof();

    public int read(String[] var1, int var2);

    public void readLine(String[] var1, int var2, boolean[] var3);

    public int write(String var1, int var2);

    public void flush();

    public void seek(int var1);

    public int tell();

    public void endLine();

    public String getUnicodePath();

    public void setUnicodePath(String var1);
}

