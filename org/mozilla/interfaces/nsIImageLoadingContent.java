/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.imgIDecoderObserver;
import org.mozilla.interfaces.imgIRequest;
import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsIURI;

public interface nsIImageLoadingContent
extends imgIDecoderObserver {
    public static final String NS_IIMAGELOADINGCONTENT_IID = "{da19c86d-08aa-421c-8c37-12ec2ba5a2c3}";
    public static final int UNKNOWN_REQUEST = -1;
    public static final int CURRENT_REQUEST = 0;
    public static final int PENDING_REQUEST = 1;

    public boolean getLoadingEnabled();

    public void setLoadingEnabled(boolean var1);

    public short getImageBlockingStatus();

    public void addObserver(imgIDecoderObserver var1);

    public void removeObserver(imgIDecoderObserver var1);

    public imgIRequest getRequest(int var1);

    public int getRequestType(imgIRequest var1);

    public nsIURI getCurrentURI();

    public nsIStreamListener loadImageWithChannel(nsIChannel var1);
}

