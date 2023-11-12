/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsISupports;

public interface nsIMultiPartChannel
extends nsISupports {
    public static final String NS_IMULTIPARTCHANNEL_IID = "{ba78db7b-b88c-4b76-baf9-3c2296a585ae}";

    public nsIChannel getBaseChannel();

    public String getContentDisposition();

    public void setContentDisposition(String var1);

    public long getPartID();

    public boolean getIsLastPart();
}

