/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISecurityEventSink
extends nsISupports {
    public static final String NS_ISECURITYEVENTSINK_IID = "{a71aee68-dd38-4736-bd79-035fea1a1ec6}";

    public void onSecurityChange(nsISupports var1, long var2);
}

