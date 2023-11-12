/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFastLoadFileControl;
import org.mozilla.interfaces.nsIFile;

public interface nsIFastLoadWriteControl
extends nsIFastLoadFileControl {
    public static final String NS_IFASTLOADWRITECONTROL_IID = "{2ad6e9e6-1379-4e45-a899-a54b27ff915c}";

    public void addDependency(nsIFile var1);
}

