/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsITextScroll
extends nsISupports {
    public static final String NS_ITEXTSCROLL_IID = "{067b28a0-877f-11d3-af7e-00a024ffc08c}";

    public void scrollByLines(int var1);

    public void scrollByPages(int var1);
}

