/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsISOAPAttachments
extends nsISupports {
    public static final String NS_ISOAPATTACHMENTS_IID = "{6192dcbe-1dd2-11b2-81ad-a4597614c4ae}";

    public void getAttachment(String var1);

    public String attach();
}

