/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIFile;
import org.mozilla.interfaces.nsISupports;

public interface nsIEditorLogging
extends nsISupports {
    public static final String NS_IEDITORLOGGING_IID = "{4805e681-49b9-11d3-9ce4-ed60bd6cb5bc}";

    public void startLogging(nsIFile var1);

    public void stopLogging();
}

