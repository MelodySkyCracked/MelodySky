/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsICommandHandler
extends nsISupports {
    public static final String NS_ICOMMANDHANDLER_IID = "{34a4fcf0-66fc-11d4-9528-0020183bf181}";

    public String exec(String var1, String var2);

    public String query(String var1, String var2);
}

