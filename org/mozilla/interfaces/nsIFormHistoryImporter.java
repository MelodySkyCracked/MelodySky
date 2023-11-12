/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsIFormHistory2;
import org.mozilla.interfaces.nsISupports;

public interface nsIFormHistoryImporter
extends nsISupports {
    public static final String NS_IFORMHISTORYIMPORTER_IID = "{9e811188-6a5b-4d96-a92d-1bac66a41898}";

    public void importFormHistory(nsIFile var1, nsIFormHistory2 var2);
}

