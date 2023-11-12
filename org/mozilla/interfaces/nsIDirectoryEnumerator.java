/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIDirectoryEnumerator
extends nsISupports {
    public static final String NS_IDIRECTORYENUMERATOR_IID = "{31f7f4ae-6916-4f2d-a81e-926a4e3022ee}";

    public nsIFile getNextFile();

    public void close();
}

