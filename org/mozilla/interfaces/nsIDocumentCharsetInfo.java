/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIAtom;
import org.mozilla.interfaces.nsISupports;

public interface nsIDocumentCharsetInfo
extends nsISupports {
    public static final String NS_IDOCUMENTCHARSETINFO_IID = "{2d40b291-01e1-11d4-9d0e-0050040007b2}";

    public nsIAtom getForcedCharset();

    public void setForcedCharset(nsIAtom var1);

    public boolean getForcedDetector();

    public void setForcedDetector(boolean var1);

    public nsIAtom getParentCharset();

    public void setParentCharset(nsIAtom var1);

    public int getParentCharsetSource();

    public void setParentCharsetSource(int var1);
}

