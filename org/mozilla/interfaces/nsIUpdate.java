/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUpdatePatch;

public interface nsIUpdate
extends nsISupports {
    public static final String NS_IUPDATE_IID = "{b0fb539e-f4ab-4ea1-bd75-e6d2813e5fc1}";

    public String getType();

    public void setType(String var1);

    public String getName();

    public void setName(String var1);

    public String getVersion();

    public void setVersion(String var1);

    public String getExtensionVersion();

    public void setExtensionVersion(String var1);

    public String getBuildID();

    public void setBuildID(String var1);

    public String getDetailsURL();

    public void setDetailsURL(String var1);

    public String getLicenseURL();

    public void setLicenseURL(String var1);

    public String getServiceURL();

    public void setServiceURL(String var1);

    public boolean getIsCompleteUpdate();

    public void setIsCompleteUpdate(boolean var1);

    public boolean getIsSecurityUpdate();

    public void setIsSecurityUpdate(boolean var1);

    public long getInstallDate();

    public void setInstallDate(long var1);

    public String getStatusText();

    public void setStatusText(String var1);

    public nsIUpdatePatch getSelectedPatch();

    public String getState();

    public void setState(String var1);

    public int getErrorCode();

    public void setErrorCode(int var1);

    public long getPatchCount();

    public nsIUpdatePatch getPatchAt(long var1);

    public nsIDOMElement serialize(nsIDOMDocument var1);
}

