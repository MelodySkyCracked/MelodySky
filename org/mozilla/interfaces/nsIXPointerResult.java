/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMRange;
import org.mozilla.interfaces.nsISupports;

public interface nsIXPointerResult
extends nsISupports {
    public static final String NS_IXPOINTERRESULT_IID = "{d3992637-f474-4b65-83ed-323fe69c60d2}";

    public nsIDOMRange item(long var1);

    public long getLength();
}

