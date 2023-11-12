/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUTF8StringEnumerator;

public interface nsIMIMEInfo
extends nsISupports {
    public static final String NS_IMIMEINFO_IID = "{1448b42f-cf0d-466e-9a15-64e876ebe857}";
    public static final int saveToDisk = 0;
    public static final int alwaysAsk = 1;
    public static final int useHelperApp = 2;
    public static final int handleInternally = 3;
    public static final int useSystemDefault = 4;

    public nsIUTF8StringEnumerator getFileExtensions();

    public void setFileExtensions(String var1);

    public boolean extensionExists(String var1);

    public void appendExtension(String var1);

    public String getPrimaryExtension();

    public void setPrimaryExtension(String var1);

    public String getMIMEType();

    public String getDescription();

    public void setDescription(String var1);

    public long getMacType();

    public void setMacType(long var1);

    public long getMacCreator();

    public void setMacCreator(long var1);

    public boolean _equals(nsIMIMEInfo var1);

    public nsIFile getPreferredApplicationHandler();

    public void setPreferredApplicationHandler(nsIFile var1);

    public String getApplicationDescription();

    public void setApplicationDescription(String var1);

    public boolean getHasDefaultHandler();

    public String getDefaultDescription();

    public void launchWithFile(nsIFile var1);

    public int getPreferredAction();

    public void setPreferredAction(int var1);

    public boolean getAlwaysAskBeforeHandling();

    public void setAlwaysAskBeforeHandling(boolean var1);
}

