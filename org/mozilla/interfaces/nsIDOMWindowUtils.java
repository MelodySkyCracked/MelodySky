/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface nsIDOMWindowUtils
extends nsISupports {
    public static final String NS_IDOMWINDOWUTILS_IID = "{8a157a4f-a81e-489f-baf2-bc8970d60472}";

    public int getImageAnimationMode();

    public void setImageAnimationMode(int var1);

    public String getDocumentMetadata(String var1);
}

