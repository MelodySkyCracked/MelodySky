/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDirIndex
extends nsISupports {
    public static final String NS_IDIRINDEX_IID = "{23bbabd0-1dd2-11b2-86b7-aad68ae7d7e0}";
    public static final long TYPE_UNKNOWN = 0L;
    public static final long TYPE_DIRECTORY = 1L;
    public static final long TYPE_FILE = 2L;
    public static final long TYPE_SYMLINK = 3L;

    public long getType();

    public void setType(long var1);

    public String getContentType();

    public void setContentType(String var1);

    public String getLocation();

    public void setLocation(String var1);

    public String getDescription();

    public void setDescription(String var1);

    public long getSize();

    public void setSize(long var1);

    public double getLastModified();

    public void setLastModified(double var1);
}

