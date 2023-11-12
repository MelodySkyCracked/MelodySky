/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.gfxIImageFrame;
import org.mozilla.interfaces.imgIContainerObserver;
import org.mozilla.interfaces.nsISupports;

public interface imgIContainer
extends nsISupports {
    public static final String IMGICONTAINER_IID = "{1a6290e6-8285-4e10-963d-d001f8d327b8}";
    public static final short kNormalAnimMode = 0;
    public static final short kDontAnimMode = 1;
    public static final short kLoopOnceAnimMode = 2;

    public void init(int var1, int var2, imgIContainerObserver var3);

    public int getPreferredAlphaChannelFormat();

    public int getWidth();

    public int getHeight();

    public gfxIImageFrame getCurrentFrame();

    public long getNumFrames();

    public int getAnimationMode();

    public void setAnimationMode(int var1);

    public gfxIImageFrame getFrameAt(long var1);

    public void appendFrame(gfxIImageFrame var1);

    public void removeFrame(gfxIImageFrame var1);

    public void endFrameDecode(long var1, long var3);

    public void decodingComplete();

    public void clear();

    public void startAnimation();

    public void stopAnimation();

    public void resetAnimation();

    public int getLoopCount();

    public void setLoopCount(int var1);
}

