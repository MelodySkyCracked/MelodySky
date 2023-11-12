/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIMIMEInfo;
import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIWebProgressListener2;

public interface nsIHelperAppLauncher
extends nsICancelable {
    public static final String NS_IHELPERAPPLAUNCHER_IID = "{99a0882d-2ff9-4659-9952-9ac531ba5592}";

    public nsIMIMEInfo getMIMEInfo();

    public nsIURI getSource();

    public String getSuggestedFileName();

    public void saveToDisk(nsIFile var1, boolean var2);

    public void launchWithApplication(nsIFile var1, boolean var2);

    public void setWebProgressListener(nsIWebProgressListener2 var1);

    public void closeProgressWindow();

    public nsIFile getTargetFile();

    public double getTimeDownloadStarted();
}

