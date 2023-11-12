/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIURIFixup
extends nsISupports {
    public static final String NS_IURIFIXUP_IID = "{2efd4a40-a5e1-11d4-9589-0020183bf181}";
    public static final long FIXUP_FLAG_NONE = 0L;
    public static final long FIXUP_FLAG_ALLOW_KEYWORD_LOOKUP = 1L;
    public static final long FIXUP_FLAGS_MAKE_ALTERNATE_URI = 2L;

    public nsIURI createExposableURI(nsIURI var1);

    public nsIURI createFixupURI(String var1, long var2);
}

