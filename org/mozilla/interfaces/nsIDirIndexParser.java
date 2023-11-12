/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDirIndexListener;
import org.mozilla.interfaces.nsIStreamListener;

public interface nsIDirIndexParser
extends nsIStreamListener {
    public static final String NS_IDIRINDEXPARSER_IID = "{38e3066c-1dd2-11b2-9b59-8be515c1ee3f}";

    public nsIDirIndexListener getListener();

    public void setListener(nsIDirIndexListener var1);

    public String getComment();

    public String getEncoding();

    public void setEncoding(String var1);
}

