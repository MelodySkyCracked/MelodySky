/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsICommandLine;
import org.mozilla.interfaces.nsISupports;

public interface nsICommandLineHandler
extends nsISupports {
    public static final String NS_ICOMMANDLINEHANDLER_IID = "{d4b123df-51ee-48b1-a663-002180e60d3b}";

    public void handle(nsICommandLine var1);

    public String getHelpInfo();
}

