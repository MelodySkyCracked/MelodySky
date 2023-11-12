/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.internal.opengl.win32;

import org.eclipse.swt.internal.Library;
import org.eclipse.swt.internal.Platform;
import org.eclipse.swt.internal.opengl.win32.PIXELFORMATDESCRIPTOR;

public class WGL
extends Platform {
    public static final int PFD_TYPE_RGBA = 0;
    public static final int PFD_MAIN_PLANE = 0;
    public static final int PFD_DOUBLEBUFFER = 1;
    public static final int PFD_STEREO = 2;
    public static final int PFD_DRAW_TO_WINDOW = 4;
    public static final int PFD_SUPPORT_OPENGL = 32;

    public static final native int ChoosePixelFormat(long var0, PIXELFORMATDESCRIPTOR var2);

    public static final native int DescribePixelFormat(long var0, int var2, int var3, PIXELFORMATDESCRIPTOR var4);

    public static final native boolean SetPixelFormat(long var0, int var2, PIXELFORMATDESCRIPTOR var3);

    public static final native boolean SwapBuffers(long var0);

    public static final native long wglCreateContext(long var0);

    public static final native boolean wglDeleteContext(long var0);

    public static final native long wglGetCurrentContext();

    public static final native boolean wglMakeCurrent(long var0, long var2);

    public static final native boolean wglShareLists(long var0, long var2);

    static {
        Library.loadLibrary("swt-wgl");
    }
}

