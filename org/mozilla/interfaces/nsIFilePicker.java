/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIFileURL;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;

public interface nsIFilePicker
extends nsISupports {
    public static final String NS_IFILEPICKER_IID = "{80faf095-c807-4558-a2cc-185ed70754ea}";
    public static final short modeOpen = 0;
    public static final short modeSave = 1;
    public static final short modeGetFolder = 2;
    public static final short modeOpenMultiple = 3;
    public static final short returnOK = 0;
    public static final short returnCancel = 1;
    public static final short returnReplace = 2;
    public static final int filterAll = 1;
    public static final int filterHTML = 2;
    public static final int filterText = 4;
    public static final int filterImages = 8;
    public static final int filterXML = 16;
    public static final int filterXUL = 32;
    public static final int filterApps = 64;

    public void init(nsIDOMWindow var1, String var2, short var3);

    public void appendFilters(int var1);

    public void appendFilter(String var1, String var2);

    public String getDefaultString();

    public void setDefaultString(String var1);

    public String getDefaultExtension();

    public void setDefaultExtension(String var1);

    public int getFilterIndex();

    public void setFilterIndex(int var1);

    public nsILocalFile getDisplayDirectory();

    public void setDisplayDirectory(nsILocalFile var1);

    public nsILocalFile getFile();

    public nsIFileURL getFileURL();

    public nsISimpleEnumerator getFiles();

    public short show();
}

