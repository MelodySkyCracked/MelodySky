/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIEditorStyleSheets
extends nsISupports {
    public static final String NS_IEDITORSTYLESHEETS_IID = "{4805e682-49b9-11d3-9ce4-ed60bd6cb5bc}";

    public void replaceStyleSheet(String var1);

    public void addStyleSheet(String var1);

    public void replaceOverrideStyleSheet(String var1);

    public void addOverrideStyleSheet(String var1);

    public void removeStyleSheet(String var1);

    public void removeOverrideStyleSheet(String var1);

    public void enableStyleSheet(String var1, boolean var2);
}

