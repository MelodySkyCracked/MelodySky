/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIWinAppHelper
extends nsISupports {
    public static final String NS_IWINAPPHELPER_IID = "{575249a7-de7a-4602-a997-b7ad2b3b6dab}";

    public void postUpdate(nsILocalFile var1);

    public void fixReg();
}

