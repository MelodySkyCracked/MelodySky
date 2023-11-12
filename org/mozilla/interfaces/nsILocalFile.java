/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;

public interface nsILocalFile
extends nsIFile {
    public static final String NS_ILOCALFILE_IID = "{aa610f20-a889-11d3-8c81-000064657374}";

    public void initWithPath(String var1);

    public void initWithFile(nsILocalFile var1);

    public boolean getFollowLinks();

    public void setFollowLinks(boolean var1);

    public long getDiskSpaceAvailable();

    public void appendRelativePath(String var1);

    public String getPersistentDescriptor();

    public void setPersistentDescriptor(String var1);

    public void reveal();

    public void launch();

    public String getRelativeDescriptor(nsILocalFile var1);

    public void setRelativeDescriptor(nsILocalFile var1, String var2);
}

