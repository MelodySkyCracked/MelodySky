/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFastLoadFileControl;
import org.mozilla.interfaces.nsISimpleEnumerator;

public interface nsIFastLoadReadControl
extends nsIFastLoadFileControl {
    public static final String NS_IFASTLOADREADCONTROL_IID = "{652ecec6-d40b-45b6-afef-641d6c63a35b}";

    public long computeChecksum();

    public nsISimpleEnumerator getDependencies();
}

