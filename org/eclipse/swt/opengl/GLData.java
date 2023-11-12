/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.opengl;

import org.eclipse.swt.opengl.GLCanvas;

public class GLData {
    public boolean doubleBuffer;
    public boolean stereo;
    public int redSize;
    public int greenSize;
    public int blueSize;
    public int alphaSize;
    public int depthSize;
    public int stencilSize;
    public int accumRedSize;
    public int accumGreenSize;
    public int accumBlueSize;
    public int accumAlphaSize;
    public int sampleBuffers;
    public int samples;
    public GLCanvas shareContext;

    public String toString() {
        return (this.doubleBuffer ? "doubleBuffer," : "") + (this.stereo ? "stereo," : "") + "r:" + this.redSize + " g:" + this.greenSize + " b:" + this.blueSize + " a:" + this.alphaSize + ",depth:" + this.depthSize + ",stencil:" + this.stencilSize + ",accum r:" + this.accumRedSize + "g:" + this.accumGreenSize + "b:" + this.accumBlueSize + "a:" + this.accumAlphaSize + ",sampleBuffers:" + this.sampleBuffers + ",samples:" + this.samples;
    }
}

