/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsILocalFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIRelativeFilePref
extends nsISupports {
    public static final String NS_IRELATIVEFILEPREF_IID = "{2f977d4e-5485-11d4-87e2-0010a4e75ef2}";

    public nsILocalFile getFile();

    public void setFile(nsILocalFile var1);

    public String getRelativeToKey();

    public void setRelativeToKey(String var1);
}

