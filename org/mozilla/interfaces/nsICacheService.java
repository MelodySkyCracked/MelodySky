/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICacheSession;
import org.mozilla.interfaces.nsICacheVisitor;
import org.mozilla.interfaces.nsISupports;

public interface nsICacheService
extends nsISupports {
    public static final String NS_ICACHESERVICE_IID = "{de114eb4-29fc-4959-b2f7-2d03eb9bc771}";

    public nsICacheSession createSession(String var1, int var2, boolean var3);

    public void visitEntries(nsICacheVisitor var1);

    public void evictEntries(int var1);
}

