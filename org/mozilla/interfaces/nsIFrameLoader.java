/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIDocShell;
import org.mozilla.interfaces.nsISupports;

public interface nsIFrameLoader
extends nsISupports {
    public static final String NS_IFRAMELOADER_IID = "{88800e93-c6af-4d69-9ee0-29c1100ff431}";

    public nsIDocShell getDocShell();

    public void loadFrame();

    public void destroy();

    public boolean getDepthTooGreat();
}

