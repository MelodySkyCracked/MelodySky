/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICancelable;
import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsIMIMEInfo;
import org.mozilla.interfaces.nsITransfer;
import org.mozilla.interfaces.nsIURI;

public interface nsIDownload
extends nsITransfer {
    public static final String NS_IDOWNLOAD_IID = "{9e1fd9f2-9727-4926-85cd-f16c375bba6d}";

    public nsILocalFile getTargetFile();

    public int getPercentComplete();

    public double getAmountTransferred();

    public double getSize();

    public nsIURI getSource();

    public nsIURI getTarget();

    public nsICancelable getCancelable();

    public String getDisplayName();

    public long getStartTime();

    public nsIMIMEInfo getMIMEInfo();
}

