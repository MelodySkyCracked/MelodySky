/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsISupportsArray;

public interface nsIEditorMailSupport
extends nsISupports {
    public static final String NS_IEDITORMAILSUPPORT_IID = "{fdf23301-4a94-11d3-9ce4-9960496c41bc}";

    public void pasteAsQuotation(int var1);

    public nsIDOMNode insertAsQuotation(String var1);

    public void insertTextWithQuotations(String var1);

    public void pasteAsCitedQuotation(String var1, int var2);

    public nsIDOMNode insertAsCitedQuotation(String var1, String var2, boolean var3);

    public void rewrap(boolean var1);

    public void stripCites();

    public nsISupportsArray getEmbeddedObjects();
}

