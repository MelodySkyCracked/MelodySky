/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.common.SystemProperty;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.ApplicationMessageHandler;
import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.LocalMessage;
import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.NativeInterfaceConfiguration;
import chrriis.dj.nativeswing.swtimpl.NativeInterfaceListener;
import chrriis.dj.nativeswing.swtimpl.PeerVMProcessFactory;
import chrriis.dj.nativeswing.swtimpl.common.NetworkURLClassLoader;
import chrriis.dj.nativeswing.swtimpl.core.DefaultPeerVMProcessFactory;
import chrriis.dj.nativeswing.swtimpl.core.InProcessMessagingInterface;
import chrriis.dj.nativeswing.swtimpl.core.MessagingInterface;
import chrriis.dj.nativeswing.swtimpl.core.OutProcessIOMessagingInterface;
import chrriis.dj.nativeswing.swtimpl.core.OutProcessSocketsMessagingInterface;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeComponent;
import chrriis.dj.nativeswing.swtimpl.core.l;
import chrriis.dj.nativeswing.swtimpl.core.lI;
import chrriis.dj.nativeswing.swtimpl.core.lIIIll;
import chrriis.dj.nativeswing.swtimpl.core.lIIlIl;
import chrriis.dj.nativeswing.swtimpl.core.lIIllI;
import chrriis.dj.nativeswing.swtimpl.core.lIIlll;
import chrriis.dj.nativeswing.swtimpl.core.lIlIlI;
import chrriis.dj.nativeswing.swtimpl.core.lIlIll;
import chrriis.dj.nativeswing.swtimpl.core.lIll;
import chrriis.dj.nativeswing.swtimpl.core.lIllI;
import chrriis.dj.nativeswing.swtimpl.core.lIlllI;
import chrriis.dj.nativeswing.swtimpl.core.llI;
import chrriis.dj.nativeswing.swtimpl.core.llII;
import chrriis.dj.nativeswing.swtimpl.core.llIIII;
import chrriis.dj.nativeswing.swtimpl.core.llIlll;
import chrriis.dj.nativeswing.swtimpl.core.lllIl;
import chrriis.dj.nativeswing.swtimpl.core.lllIll;
import chrriis.dj.nativeswing.swtimpl.core.llll;
import chrriis.dj.nativeswing.swtimpl.core.lllll;
import chrriis.dj.nativeswing.swtimpl.internal.ISWTNativeInterface;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class SWTNativeInterface
extends NativeInterface
implements ISWTNativeInterface {
    private static final boolean IS_SYNCING_MESSAGES = Boolean.parseBoolean(NSSystemPropertySWT.INTERFACE_SYNCMESSAGES.get());
    private static boolean isOpen;
    private static volatile NativeInterfaceConfiguration nativeInterfaceConfiguration;
    private volatile boolean isInitialized;
    private boolean isInProcess;
    private static final Object OPEN_CLOSE_SYNC_LOCK;
    private static final Object OPEN_STATE_LOCK;
    private static MessagingInterface messagingInterface;
    private static volatile Display display;
    private static volatile boolean isEventPumpRunning;
    private EventListenerList listenerList = new EventListenerList();
    private ApplicationMessageHandler applicationMessageHandler;
    private static volatile long lastProcessTime;

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public boolean isOpen_() {
        Object object = OPEN_STATE_LOCK;
        // MONITORENTER : object
        // MONITOREXIT : object
        return isOpen;
    }

    private void checkOpen() {
        if (!SWTNativeInterface.isOpen()) {
            throw new IllegalStateException("The native interface is not open! Please refer to the instructions to set it up properly.");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void close_() {
        Object object = OPEN_CLOSE_SYNC_LOCK;
        synchronized (object) {
            if (!SWTNativeInterface.isOpen()) {
                return;
            }
            NativeInterfaceListener[] nativeInterfaceListenerArray = OPEN_STATE_LOCK;
            synchronized (OPEN_STATE_LOCK) {
                isOpen = false;
                messagingInterface.destroy();
                messagingInterface = null;
                // ** MonitorExit[var2_2] (shouldn't be in output)
                for (NativeInterfaceListener nativeInterfaceListener : SWTNativeInterface.getNativeInterfaceListeners()) {
                    nativeInterfaceListener.nativeInterfaceClosed();
                }
                return;
            }
        }
    }

    @Override
    public NativeInterfaceConfiguration getConfiguration_() {
        if (nativeInterfaceConfiguration == null) {
            nativeInterfaceConfiguration = SWTNativeInterface.createConfiguration();
        }
        return nativeInterfaceConfiguration;
    }

    private void loadClipboardDebuggingProperties() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            if (!clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                return;
            }
            BufferedReader bufferedReader = new BufferedReader(new StringReader((String)clipboard.getData(DataFlavor.stringFlavor)));
            if ("[nativeswing debug]".equals(bufferedReader.readLine().trim().toLowerCase(Locale.ENGLISH))) {
                String string;
                while ((string = bufferedReader.readLine()) != null) {
                    if (string.length() == 0) continue;
                    int n = string.indexOf(61);
                    if (n <= 0) break;
                    String string2 = string.substring(0, n).trim();
                    String string3 = string.substring(n + 1).trim();
                    if (!string2.startsWith("nativeswing.")) continue;
                    System.setProperty(string2, string3);
                }
            }
            bufferedReader.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public boolean isInitialized_() {
        return this.isInitialized;
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public boolean isInProcess_() {
        Object object = OPEN_STATE_LOCK;
        // MONITORENTER : object
        // MONITOREXIT : object
        return this.isInProcess;
    }

    @Override
    public void initialize_() {
        Object object = OPEN_CLOSE_SYNC_LOCK;
        synchronized (object) {
            StackTraceElement[] stackTraceElementArray;
            if (SWTNativeInterface.isInitialized()) {
                return;
            }
            if (Boolean.parseBoolean(NSSystemPropertySWT.DEPENDENCIES_CHECKVERSIONS.get("true")) && SWT.getVersion() < 4332) {
                throw new IllegalStateException("The version of SWT that is required is 4.3 or later!");
            }
            if (nativeInterfaceConfiguration == null) {
                nativeInterfaceConfiguration = SWTNativeInterface.createConfiguration();
            }
            if (Utils.IS_MAC && !"applet".equals(NSSystemProperty.DEPLOYMENT_TYPE.get())) {
                stackTraceElementArray = Thread.currentThread().getStackTrace();
                int n = stackTraceElementArray.length;
                block5: for (int i = 0; i < n; ++i) {
                    StackTraceElement stackTraceElement = stackTraceElementArray[i];
                    try {
                        Class<?> classNotFoundException;
                        for (Class<?> clazz = classNotFoundException = Class.forName(stackTraceElement.getClassName()); clazz != null; clazz = clazz.getSuperclass()) {
                            if (!clazz.getName().equals("java.awt.Component")) continue;
                            System.err.println("On Mac, \"NativeInterface.initialize()\"/\"NativeInterface.open()\" should not be called after AWT static initializers have run, otherwise there can be all sorts of side effects (non-functional modal dialogs, etc.). Generally, the problem is when the \"main(String[])\" method is located inside an AWT component subclass and the fix is to move that main method to a standalone class. The problematic class here is \"" + classNotFoundException.getName() + "\"");
                            break block5;
                        }
                        continue;
                    }
                    catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
            }
            this.isInProcess = (stackTraceElementArray = NSSystemPropertySWT.INTERFACE_INPROCESS.get()) != null ? Boolean.parseBoolean((String)stackTraceElementArray) : Utils.IS_MAC;
            try {
                for (NativeInterfaceListener nativeInterfaceListener : SWTNativeInterface.getNativeInterfaceListeners()) {
                    nativeInterfaceListener.nativeInterfaceInitialized();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            if (this.isInProcess_()) {
                InProcess.access$000();
            } else {
                OutProcess.access$100();
            }
            Toolkit.getDefaultToolkit().addAWTEventListener(new lllll(this), 8L);
            this.isInitialized = true;
        }
    }

    @Override
    public void printStackTraces_() {
        Utils.printStackTraces();
        this.printPeerStackTrace(System.err);
    }

    @Override
    public void printStackTraces_(PrintStream printStream) {
        Utils.printStackTraces(printStream);
        this.printPeerStackTrace(printStream);
    }

    @Override
    public void printStackTraces_(PrintWriter printWriter) {
        Utils.printStackTraces(printWriter);
        this.printPeerStackTrace(printWriter);
    }

    private void printPeerStackTrace(Object object) {
        if (!this.isInProcess_() && SWTNativeInterface.isOpen()) {
            if (SWTNativeInterface.isUIThread(false)) {
                llIlll llIlll2 = new llIlll(this, "NativeSwing stack traces dump", object);
                llIlll2.start();
                try {
                    llIlll2.join();
                }
                catch (InterruptedException interruptedException) {}
            } else {
                boolean bl = object == null;
                CMN_printStackTraces cMN_printStackTraces = new CMN_printStackTraces(null);
                SWTNativeInterface.setMessageArgs(cMN_printStackTraces, bl);
                String string = (String)this.syncSend_(true, cMN_printStackTraces);
                if (!bl) {
                    String string2 = "---- NativeSwing[" + this.getMessagingInterface(false).getPID() + "] Peer VM Stack Traces ----" + Utils.LINE_SEPARATOR;
                    if (object instanceof PrintStream) {
                        PrintStream printStream = (PrintStream)object;
                        printStream.append(string2);
                        printStream.append(string);
                    } else if (object instanceof PrintWriter) {
                        PrintWriter printWriter = (PrintWriter)object;
                        printWriter.append(string2);
                        printWriter.append(string);
                    }
                }
            }
        }
    }

    @Override
    public void open_() {
        Object object = OPEN_CLOSE_SYNC_LOCK;
        synchronized (object) {
            if (SWTNativeInterface.isOpen()) {
                return;
            }
            SWTNativeInterface.initialize();
            this.loadClipboardDebuggingProperties();
            if (this.isInProcess_()) {
                InProcess.createInProcessCommunicationChannel();
            } else {
                OutProcess.createOutProcessCommunicationChannel();
            }
            try {
                for (NativeInterfaceListener nativeInterfaceListener : SWTNativeInterface.getNativeInterfaceListeners()) {
                    nativeInterfaceListener.nativeInterfaceOpened();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public boolean notifyKilled() {
        NativeInterfaceListener[] nativeInterfaceListenerArray = OPEN_STATE_LOCK;
        synchronized (OPEN_STATE_LOCK) {
            isOpen = false;
            messagingInterface = null;
            // ** MonitorExit[var1_1] (shouldn't be in output)
            try {
                for (NativeInterfaceListener nativeInterfaceListener : SWTNativeInterface.getNativeInterfaceListeners()) {
                    nativeInterfaceListener.nativeInterfaceClosed();
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            if (!OutProcess.isNativeSide() && nativeInterfaceConfiguration.isNativeSideRespawnedOnError()) {
                OutProcess.createOutProcessCommunicationChannel();
                return true;
            }
            return false;
        }
    }

    public void notifyRespawned() {
        try {
            for (NativeInterfaceListener nativeInterfaceListener : SWTNativeInterface.getNativeInterfaceListeners()) {
                nativeInterfaceListener.nativeInterfaceOpened();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Object syncSend_(boolean bl, Message message) {
        this.checkOpen();
        if (message instanceof LocalMessage) {
            LocalMessage localMessage = (LocalMessage)message;
            return SWTNativeInterface.runMessageCommand(localMessage);
        }
        return this.getMessagingInterface(!bl).syncSend(message);
    }

    @Override
    public void asyncSend_(boolean bl, Message message) {
        if (IS_SYNCING_MESSAGES) {
            this.syncSend_(bl, message);
        } else {
            this.checkOpen();
            if (message instanceof LocalMessage) {
                LocalMessage localMessage = (LocalMessage)message;
                SWTNativeInterface.runMessageCommand(localMessage);
                return;
            }
            this.getMessagingInterface(!bl).asyncSend(message);
        }
    }

    MessagingInterface getMessagingInterface(boolean bl) {
        Object object = OPEN_STATE_LOCK;
        synchronized (object) {
            if (this.isInProcess_()) {
                if (bl) {
                    InProcessMessagingInterface.SWTInProcessMessagingInterface sWTInProcessMessagingInterface = (InProcessMessagingInterface.SWTInProcessMessagingInterface)((InProcessMessagingInterface.SwingInProcessMessagingInterface)messagingInterface).getMirrorMessagingInterface();
                    return sWTInProcessMessagingInterface;
                }
                InProcessMessagingInterface.SwingInProcessMessagingInterface swingInProcessMessagingInterface = (InProcessMessagingInterface.SwingInProcessMessagingInterface)messagingInterface;
                return swingInProcessMessagingInterface;
            }
            if (bl) {
                if (messagingInterface instanceof OutProcessSocketsMessagingInterface.SWTOutProcessSocketsMessagingInterface) {
                    OutProcessSocketsMessagingInterface.SWTOutProcessSocketsMessagingInterface sWTOutProcessSocketsMessagingInterface = (OutProcessSocketsMessagingInterface.SWTOutProcessSocketsMessagingInterface)messagingInterface;
                    return sWTOutProcessSocketsMessagingInterface;
                }
                OutProcessIOMessagingInterface.SWTOutProcessIOMessagingInterface sWTOutProcessIOMessagingInterface = (OutProcessIOMessagingInterface.SWTOutProcessIOMessagingInterface)messagingInterface;
                return sWTOutProcessIOMessagingInterface;
            }
            if (messagingInterface instanceof OutProcessSocketsMessagingInterface.SwingOutProcessSocketsMessagingInterface) {
                OutProcessSocketsMessagingInterface.SwingOutProcessSocketsMessagingInterface swingOutProcessSocketsMessagingInterface = (OutProcessSocketsMessagingInterface.SwingOutProcessSocketsMessagingInterface)messagingInterface;
                return swingOutProcessSocketsMessagingInterface;
            }
            OutProcessIOMessagingInterface.SwingOutProcessIOMessagingInterface swingOutProcessIOMessagingInterface = (OutProcessIOMessagingInterface.SwingOutProcessIOMessagingInterface)messagingInterface;
            return swingOutProcessIOMessagingInterface;
        }
    }

    public Display getDisplay() {
        return display;
    }

    @Override
    public boolean isOutProcessNativeSide_() {
        return OutProcess.isNativeSide();
    }

    @Override
    public boolean isUIThread_(boolean bl) {
        if (this != false) {
            throw new IllegalStateException("The native interface is not alive!");
        }
        return this.getMessagingInterface(bl).isUIThread();
    }

    public int getInterfaceID(boolean bl) {
        if (this != false) {
            throw new IllegalStateException("The native interface is not alive!");
        }
        return this.getMessagingInterface(bl).getPID();
    }

    public void checkUIThread(boolean bl) {
        if (this != false) {
            throw new IllegalStateException("The native interface is not alive!");
        }
        this.getMessagingInterface(bl).checkUIThread();
    }

    @Override
    public boolean isEventPumpRunning_() {
        return isEventPumpRunning;
    }

    @Override
    public void runEventPump_() {
        if (!SWTNativeInterface.isInitialized()) {
            throw new IllegalStateException("Cannot run the event pump when the interface is not initialized!");
        }
        if (isEventPumpRunning) {
            throw new IllegalStateException("runEventPump was already called and can only be called once (the first call should be at the end of the main method)!");
        }
        isEventPumpRunning = true;
        this.startAutoShutdownThread();
        if (this.isInProcess_()) {
            InProcess.runEventPump();
        } else {
            OutProcess.runEventPump();
        }
    }

    private void startAutoShutdownThread() {
        Thread thread = display == null ? null : display.getThread();
        Thread thread2 = Thread.currentThread();
        lIllI lIllI2 = new lIllI(this, "NativeSwing Auto-Shutdown", thread, thread2);
        lIllI2.setDaemon(true);
        lIllI2.start();
    }

    @Override
    public void addNativeInterfaceListener_(NativeInterfaceListener nativeInterfaceListener) {
        this.listenerList.add(NativeInterfaceListener.class, nativeInterfaceListener);
    }

    @Override
    public void removeNativeInterfaceListener_(NativeInterfaceListener nativeInterfaceListener) {
        this.listenerList.remove(NativeInterfaceListener.class, nativeInterfaceListener);
    }

    @Override
    public NativeInterfaceListener[] getNativeInterfaceListeners_() {
        return (NativeInterfaceListener[])this.listenerList.getListeners(NativeInterfaceListener.class);
    }

    @Override
    public void setApplicationMessageHandler_(ApplicationMessageHandler applicationMessageHandler) {
        this.applicationMessageHandler = applicationMessageHandler;
    }

    public ApplicationMessageHandler getApplicationMessageHandler() {
        return this.applicationMessageHandler;
    }

    private static void handleQuit() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(new lllIll());
            return;
        }
        ApplicationMessageHandler applicationMessageHandler = SWTNativeInterface.getInstance().getApplicationMessageHandler();
        if (applicationMessageHandler != null) {
            applicationMessageHandler.handleQuit();
        }
    }

    private static void destroyControls() {
        if (display != null && !display.isDisposed()) {
            if (display.getThread() != Thread.currentThread()) {
                display.syncExec(new lllIl());
                return;
            }
            for (Control control : SWTNativeComponent.getControls()) {
                Shell shell = control.isDisposed() ? null : control.getShell();
                try {
                    if (shell != null) {
                        shell.dispose();
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                control.dispose();
            }
            try {
                display.dispose();
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static SWTNativeInterface getInstance() {
        return (SWTNativeInterface)NativeInterface.getInstance();
    }

    protected static int getMessageID(Message message) {
        return NativeInterface.getMessageID(message);
    }

    protected static boolean isMessageValid(Message message) {
        return NativeInterface.isMessageValid(message);
    }

    protected static Object runMessageCommand(LocalMessage localMessage) {
        return NativeInterface.runMessageCommand(localMessage);
    }

    protected static Object runMessageCommand(CommandMessage commandMessage) throws Exception {
        return NativeInterface.runMessageCommand(commandMessage);
    }

    protected static boolean isMessageSyncExec(Message message) {
        return NativeInterface.isMessageSyncExec(message);
    }

    protected static void setMessageSyncExec(Message message, boolean bl) {
        NativeInterface.setMessageSyncExec(message, bl);
    }

    protected static void setMessageArgs(CommandMessage commandMessage, Object ... objectArray) {
        NativeInterface.setMessageArgs(commandMessage, objectArray);
    }

    protected static void computeMessageID(Message message, boolean bl) {
        NativeInterface.computeMessageID(message, bl);
    }

    protected static void setMessageUI(Message message, boolean bl) {
        NativeInterface.setMessageUI(message, bl);
    }

    protected static boolean isMessageUI(Message message) {
        return NativeInterface.isMessageUI(message);
    }

    @Override
    public void main_(String[] stringArray) throws Exception {
        OutProcess.runNativeSide(stringArray);
    }

    static void access$200(SWTNativeInterface sWTNativeInterface, Object object) {
        sWTNativeInterface.printPeerStackTrace(object);
    }

    static Display access$400() {
        return display;
    }

    static boolean access$502(boolean bl) {
        isEventPumpRunning = bl;
        return isEventPumpRunning;
    }

    static void access$600() {
        SWTNativeInterface.handleQuit();
    }

    static Object access$700() {
        return OPEN_STATE_LOCK;
    }

    static MessagingInterface access$802(MessagingInterface messagingInterface) {
        SWTNativeInterface.messagingInterface = messagingInterface;
        return SWTNativeInterface.messagingInterface;
    }

    static boolean access$902(boolean bl) {
        isOpen = bl;
        return isOpen;
    }

    static void access$1100() {
        SWTNativeInterface.destroyControls();
    }

    static Display access$402(Display display) {
        SWTNativeInterface.display = display;
        return SWTNativeInterface.display;
    }

    static boolean access$500() {
        return isEventPumpRunning;
    }

    static boolean access$900() {
        return isOpen;
    }

    static NativeInterfaceConfiguration access$1500() {
        return nativeInterfaceConfiguration;
    }

    static Class[] access$1600(NativeInterfaceConfiguration nativeInterfaceConfiguration) {
        return SWTNativeInterface.getNativeClassPathReferenceClasses(nativeInterfaceConfiguration);
    }

    static String[] access$1700(NativeInterfaceConfiguration nativeInterfaceConfiguration) {
        return SWTNativeInterface.getNativeClassPathReferenceResources(nativeInterfaceConfiguration);
    }

    static String[] access$1800(NativeInterfaceConfiguration nativeInterfaceConfiguration) {
        return SWTNativeInterface.getPeerVMParams(nativeInterfaceConfiguration);
    }

    static MessagingInterface access$800() {
        return messagingInterface;
    }

    static long access$2100() {
        return lastProcessTime;
    }

    static long access$2102(long l2) {
        lastProcessTime = l2;
        return lastProcessTime;
    }

    static {
        OPEN_CLOSE_SYNC_LOCK = new Object();
        OPEN_STATE_LOCK = new Object();
        lastProcessTime = Long.MAX_VALUE;
    }

    static class OutProcess {
        private static final boolean IS_PROCESS_IO_CHANNEL_MODE = "processio".equals(NSSystemPropertySWT.INTERFACE_OUTPROCESS_COMMUNICATION.get());
        private static volatile int pid;

        OutProcess() {
        }

        private static void initialize() {
            NativeSwing.initialize();
            Runtime.getRuntime().addShutdownHook(new lIlllI());
        }

        static boolean isNativeSide() {
            return SWTNativeInterface.access$400() != null;
        }

        static void createOutProcessCommunicationChannel() {
            Object object = SWTNativeInterface.access$700();
            synchronized (object) {
                for (int i = 2; i >= 0; --i) {
                    try {
                        SWTNativeInterface.access$802(OutProcess.createOutProcessMessagingInterface());
                        break;
                    }
                    catch (RuntimeException runtimeException) {
                        if (i != 0) continue;
                        throw runtimeException;
                    }
                }
                SWTNativeInterface.access$902(true);
            }
            object = new Properties();
            Properties properties = System.getProperties();
            for (Object object2 : properties.keySet()) {
                Object object3;
                if (!(object2 instanceof String) || !((object3 = properties.get(object2)) instanceof String)) continue;
                ((Properties)object).setProperty((String)object2, (String)object3);
            }
            new CMN_setProperties(null).syncExec(true, object);
        }

        /*
         * WARNING - void declaration
         */
        private static Process createProcess(String string, int n, int n2) {
            Process process;
            Object[] objectArray;
            Object object;
            Object object2;
            String[] stringArray;
            ArrayList<String> arrayList = new ArrayList<String>();
            ArrayList<Object> arrayList2 = new ArrayList<Object>();
            Class[] classArray = SWTNativeInterface.access$1600(SWTNativeInterface.access$1500());
            if (classArray != null) {
                arrayList2.addAll(Arrays.asList(classArray));
            }
            if ((stringArray = SWTNativeInterface.access$1700(SWTNativeInterface.access$1500())) != null) {
                arrayList2.addAll(Arrays.asList(stringArray));
            }
            ArrayList<String> arrayList3 = new ArrayList<String>();
            arrayList2.add(NativeSwing.class);
            arrayList2.add(NativeInterface.class);
            arrayList2.add(SWTNativeInterface.class);
            if (SWTNativeInterface.class.getClassLoader() != NativeInterface.class.getClassLoader()) {
                WebServer.getDefaultWebServer().addReferenceClassLoader(SWTNativeInterface.class.getClassLoader());
            }
            arrayList2.add("org/eclipse/swt/widgets/Display.class");
            arrayList3.add("org/mozilla/xpcom/Mozilla.class");
            arrayList3.add("org/mozilla/interfaces/nsIWebBrowser.class");
            for (String string2 : arrayList3) {
                void object62;
                if (string2.startsWith("/")) {
                    String string3 = string2.substring(1);
                }
                if (Utils.getResourceWithinJavaModules(SWTNativeInterface.class, '/' + (String)object62) == null) continue;
                arrayList2.add(object62);
            }
            boolean bl = Boolean.parseBoolean(NSSystemPropertySWT.PEERVM_FORCEPROXYCLASSLOADER.get());
            if (!bl) {
                for (Object e : arrayList2) {
                    if (e instanceof Class) {
                        object2 = Utils.getClassPathFile((Class)e);
                    } else {
                        object = (String)e;
                        object2 = Utils.getClassPathFile((String)object);
                        if (Utils.getResourceWithinJavaModules(SWTNativeInterface.class, '/' + (String)object) == null) {
                            throw new IllegalStateException("A resource that is needed in the classpath is missing: " + e);
                        }
                    }
                    if (object2 != null) {
                        object = ((File)object2).getAbsolutePath();
                        if (arrayList.contains(object)) continue;
                        arrayList.add((String)object);
                        continue;
                    }
                    bl = true;
                }
            }
            if (bl) {
                arrayList.clear();
                File file = new File(SystemProperty.JAVA_IO_TMPDIR.get(), ".djnativeswing/classpath");
                Utils.deleteAll(file);
                String string4 = NetworkURLClassLoader.class.getName().replace('.', '/') + ".class";
                object2 = new File(file, string4);
                ((File)object2).getParentFile().mkdirs();
                if (!((File)object2).exists()) {
                    try {
                        int n3;
                        object = new BufferedOutputStream(new FileOutputStream((File)object2));
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(Utils.getResourceAsStreamWithinJavaModules(SWTNativeInterface.class, "/" + string4));
                        objectArray = new byte[1024];
                        while ((n3 = bufferedInputStream.read((byte[])objectArray)) != -1) {
                            ((BufferedOutputStream)object).write((byte[])objectArray, 0, n3);
                        }
                        bufferedInputStream.close();
                        ((FilterOutputStream)object).close();
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    ((File)object2).deleteOnExit();
                }
                arrayList.add(file.getAbsolutePath());
            }
            ArrayList<Object> arrayList4 = new ArrayList<Object>();
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            object2 = SWTNativeInterface.access$1800(SWTNativeInterface.access$1500());
            boolean bl2 = false;
            boolean bl3 = false;
            if (object2 != null) {
                objectArray = object2;
                int n4 = objectArray.length;
                for (int i = 0; i < n4; ++i) {
                    Object object3 = objectArray[i];
                    if (((String)object3).startsWith("-D")) {
                        String string5 = ((String)object3).substring(2);
                        int n5 = string5.indexOf(61);
                        String string6 = string5.substring(0, n5);
                        String string7 = string5.substring(n5 + 1);
                        hashMap.put(string6, string7);
                        if (SystemProperty.JAVA_LIBRARY_PATH.getName().equals(string6)) {
                            bl2 = true;
                            continue;
                        }
                        if (!"swt.library.path".equals(string6)) continue;
                        bl3 = true;
                        continue;
                    }
                    arrayList4.add(object3);
                }
            }
            if (!bl2 && (objectArray = SystemProperty.JAVA_LIBRARY_PATH.get()) != null) {
                hashMap.put(SystemProperty.JAVA_LIBRARY_PATH.getName(), objectArray);
            }
            if (!bl3 && (objectArray = NSSystemPropertySWT.SWT_LIBRARY_PATH.get()) != null) {
                hashMap.put(NSSystemPropertySWT.SWT_LIBRARY_PATH.getName(), objectArray);
            }
            for (String string8 : objectArray = new String[]{NSSystemPropertySWT.INTERFACE_SYNCMESSAGES.getName(), NSSystemPropertySWT.INTERFACE_DEBUG_PRINTMESSAGES.getName(), NSSystemPropertySWT.PEERVM_DEBUG_PRINTSTARTMESSAGE.getName(), NSSystemPropertySWT.PEERVM_DEBUG_PRINTSTOPMESSAGE.getName(), NSSystemPropertySWT.SWT_DEVICE_DEBUG.getName(), NSSystemPropertySWT.SWT_DEVICEDATA_DEBUG.getName(), NSSystemPropertySWT.SWT_DEVICEDATA_TRACKING.getName()}) {
                if (!Boolean.parseBoolean(System.getProperty(string8))) continue;
                hashMap.put(string8, "true");
            }
            hashMap.put(NSSystemProperty.LOCALHOSTADDRESS.getName(), string);
            ArrayList<String> arrayList5 = new ArrayList<String>();
            if (bl) {
                String string9 = NetworkURLClassLoader.class.getName();
                arrayList5.add(WebServer.getDefaultWebServer().getClassPathResourceURL("", ""));
                arrayList5.add(NativeInterface.class.getName());
            } else {
                String string10 = NativeInterface.class.getName();
            }
            arrayList5.add(String.valueOf(n2));
            arrayList5.add(String.valueOf(n));
            PeerVMProcessFactory peerVMProcessFactory = SWTNativeInterface.access$1500().getPeerVMProcessFactory();
            if (peerVMProcessFactory == null) {
                peerVMProcessFactory = new DefaultPeerVMProcessFactory();
            }
            Object var18_43 = null;
            try {
                void var15_34;
                process = peerVMProcessFactory.createProcess(arrayList.toArray(new String[0]), hashMap, arrayList4.toArray(new String[0]), (String)var15_34, arrayList5.toArray(new String[0]));
            }
            catch (Exception exception) {
                throw new IllegalStateException("Failed to spawn the peer VM!", exception);
            }
            if (process == null) {
                throw new IllegalStateException("Failed to spawn the peer VM!");
            }
            return process;
        }

        private static MessagingInterface createOutProcessMessagingInterface() {
            Process process;
            int n;
            boolean bl;
            String string = Utils.getLocalHostAddress();
            if (string == null) {
                throw new IllegalStateException("Failed to find a suitable local host address to communicate with a spawned VM!");
            }
            boolean bl2 = Boolean.parseBoolean(NSSystemPropertySWT.PEERVM_CREATE.get("true"));
            boolean bl3 = bl = IS_PROCESS_IO_CHANNEL_MODE && bl2;
            if (bl) {
                n = 0;
            } else {
                n = Integer.parseInt(NSSystemPropertySWT.INTERFACE_PORT.get("-1"));
                if (n <= 0) {
                    ServerSocket serverSocket;
                    try {
                        serverSocket = new ServerSocket();
                        serverSocket.setReuseAddress(false);
                        serverSocket.bind(new InetSocketAddress(InetAddress.getByName(string), 0));
                    }
                    catch (IOException iOException) {
                        throw new RuntimeException(iOException);
                    }
                    n = serverSocket.getLocalPort();
                    try {
                        serverSocket.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
            }
            int n2 = ++pid;
            if (bl2) {
                process = OutProcess.createProcess(string, n, n2);
                if (!bl) {
                    OutProcess.connectStream(System.out, process.getInputStream(), "out", n2);
                }
                OutProcess.connectStream(System.err, process.getErrorStream(), "err", n2);
            } else {
                process = null;
            }
            if (bl) {
                return new OutProcessIOMessagingInterface.SwingOutProcessIOMessagingInterface(process.getInputStream(), process.getOutputStream(), false, process, n2);
            }
            Exception exception = null;
            Socket socket = null;
            long l2 = Integer.parseInt(NSSystemPropertySWT.INTERFACE_OUTPROCESS_CONNECTIONTIMEOUT.get("10000"));
            long l3 = System.currentTimeMillis();
            while (true) {
                if (process != null) {
                    try {
                        process.exitValue();
                        break;
                    }
                    catch (IllegalThreadStateException illegalThreadStateException) {
                        // empty catch block
                    }
                }
                try {
                    socket = new Socket(string, n);
                    exception = null;
                }
                catch (Exception exception2) {
                    exception = exception2;
                    try {
                        Thread.sleep(200L);
                        continue;
                    }
                    catch (Exception exception3) {
                        // empty catch block
                    }
                    if (System.currentTimeMillis() - l3 < l2) continue;
                }
                break;
            }
            if (socket == null) {
                if (process != null) {
                    process.destroy();
                }
                if (exception == null) {
                    throw new IllegalStateException("Failed to connect to spawned VM! The native side process was already terminated.");
                }
                throw new IllegalStateException("Failed to connect to spawned VM!", exception);
            }
            return new OutProcessSocketsMessagingInterface.SwingOutProcessSocketsMessagingInterface(socket, false, process, n2);
        }

        private static void connectStream(PrintStream printStream, InputStream inputStream, String string, int n) {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            Thread thread = new Thread(() -> OutProcess.lambda$connectStream$0(n, bufferedInputStream, printStream), "NativeSwing[" + n + "] " + string + " Stream Connector");
            thread.setDaemon(true);
            thread.start();
        }

        static void runNativeSide(String[] stringArray) throws IOException {
            Object object;
            Object object2;
            Object object3;
            Object object4;
            Object object5;
            Object object6;
            int n = Integer.parseInt(stringArray[0]);
            if (Boolean.parseBoolean(NSSystemPropertySWT.PEERVM_DEBUG_PRINTSTARTMESSAGE.get())) {
                System.err.println("Starting peer VM #" + n);
            }
            Object object7 = SWTNativeInterface.access$700();
            synchronized (object7) {
                SWTNativeInterface.access$902(true);
            }
            int n2 = Integer.parseInt(stringArray[1]);
            boolean bl = n2 <= 0;
            Socket socket = null;
            if (!bl) {
                object6 = null;
                long l2 = System.currentTimeMillis();
                while (true) {
                    try {
                        object6 = new ServerSocket();
                        ((ServerSocket)object6).setReuseAddress(true);
                        ((ServerSocket)object6).bind(new InetSocketAddress(Utils.getLocalHostAddress(), n2));
                        object5 = null;
                    }
                    catch (IOException iOException) {
                        object5 = iOException;
                        if (object6 != null) {
                            try {
                                ((ServerSocket)object6).close();
                            }
                            catch (Exception exception) {
                                // empty catch block
                            }
                        }
                        object6 = null;
                        try {
                            Thread.sleep(200L);
                            continue;
                        }
                        catch (Exception exception) {
                            // empty catch block
                        }
                        if (System.currentTimeMillis() - l2 < 5000L) continue;
                    }
                    break;
                }
                if (object6 == null) {
                    if (object5 == null) {
                        throw new IllegalStateException("Failed to create the server socket for native side communication!");
                    }
                    throw object5;
                }
                object4 = object6;
                if (!Boolean.parseBoolean(NSSystemPropertySWT.PEERVM_KEEPALIVE.get())) {
                    object3 = new l("NativeSwing Shutdown", (ServerSocket)object4);
                    ((Thread)object3).setDaemon(true);
                    ((Thread)object3).start();
                }
                Runtime.getRuntime().addShutdownHook(new llIIII());
                try {
                    socket = ((ServerSocket)object6).accept();
                }
                catch (Exception exception) {
                    throw new IllegalStateException("The native side did not receive an incoming connection!", exception);
                }
            }
            Device.DEBUG = Boolean.parseBoolean(NSSystemPropertySWT.SWT_DEVICE_DEBUG.get());
            object6 = new DeviceData();
            ((DeviceData)object6).debug = Boolean.parseBoolean(NSSystemPropertySWT.SWT_DEVICEDATA_DEBUG.get());
            ((DeviceData)object6).tracking = Boolean.parseBoolean(NSSystemPropertySWT.SWT_DEVICEDATA_TRACKING.get());
            SWTNativeInterface.access$402(new Display((DeviceData)object6));
            SWTNativeInterface.access$400().addListener(21, new lIIllI());
            Display.setAppName("DJ Native Swing");
            if (bl) {
                object2 = System.out;
                object = System.in;
                object5 = new OutProcessIOMessagingInterface.SWTOutProcessIOMessagingInterface((InputStream)object, (OutputStream)object2, true, SWTNativeInterface.access$400(), n);
                object4 = SWTNativeInterface.access$700();
                synchronized (object4) {
                    SWTNativeInterface.access$802((MessagingInterface)object5);
                }
                System.setIn(new lIIIll());
                System.setOut(new PrintStream(new llll(n)));
                if (Utils.IS_WINDOWS) {
                    object3 = SWTNativeInterface.access$700();
                    synchronized (object3) {
                        object4 = SWTNativeInterface.access$800();
                    }
                    new llI("System.in unlocker", (MessagingInterface)object4).start();
                }
            } else {
                object2 = new OutProcessSocketsMessagingInterface.SWTOutProcessSocketsMessagingInterface(socket, true, SWTNativeInterface.access$400(), n);
                object = SWTNativeInterface.access$700();
                synchronized (object) {
                    SWTNativeInterface.access$802((MessagingInterface)object2);
                }
            }
            while (SWTNativeInterface.access$400() != null && !SWTNativeInterface.access$400().isDisposed()) {
                try {
                    SWTNativeInterface.access$2102(System.currentTimeMillis());
                    if (!SWTNativeInterface.access$400().readAndDispatch()) {
                        SWTNativeInterface.access$2102(Long.MAX_VALUE);
                        SWTNativeInterface.access$400().sleep();
                    }
                    SWTNativeInterface.access$2102(Long.MAX_VALUE);
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            if (Boolean.parseBoolean(NSSystemPropertySWT.PEERVM_DEBUG_PRINTSTOPMESSAGE.get())) {
                System.err.println("Stopping peer VM #" + n);
            }
        }

        static void runEventPump() {
            while (SWTNativeInterface.access$500()) {
                try {
                    Thread.sleep(1000L);
                }
                catch (Exception exception) {}
            }
        }

        private static void lambda$connectStream$0(int n, BufferedInputStream bufferedInputStream, PrintStream printStream) {
            IOStreamFormatter iOStreamFormatter = new IOStreamFormatter(n);
            try {
                int n2;
                byte[] byArray = new byte[1024];
                while ((n2 = bufferedInputStream.read(byArray)) != -1) {
                    byte[] byArray2 = iOStreamFormatter.process(byArray, 0, n2);
                    try {
                        printStream.write(byArray2);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        static void access$100() {
            OutProcess.initialize();
        }

        private static class CMJ_unlockSystemIn
        extends CommandMessage {
            private CMJ_unlockSystemIn() {
            }

            @Override
            public Object run(Object[] objectArray) throws Exception {
                new Message().asyncSend(true);
                return null;
            }

            CMJ_unlockSystemIn(lllll lllll2) {
                this();
            }
        }

        private static class CMJ_systemOut
        extends CommandMessage {
            private CMJ_systemOut() {
            }

            @Override
            public Object run(Object[] objectArray) {
                try {
                    System.out.write((byte[])objectArray[0]);
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
                return null;
            }

            CMJ_systemOut(lllll lllll2) {
                this();
            }
        }

        private static class CMJ_handleClosedDisplay
        extends CommandMessage {
            private CMJ_handleClosedDisplay() {
            }

            @Override
            public Object run(Object[] objectArray) {
                SWTNativeInterface.access$600();
                return null;
            }

            CMJ_handleClosedDisplay(lllll lllll2) {
                this();
            }
        }

        private static class IOStreamFormatter {
            private ByteArrayOutputStream baos = new ByteArrayOutputStream();
            private byte lastByte = (byte)Utils.LINE_SEPARATOR.charAt(Utils.LINE_SEPARATOR.length() - 1);
            private boolean isAddingMessage = true;
            private final byte[] prefixBytes;

            public IOStreamFormatter(int n) {
                this.prefixBytes = ("NativeSwing[" + n + "]: ").getBytes();
            }

            public byte[] process(byte[] byArray, int n, int n2) throws IOException {
                this.baos.reset();
                for (int i = n; i < n2; ++i) {
                    byte by = byArray[i];
                    if (this.isAddingMessage) {
                        this.baos.write(this.prefixBytes);
                    }
                    this.isAddingMessage = by == this.lastByte;
                    this.baos.write(by);
                }
                return this.baos.toByteArray();
            }
        }

        private static class CMN_setProperties
        extends CommandMessage {
            private CMN_setProperties() {
            }

            @Override
            public Object run(Object[] objectArray) {
                Properties properties = System.getProperties();
                Properties properties2 = (Properties)objectArray[0];
                for (Object object : properties2.keySet()) {
                    if (properties.containsKey(object)) continue;
                    try {
                        System.setProperty((String)object, properties2.getProperty((String)object));
                    }
                    catch (Exception exception) {}
                }
                return null;
            }

            CMN_setProperties(lllll lllll2) {
                this();
            }
        }

        private static class CMN_destroyControls
        extends CommandMessage {
            private CMN_destroyControls() {
            }

            @Override
            public Object run(Object[] objectArray) throws Exception {
                if (SWTNativeInterface.access$400() != null && !SWTNativeInterface.access$400().isDisposed()) {
                    SWTNativeInterface.access$400().syncExec(new lIlIll(this));
                }
                return null;
            }

            CMN_destroyControls(lllll lllll2) {
                this();
            }
        }
    }

    static class InProcess {
        private static volatile int pid;

        InProcess() {
        }

        static void createInProcessCommunicationChannel() {
            Object object = SWTNativeInterface.access$700();
            synchronized (object) {
                SWTNativeInterface.access$802(InProcess.createInProcessMessagingInterface());
                SWTNativeInterface.access$902(true);
            }
        }

        private static void initialize() {
            Device.DEBUG = Boolean.parseBoolean(NSSystemPropertySWT.SWT_DEVICE_DEBUG.get());
            if (Utils.IS_MAC && "applet".equals(NSSystemProperty.DEPLOYMENT_TYPE.get())) {
                NativeSwing.initialize();
                InProcess.runWithMacExecutor(new llII());
            } else {
                try {
                    InProcess.findSWTDisplay();
                }
                catch (SWTException sWTException) {
                    if (Utils.IS_MAC) {
                        InProcess.runWithMacExecutor(new lIll());
                    }
                    throw sWTException;
                }
                NativeSwing.initialize();
            }
            if (!Utils.IS_MAC || Boolean.parseBoolean(NSSystemPropertySWT.INTERFACE_INPROCESS_FORCESHUTDOWNHOOK.get())) {
                Runtime.getRuntime().addShutdownHook(new lIIlIl("NativeSwing Shutdown Hook"));
            }
        }

        private static void runWithMacExecutor(Runnable runnable) {
            Executor executor;
            Object object;
            try {
                object = Class.forName("com.apple.concurrent.Dispatch").getMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
                executor = (Executor)object.getClass().getMethod("getNonBlockingMainQueueExecutor", new Class[0]).invoke(object, new Object[0]);
            }
            catch (Exception exception) {
                throw new IllegalStateException("Failed to use the Mac Dispatch executor. This may happen if the version of Java that is used is too old.", exception);
            }
            object = new AtomicBoolean(false);
            AtomicReference atomicReference = new AtomicReference();
            Object object2 = object;
            synchronized (object2) {
                executor.execute(new lIIlll(runnable, atomicReference, (AtomicBoolean)object));
                while (!((AtomicBoolean)object).get()) {
                    try {
                        object.wait();
                    }
                    catch (InterruptedException interruptedException) {}
                }
            }
            object2 = (Throwable)atomicReference.get();
            if (object2 != null) {
                if (object2 instanceof RuntimeException) {
                    throw (RuntimeException)object2;
                }
                throw new RuntimeException((Throwable)object2);
            }
        }

        private static void findSWTDisplay() {
            SWTNativeInterface.access$402(Display.getCurrent());
            if (SWTNativeInterface.access$400() == null && Boolean.parseBoolean(NSSystemPropertySWT.INTERFACE_INPROCESS_USEEXTERNALSWTDISPLAY.get())) {
                SWTNativeInterface.access$402(Display.getDefault());
                if (SWTNativeInterface.access$400().getThread() == Thread.currentThread()) {
                    SWTNativeInterface.access$400().dispose();
                    SWTNativeInterface.access$402(null);
                    NSSystemPropertySWT.INTERFACE_INPROCESS_USEEXTERNALSWTDISPLAY.set("false");
                }
            }
            if (SWTNativeInterface.access$400() == null) {
                DeviceData deviceData = new DeviceData();
                deviceData.debug = Boolean.parseBoolean(NSSystemPropertySWT.SWT_DEVICEDATA_DEBUG.get());
                deviceData.tracking = Boolean.parseBoolean(NSSystemPropertySWT.SWT_DEVICEDATA_TRACKING.get());
                SWTNativeInterface.access$402(new Display(deviceData));
            }
            SWTNativeInterface.access$400().addListener(21, new lIlIlI());
        }

        private static MessagingInterface createInProcessMessagingInterface() {
            int n = ++pid;
            return new InProcessMessagingInterface.SWTInProcessMessagingInterface(SWTNativeInterface.access$400(), n).getMirrorMessagingInterface();
        }

        static void runEventPump() {
            if (Boolean.parseBoolean(NSSystemPropertySWT.INTERFACE_INPROCESS_USEEXTERNALSWTDISPLAY.get()) && SWTNativeInterface.access$400().getThread() != Thread.currentThread()) {
                return;
            }
            if (Utils.IS_MAC && SWTNativeInterface.access$400().getThread() != Thread.currentThread()) {
                InProcess.runWithMacExecutor(new lI());
                return;
            }
            InProcess.runSWTEventPump();
        }

        private static void runSWTEventPump() {
            while (SWTNativeInterface.access$500()) {
                try {
                    if (SWTNativeInterface.access$400().isDisposed()) {
                        SWTNativeInterface.access$502(false);
                        continue;
                    }
                    if (SWTNativeInterface.access$400().readAndDispatch() || !SWTNativeInterface.access$500()) continue;
                    SWTNativeInterface.access$400().sleep();
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            SWTNativeInterface.access$400().dispose();
        }

        static void access$000() {
            InProcess.initialize();
        }

        static void access$1000() {
            InProcess.findSWTDisplay();
        }

        static void access$1200() {
            InProcess.runSWTEventPump();
        }
    }

    private static class CMN_printStackTraces
    extends CommandMessage {
        private CMN_printStackTraces() {
        }

        @Override
        public Object run(Object[] objectArray) {
            boolean bl = (Boolean)objectArray[0];
            if (bl) {
                Utils.printStackTraces();
                return null;
            }
            StringWriter stringWriter = new StringWriter();
            Utils.printStackTraces(new PrintWriter(stringWriter));
            return stringWriter.toString();
        }

        CMN_printStackTraces(lllll lllll2) {
            this();
        }
    }
}

