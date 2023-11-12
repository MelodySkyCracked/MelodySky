/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIWebPageDescriptor
extends nsISupports {
    public static final String NS_IWEBPAGEDESCRIPTOR_IID = "{6f30b676-3710-4c2c-80b1-0395fb26516e}";
    public static final long DISPLAY_AS_SOURCE = 1L;
    public static final long DISPLAY_NORMAL = 2L;

    public void loadPage(nsISupports var1, long var2);

    public nsISupports getCurrentDescriptor();
}

