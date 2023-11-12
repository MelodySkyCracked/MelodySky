/*
 * Decompiled with CFR 0.152.
 */
package org.mozilla.interfaces;

import org.mozilla.interfaces.nsISupports;

public interface gfxIImageFrame
extends nsISupports {
    public static final String GFXIIMAGEFRAME_IID = "{f6d00ee7-defc-4101-b2dc-e72cf4c37c3c}";

    public void init(int var1, int var2, int var3, int var4, int var5, int var6);

    public boolean getMutable();

    public void setMutable(boolean var1);

    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();

    public int getFormat();

    public boolean getNeedsBackground();

    public long getImageBytesPerRow();

    public long getImageDataLength();

    public void getImageData(short[][] var1, long[] var2);

    public void setImageData(short[] var1, long var2, int var4);

    public void lockImageData();

    public void unlockImageData();

    public long getAlphaBytesPerRow();

    public long getAlphaDataLength();

    public void getAlphaData(short[][] var1, long[] var2);

    public void setAlphaData(short[] var1, long var2, int var4);

    public void lockAlphaData();

    public void unlockAlphaData();

    public void drawTo(gfxIImageFrame var1, int var2, int var3, int var4, int var5);

    public int getTimeout();

    public void setTimeout(int var1);

    public int getFrameDisposalMethod();

    public void setFrameDisposalMethod(int var1);

    public long getBackgroundColor();

    public void setBackgroundColor(long var1);
}

