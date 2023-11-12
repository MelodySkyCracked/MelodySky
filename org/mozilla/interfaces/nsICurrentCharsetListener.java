/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICurrentCharsetListener
extends nsISupports {
    public static final String NS_ICURRENTCHARSETLISTENER_IID = "{cf9428c1-df50-11d3-9d0c-0050040007b2}";

    public void setCurrentCharset(String var1);

    public void setCurrentMailCharset(String var1);

    public void setCurrentComposerCharset(String var1);
}

