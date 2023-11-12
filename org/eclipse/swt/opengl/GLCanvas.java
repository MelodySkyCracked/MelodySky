/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.opengl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.opengl.win32.PIXELFORMATDESCRIPTOR;
import org.eclipse.swt.internal.opengl.win32.WGL;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class GLCanvas
extends Canvas {
    long context;
    int pixelFormat;
    static final String USE_OWNDC_KEY = "org.eclipse.swt.internal.win32.useOwnDC";

    public GLCanvas(Composite composite, int n, GLData gLData) {
        super(composite, GLCanvas.checkStyle(composite, n));
        PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR;
        composite.getDisplay().setData(USE_OWNDC_KEY, false);
        if (gLData == null) {
            SWT.error(4);
        }
        PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR2 = new PIXELFORMATDESCRIPTOR();
        pIXELFORMATDESCRIPTOR2.nSize = (short)40;
        pIXELFORMATDESCRIPTOR2.nVersion = 1;
        pIXELFORMATDESCRIPTOR2.dwFlags = 36;
        pIXELFORMATDESCRIPTOR2.dwLayerMask = 0;
        pIXELFORMATDESCRIPTOR2.iPixelType = 0;
        if (gLData.doubleBuffer) {
            pIXELFORMATDESCRIPTOR = pIXELFORMATDESCRIPTOR2;
            pIXELFORMATDESCRIPTOR.dwFlags |= 1;
        }
        if (gLData.stereo) {
            pIXELFORMATDESCRIPTOR = pIXELFORMATDESCRIPTOR2;
            pIXELFORMATDESCRIPTOR.dwFlags |= 2;
        }
        pIXELFORMATDESCRIPTOR2.cRedBits = (byte)gLData.redSize;
        pIXELFORMATDESCRIPTOR2.cGreenBits = (byte)gLData.greenSize;
        pIXELFORMATDESCRIPTOR2.cBlueBits = (byte)gLData.blueSize;
        pIXELFORMATDESCRIPTOR2.cAlphaBits = (byte)gLData.alphaSize;
        pIXELFORMATDESCRIPTOR2.cDepthBits = (byte)gLData.depthSize;
        pIXELFORMATDESCRIPTOR2.cStencilBits = (byte)gLData.stencilSize;
        pIXELFORMATDESCRIPTOR2.cAccumRedBits = (byte)gLData.accumRedSize;
        pIXELFORMATDESCRIPTOR2.cAccumGreenBits = (byte)gLData.accumGreenSize;
        pIXELFORMATDESCRIPTOR2.cAccumBlueBits = (byte)gLData.accumBlueSize;
        pIXELFORMATDESCRIPTOR2.cAccumAlphaBits = (byte)gLData.accumAlphaSize;
        pIXELFORMATDESCRIPTOR2.cAccumBits = (byte)(pIXELFORMATDESCRIPTOR2.cAccumRedBits + pIXELFORMATDESCRIPTOR2.cAccumGreenBits + pIXELFORMATDESCRIPTOR2.cAccumBlueBits + pIXELFORMATDESCRIPTOR2.cAccumAlphaBits);
        long l2 = OS.GetDC(this.handle);
        this.pixelFormat = WGL.ChoosePixelFormat(l2, pIXELFORMATDESCRIPTOR2);
        if (this.pixelFormat == 0 || !WGL.SetPixelFormat(l2, this.pixelFormat, pIXELFORMATDESCRIPTOR2)) {
            OS.ReleaseDC(this.handle, l2);
            this.dispose();
            SWT.error(38);
        }
        this.context = WGL.wglCreateContext(l2);
        if (this.context == 0L) {
            OS.ReleaseDC(this.handle, l2);
            SWT.error(2);
        }
        OS.ReleaseDC(this.handle, l2);
        if (gLData.shareContext != null) {
            WGL.wglShareLists(gLData.shareContext.context, this.context);
        }
        Listener listener = this::lambda$new$0;
        this.addListener(12, listener);
    }

    static int checkStyle(Composite composite, int n) {
        if (composite != null) {
            composite.getDisplay().setData(USE_OWNDC_KEY, true);
        }
        return n;
    }

    public GLData getGLData() {
        this.checkWidget();
        GLData gLData = new GLData();
        PIXELFORMATDESCRIPTOR pIXELFORMATDESCRIPTOR = new PIXELFORMATDESCRIPTOR();
        long l2 = OS.GetDC(this.handle);
        WGL.DescribePixelFormat(l2, this.pixelFormat, 40, pIXELFORMATDESCRIPTOR);
        OS.ReleaseDC(this.handle, l2);
        gLData.doubleBuffer = (pIXELFORMATDESCRIPTOR.dwFlags & 1) != 0;
        gLData.stereo = (pIXELFORMATDESCRIPTOR.dwFlags & 2) != 0;
        gLData.redSize = pIXELFORMATDESCRIPTOR.cRedBits;
        gLData.greenSize = pIXELFORMATDESCRIPTOR.cGreenBits;
        gLData.blueSize = pIXELFORMATDESCRIPTOR.cBlueBits;
        gLData.alphaSize = pIXELFORMATDESCRIPTOR.cAlphaBits;
        gLData.depthSize = pIXELFORMATDESCRIPTOR.cDepthBits;
        gLData.stencilSize = pIXELFORMATDESCRIPTOR.cStencilBits;
        gLData.accumRedSize = pIXELFORMATDESCRIPTOR.cAccumRedBits;
        gLData.accumGreenSize = pIXELFORMATDESCRIPTOR.cAccumGreenBits;
        gLData.accumBlueSize = pIXELFORMATDESCRIPTOR.cAccumBlueBits;
        gLData.accumAlphaSize = pIXELFORMATDESCRIPTOR.cAccumAlphaBits;
        return gLData;
    }

    public boolean isCurrent() {
        this.checkWidget();
        return WGL.wglGetCurrentContext() == this.context;
    }

    public void setCurrent() {
        this.checkWidget();
        if (WGL.wglGetCurrentContext() == this.context) {
            return;
        }
        long l2 = OS.GetDC(this.handle);
        WGL.wglMakeCurrent(l2, this.context);
        OS.ReleaseDC(this.handle, l2);
    }

    public void swapBuffers() {
        this.checkWidget();
        long l2 = OS.GetDC(this.handle);
        WGL.SwapBuffers(l2);
        OS.ReleaseDC(this.handle, l2);
    }

    private void lambda$new$0(Event event) {
        switch (event.type) {
            case 12: {
                WGL.wglDeleteContext(this.context);
            }
        }
    }
}

