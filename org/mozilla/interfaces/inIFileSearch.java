/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.inISearchProcess;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupportsArray;

public interface inIFileSearch
extends inISearchProcess {
    public static final String INIFILESEARCH_IID = "{efa53257-526d-4350-9088-343a510346b8}";

    public String getBasePath();

    public void setBasePath(String var1);

    public boolean getReturnRelativePaths();

    public void setReturnRelativePaths(boolean var1);

    public long getDirectoryDepth(nsIFile var1);

    public nsISupportsArray getSubDirectories(nsIFile var1);

    public String getFilenameCriteria();

    public void setFilenameCriteria(String var1);

    public String getTextCriteria();

    public void setTextCriteria(String var1);

    public nsIFile getSearchPath();

    public void setSearchPath(nsIFile var1);

    public boolean getSearchRecursive();

    public void setSearchRecursive(boolean var1);

    public long getDirectoriesSearched();

    public nsIFile getCurrentDirectory();

    public nsIFile getFileResultAt(int var1);
}

