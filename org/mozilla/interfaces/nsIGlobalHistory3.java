/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIGlobalHistory2;

public interface nsIGlobalHistory3
extends nsIGlobalHistory2 {
    public static final String NS_IGLOBALHISTORY3_IID = "{24306852-c60e-49c3-a455-90f6747118ba}";

    public void addDocumentRedirect(nsIChannel var1, nsIChannel var2, int var3, boolean var4);
}

