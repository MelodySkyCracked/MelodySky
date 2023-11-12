/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.awt;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.I;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.Library;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class SWT_AWT {
    public static String embeddedFrameClass;
    static String EMBEDDED_FRAME_KEY;
    static boolean loaded;
    static boolean swingInitialized;

    static final native long getAWTHandle(Canvas var0);

    static final native Object initFrame(long var0, String var2);

    static final native void synthesizeWindowActivation(Frame var0, boolean var1);

    static synchronized void loadLibrary() {
        if (loaded) {
            return;
        }
        loaded = true;
        Toolkit.getDefaultToolkit();
        try {
            System.loadLibrary("jawt");
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        Library.loadLibrary("swt-awt");
    }

    static synchronized void initializeSwing() {
        if (swingInitialized) {
            return;
        }
        swingInitialized = true;
        try {
            Class<?> clazz = Class.forName("javax.swing.UIManager");
            Method method = clazz.getMethod("getDefaults", new Class[0]);
            if (method != null) {
                method.invoke(clazz, new Object[0]);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    public static Frame getFrame(Composite composite) {
        if (composite == null) {
            SWT.error(4);
        }
        if ((composite.getStyle() & 0x1000000) == 0) {
            return null;
        }
        return (Frame)composite.getData(EMBEDDED_FRAME_KEY);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public static Frame new_Frame(Composite composite) {
        Frame[] frameArray;
        Object object;
        if (composite == null) {
            SWT.error(4);
        }
        if ((composite.getStyle() & 0x1000000) == 0) {
            SWT.error(5);
        }
        long l2 = composite.handle;
        Frame[] frameArray2 = new Frame[]{null};
        Throwable[] throwableArray = new Throwable[]{null};
        Runnable runnable = () -> SWT_AWT.lambda$new_Frame$0(throwableArray, l2, frameArray2);
        if (EventQueue.isDispatchThread() || composite.getDisplay().getSyncThread() != null) {
            runnable.run();
        } else {
            EventQueue.invokeLater(runnable);
            OS.ReplyMessage(0L);
            boolean bl = false;
            object = new MSG();
            int n = 0x400002;
            while (frameArray2[0] == null && throwableArray[0] == null) {
                OS.PeekMessage((MSG)object, 0L, 0, 0, 0x400002);
                try {
                    frameArray = frameArray2;
                    // MONITORENTER : frameArray2
                    frameArray2.wait(50L);
                    // MONITOREXIT : frameArray
                }
                catch (InterruptedException interruptedException) {
                    bl = true;
                }
            }
            if (bl) {
                Thread.currentThread().interrupt();
            }
        }
        if (throwableArray[0] != null) {
            SWT.error(20, throwableArray[0]);
        }
        Frame frame = frameArray2[0];
        composite.setData(EMBEDDED_FRAME_KEY, frame);
        object = arg_0 -> SWT_AWT.lambda$new_Frame$3(frame, arg_0);
        Shell shell = composite.getShell();
        shell.addListener(20, (Listener)object);
        shell.addListener(19, (Listener)object);
        frameArray = arg_0 -> SWT_AWT.lambda$new_Frame$7(composite, (Listener)object, frame, arg_0);
        composite.addListener(15, (Listener)frameArray);
        composite.addListener(27, (Listener)frameArray);
        composite.addListener(12, (Listener)frameArray);
        composite.getDisplay().asyncExec(() -> SWT_AWT.lambda$new_Frame$9(composite, frame));
        return frame;
    }

    public static Shell new_Shell(Display display, Canvas canvas) {
        if (display == null) {
            SWT.error(4);
        }
        if (canvas == null) {
            SWT.error(4);
        }
        long l2 = 0L;
        try {
            SWT_AWT.loadLibrary();
            l2 = SWT_AWT.getAWTHandle(canvas);
        }
        catch (Throwable throwable) {
            SWT.error(20, throwable);
        }
        if (l2 == 0L) {
            SWT.error(5, null, " [peer not created]");
        }
        Shell shell = Shell.win32_new(display, l2);
        I i = new I(display, shell, canvas);
        canvas.addComponentListener(i);
        shell.addListener(12, arg_0 -> SWT_AWT.lambda$new_Shell$10(canvas, i, arg_0));
        shell.setVisible(true);
        return shell;
    }

    private static void lambda$new_Shell$10(Canvas canvas, ComponentListener componentListener, Event event) {
        canvas.removeComponentListener(componentListener);
    }

    private static void lambda$new_Frame$9(Composite composite, Frame frame) {
        if (composite.isDisposed()) {
            return;
        }
        Rectangle rectangle = DPIUtil.autoScaleUp(composite.getClientArea());
        EventQueue.invokeLater(() -> SWT_AWT.lambda$null$8(frame, rectangle));
    }

    private static void lambda$null$8(Frame frame, Rectangle rectangle) {
        frame.setSize(rectangle.width, rectangle.height);
        frame.validate();
    }

    private static void lambda$new_Frame$7(Composite composite, Listener listener, Frame frame, Event event) {
        switch (event.type) {
            case 12: {
                Shell shell = composite.getShell();
                shell.removeListener(20, listener);
                shell.removeListener(19, listener);
                composite.setVisible(false);
                EventQueue.invokeLater(() -> SWT_AWT.lambda$null$4(frame));
                break;
            }
            case 15: 
            case 26: {
                EventQueue.invokeLater(() -> SWT_AWT.lambda$null$5(frame));
                break;
            }
            case 27: {
                EventQueue.invokeLater(() -> SWT_AWT.lambda$null$6(frame));
            }
        }
    }

    private static void lambda$null$6(Frame frame) {
        if (!frame.isActive()) {
            return;
        }
        SWT_AWT.synthesizeWindowActivation(frame, false);
    }

    private static void lambda$null$5(Frame frame) {
        if (frame.isActive()) {
            return;
        }
        SWT_AWT.synthesizeWindowActivation(frame, true);
    }

    private static void lambda$null$4(Frame frame) {
        try {
            frame.dispose();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static void lambda$new_Frame$3(Frame frame, Event event) {
        switch (event.type) {
            case 20: {
                EventQueue.invokeLater(() -> SWT_AWT.lambda$null$1(frame));
                break;
            }
            case 19: {
                EventQueue.invokeLater(() -> SWT_AWT.lambda$null$2(frame));
            }
        }
    }

    private static void lambda$null$2(Frame frame) {
        frame.dispatchEvent(new WindowEvent(frame, 203));
    }

    private static void lambda$null$1(Frame frame) {
        frame.dispatchEvent(new WindowEvent(frame, 204));
    }

    private static void lambda$new_Frame$0(Throwable[] throwableArray, long l2, Frame[] frameArray) {
        Frame[] frameArray2 = embeddedFrameClass != null ? embeddedFrameClass : "sun.awt.windows.WEmbeddedFrame";
        try {
            if (embeddedFrameClass != null) {
                Class.forName((String)frameArray2);
            }
            SWT_AWT.loadLibrary();
        }
        catch (ClassNotFoundException classNotFoundException) {
            SWT.error(20, classNotFoundException);
        }
        catch (Throwable throwable) {
            throwableArray[0] = throwable;
            Frame[] frameArray3 = frameArray;
            synchronized (frameArray) {
                frameArray.notify();
                // ** MonitorExit[var6_7] (shouldn't be in output)
                return;
            }
        }
        SWT_AWT.initializeSwing();
        Object object = SWT_AWT.initFrame(l2, (String)frameArray2);
        if (object == null || !(object instanceof Frame)) {
            throwableArray[0] = new Throwable("[Error while creating AWT embedded frame]");
            SWT.error(1, throwableArray[0]);
        } else {
            frameArray[0] = (Frame)object;
        }
        frameArray2 = frameArray;
        synchronized (frameArray) {
            frameArray.notify();
            // ** MonitorExit[var4_3] (shouldn't be in output)
            return;
        }
    }

    static {
        EMBEDDED_FRAME_KEY = "org.eclipse.swt.awt.SWT_AWT.embeddedFrame";
    }
}

