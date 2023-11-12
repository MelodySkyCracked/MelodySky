/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;

public interface nsIContextMenuListener
extends nsISupports {
    public static final String NS_ICONTEXTMENULISTENER_IID = "{3478b6b0-3875-11d4-94ef-0020183bf181}";
    public static final long CONTEXT_NONE = 0L;
    public static final long CONTEXT_LINK = 1L;
    public static final long CONTEXT_IMAGE = 2L;
    public static final long CONTEXT_DOCUMENT = 4L;
    public static final long CONTEXT_TEXT = 8L;
    public static final long CONTEXT_INPUT = 16L;

    public void onShowContextMenu(long var1, nsIDOMEvent var3, nsIDOMNode var4);
}

