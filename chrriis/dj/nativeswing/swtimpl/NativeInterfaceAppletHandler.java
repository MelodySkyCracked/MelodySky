/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.lI;
import java.applet.Applet;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class NativeInterfaceAppletHandler {
    private static final Object INITIALIZATION_LOCK = new Object();
    private static boolean isInterfaceToOpen;
    private static Set activeAppletSet;

    private NativeInterfaceAppletHandler() {
    }

    public static void activateAppletMode() {
        NSSystemProperty.DEPLOYMENT_TYPE.set("applet");
        if (NativeInterface.isInProcess()) {
            AtomicBoolean atomicBoolean;
            AtomicBoolean atomicBoolean2 = atomicBoolean = new AtomicBoolean(false);
            synchronized (atomicBoolean2) {
                lI lI2 = new lI("NativeSwing event pump thread", atomicBoolean);
                lI2.setDaemon(true);
                lI2.start();
                while (!atomicBoolean.get()) {
                    try {
                        atomicBoolean.wait();
                    }
                    catch (InterruptedException interruptedException) {}
                }
            }
        } else {
            NativeInterface.initialize();
        }
    }

    public static void init(Applet applet) {
        NativeInterfaceAppletHandler.checkAppletMode();
    }

    public static void start(Applet applet) {
        NativeInterfaceAppletHandler.checkAppletMode();
        Object object = INITIALIZATION_LOCK;
        synchronized (object) {
            activeAppletSet.add(applet);
            if (isInterfaceToOpen) {
                NativeInterface.open();
            }
        }
    }

    public static void stop(Applet applet) {
        NativeInterfaceAppletHandler.checkAppletMode();
        Object object = INITIALIZATION_LOCK;
        synchronized (object) {
            activeAppletSet.remove(applet);
            if (activeAppletSet.isEmpty()) {
                isInterfaceToOpen = NativeInterface.isOpen();
                NativeInterfaceAppletHandler.stopActivity();
            }
        }
    }

    public static void destroy(Applet applet) {
        NativeInterfaceAppletHandler.checkAppletMode();
        Object object = INITIALIZATION_LOCK;
        synchronized (object) {
            activeAppletSet.remove(applet);
            if (activeAppletSet.isEmpty()) {
                isInterfaceToOpen = false;
                NativeInterfaceAppletHandler.stopActivity();
            }
        }
    }

    private static void stopActivity() {
        NativeInterface.close();
        WebServer.stopDefaultWebServer();
    }

    private static void checkAppletMode() {
        if (!"applet".equals(NSSystemProperty.DEPLOYMENT_TYPE.get())) {
            throw new IllegalStateException(NativeInterfaceAppletHandler.class.getName() + ".activateAppletMode() was not called! This code has to be placed first in the applet subclass in a static initializer.");
        }
    }

    static {
        activeAppletSet = new HashSet();
    }
}

