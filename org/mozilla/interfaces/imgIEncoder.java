/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIClipboardImage;
import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface imgIEncoder
extends nsISupports {
    public static final String IMGIENCODER_IID = "{ccc5b3ad-3e67-4e3d-97e1-b06b2e96fef8}";

    public void encodeClipboardImage(nsIClipboardImage var1, nsIFile[] var2);
}

