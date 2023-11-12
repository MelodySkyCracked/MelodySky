/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIURL;

public interface nsIFileURL
extends nsIURL {
    public static final String NS_IFILEURL_IID = "{d26b2e2e-1dd1-11b2-88f3-8545a7ba7949}";

    public nsIFile getFile();

    public void setFile(nsIFile var1);
}

