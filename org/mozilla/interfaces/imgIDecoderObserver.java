/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.gfxIImageFrame;
import org.mozilla.interfaces.imgIContainer;
import org.mozilla.interfaces.imgIContainerObserver;
import org.mozilla.interfaces.imgIRequest;

public interface imgIDecoderObserver
extends imgIContainerObserver {
    public static final String IMGIDECODEROBSERVER_IID = "{cce7152e-4395-4231-a781-c347c5446cc2}";

    public void onStartDecode(imgIRequest var1);

    public void onStartContainer(imgIRequest var1, imgIContainer var2);

    public void onStartFrame(imgIRequest var1, gfxIImageFrame var2);

    public void onStopFrame(imgIRequest var1, gfxIImageFrame var2);

    public void onStopContainer(imgIRequest var1, imgIContainer var2);

    public void onStopDecode(imgIRequest var1, long var2, String var4);
}

