/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAddonUpdateCheckListener;
import org.mozilla.interfaces.nsIAddonUpdateListener;
import org.mozilla.interfaces.nsICommandLine;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIInstallLocation;
import org.mozilla.interfaces.nsIRDFDataSource;
import org.mozilla.interfaces.nsISimpleEnumerator;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdateItem;

public interface nsIExtensionManager
extends nsISupports {
    public static final String NS_IEXTENSIONMANAGER_IID = "{a3f5396c-a6e8-414a-8fbc-c8d831746328}";

    public boolean start(nsICommandLine var1);

    public boolean checkForMismatches();

    public void handleCommandLineArgs(nsICommandLine var1);

    public nsIInstallLocation getInstallLocation(String var1);

    public nsISimpleEnumerator getInstallLocations();

    public void installItemFromFile(nsIFile var1, String var2);

    public void uninstallItem(String var1);

    public void enableItem(String var1);

    public void disableItem(String var1);

    public void update(nsIUpdateItem[] var1, long var2, long var4, nsIAddonUpdateCheckListener var6);

    public nsIUpdateItem getItemForID(String var1);

    public nsIUpdateItem[] getItemList(long var1, long[] var3);

    public nsIUpdateItem[] getIncompatibleItemList(String var1, String var2, long var3, boolean var5, long[] var6);

    public nsIRDFDataSource getDatasource();

    public void addDownloads(nsIUpdateItem[] var1, long var2, boolean var4);

    public void removeDownload(String var1);

    public int addUpdateListener(nsIAddonUpdateListener var1);

    public void removeUpdateListenerAt(int var1);

    public void moveToIndexOf(String var1, String var2);
}

