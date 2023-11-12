/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.imgIDecoderObserver;
import org.mozilla.interfaces.imgIRequest;
import org.mozilla.interfaces.nsIChannel;
import org.mozilla.interfaces.nsILoadGroup;
import org.mozilla.interfaces.nsIStreamListener;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;

public interface imgILoader
extends nsISupports {
    public static final String IMGILOADER_IID = "{a32826ff-9e56-4425-a811-97a8dba64ff5}";

    public imgIRequest loadImage(nsIURI var1, nsIURI var2, nsIURI var3, nsILoadGroup var4, imgIDecoderObserver var5, nsISupports var6, long var7, nsISupports var9, imgIRequest var10);

    public imgIRequest loadImageWithChannel(nsIChannel var1, imgIDecoderObserver var2, nsISupports var3, nsIStreamListener[] var4);

    public boolean supportImageWithMimeType(String var1);
}

