/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface nsIContentPolicy
extends nsISupports {
    public static final String NS_ICONTENTPOLICY_IID = "{3bb1a3c8-3073-41e0-9a26-a7671955fb65}";
    public static final long TYPE_OTHER = 1L;
    public static final long TYPE_SCRIPT = 2L;
    public static final long TYPE_IMAGE = 3L;
    public static final long TYPE_STYLESHEET = 4L;
    public static final long TYPE_OBJECT = 5L;
    public static final long TYPE_DOCUMENT = 6L;
    public static final long TYPE_SUBDOCUMENT = 7L;
    public static final long TYPE_REFRESH = 8L;
    public static final short REJECT_REQUEST = -1;
    public static final short REJECT_TYPE = -2;
    public static final short REJECT_SERVER = -3;
    public static final short REJECT_OTHER = -4;
    public static final short ACCEPT = 1;

    public short shouldLoad(long var1, nsIURI var3, nsIURI var4, nsISupports var5, String var6, nsISupports var7);

    public short shouldProcess(long var1, nsIURI var3, nsIURI var4, nsISupports var5, String var6, nsISupports var7);
}

