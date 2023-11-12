/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICacheEntryInfo;
import org.mozilla.interfaces.nsICacheMetaDataVisitor;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIInputStream;
import org.mozilla.interfaces.nsIOutputStream;
import org.mozilla.interfaces.nsISupports;

public interface nsICacheEntryDescriptor
extends nsICacheEntryInfo {
    public static final String NS_ICACHEENTRYDESCRIPTOR_IID = "{49c1a11d-f5d2-4f09-8262-551e64908ada}";

    public void setExpirationTime(long var1);

    public void setDataSize(long var1);

    public nsIInputStream openInputStream(long var1);

    public nsIOutputStream openOutputStream(long var1);

    public nsISupports getCacheElement();

    public void setCacheElement(nsISupports var1);

    public int getAccessGranted();

    public int getStoragePolicy();

    public void setStoragePolicy(int var1);

    public nsIFile getFile();

    public nsISupports getSecurityInfo();

    public void setSecurityInfo(nsISupports var1);

    public void doom();

    public void doomAndFailPendingRequests(long var1);

    public void markValid();

    public void close();

    public String getMetaDataElement(String var1);

    public void setMetaDataElement(String var1, String var2);

    public void visitMetaData(nsICacheMetaDataVisitor var1);
}

