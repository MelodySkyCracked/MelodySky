/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.swtimpl.ApplicationMessageHandler;
import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.LocalMessage;
import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NativeInterfaceConfiguration;
import chrriis.dj.nativeswing.swtimpl.NativeInterfaceListener;
import chrriis.dj.nativeswing.swtimpl.internal.ISWTNativeInterface;
import chrriis.dj.nativeswing.swtimpl.internal.NativeCoreObjectFactory;
import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class NativeInterface {
    private static ISWTNativeInterface swtNativeInterface = (ISWTNativeInterface)NativeCoreObjectFactory.create(ISWTNativeInterface.class, "chrriis.dj.nativeswing.swtimpl.core.SWTNativeInterface", new Class[0], new Object[0]);

    protected static NativeInterface getInstance() {
        return (NativeInterface)((Object)swtNativeInterface);
    }

    public static boolean isOpen() {
        return swtNativeInterface.isOpen_();
    }

    public static void close() {
        swtNativeInterface.close_();
    }

    public static NativeInterfaceConfiguration getConfiguration() {
        return swtNativeInterface.getConfiguration_();
    }

    public static boolean isInitialized() {
        return swtNativeInterface.isInitialized_();
    }

    public static boolean isInProcess() {
        return swtNativeInterface.isInProcess_();
    }

    static boolean isOutProcessNativeSide() {
        return swtNativeInterface.isOutProcessNativeSide_();
    }

    public static void initialize() {
        swtNativeInterface.initialize_();
    }

    public static void printStackTraces() {
        swtNativeInterface.printStackTraces_();
    }

    public static void printStackTraces(PrintStream printStream) {
        swtNativeInterface.printStackTraces_(printStream);
    }

    public static void printStackTraces(PrintWriter printWriter) {
        swtNativeInterface.printStackTraces_(printWriter);
    }

    public static void open() {
        swtNativeInterface.open_();
    }

    static Object syncSend(boolean bl, Message message) {
        return swtNativeInterface.syncSend_(bl, message);
    }

    static void asyncSend(boolean bl, Message message) {
        swtNativeInterface.asyncSend_(bl, message);
    }

    public static boolean isUIThread(boolean bl) {
        return swtNativeInterface.isUIThread_(bl);
    }

    public static void runEventPump() {
        swtNativeInterface.runEventPump_();
    }

    public static boolean isEventPumpRunning() {
        return swtNativeInterface.isEventPumpRunning_();
    }

    public static void addNativeInterfaceListener(NativeInterfaceListener nativeInterfaceListener) {
        swtNativeInterface.addNativeInterfaceListener_(nativeInterfaceListener);
    }

    public static void removeNativeInterfaceListener(NativeInterfaceListener nativeInterfaceListener) {
        swtNativeInterface.removeNativeInterfaceListener_(nativeInterfaceListener);
    }

    public static NativeInterfaceListener[] getNativeInterfaceListeners() {
        return swtNativeInterface.getNativeInterfaceListeners_();
    }

    public static void setApplicationMessageHandler(ApplicationMessageHandler applicationMessageHandler) {
        swtNativeInterface.setApplicationMessageHandler_(applicationMessageHandler);
    }

    protected static NativeInterfaceConfiguration createConfiguration() {
        return new NativeInterfaceConfiguration();
    }

    protected static Object runMessageCommand(LocalMessage localMessage) {
        return localMessage.runCommand();
    }

    protected static Object runMessageCommand(CommandMessage commandMessage) throws Exception {
        return commandMessage.runCommand();
    }

    protected static boolean isMessageSyncExec(Message message) {
        return message.isSyncExec();
    }

    protected static void setMessageSyncExec(Message message, boolean bl) {
        message.setSyncExec(bl);
    }

    protected static String[] getPeerVMParams(NativeInterfaceConfiguration nativeInterfaceConfiguration) {
        return nativeInterfaceConfiguration.getPeerVMParams();
    }

    protected static Class[] getNativeClassPathReferenceClasses(NativeInterfaceConfiguration nativeInterfaceConfiguration) {
        return nativeInterfaceConfiguration.getNativeClassPathReferenceClasses();
    }

    protected static String[] getNativeClassPathReferenceResources(NativeInterfaceConfiguration nativeInterfaceConfiguration) {
        return nativeInterfaceConfiguration.getNativeClassPathReferenceResources();
    }

    protected static int getMessageID(Message message) {
        return message.getID();
    }

    protected static boolean isMessageValid(Message message) {
        return message.isValid();
    }

    protected static void setMessageArgs(CommandMessage commandMessage, Object ... objectArray) {
        commandMessage.setArgs(objectArray);
    }

    protected static void computeMessageID(Message message, boolean bl) {
        message.computeID(bl);
    }

    protected static void setMessageUI(Message message, boolean bl) {
        message.setUI(bl);
    }

    protected static boolean isMessageUI(Message message) {
        return message.isUI();
    }

    public static void main(String[] stringArray) throws Exception {
        ((ISWTNativeInterface)((Object)NativeInterface.getInstance())).main_(stringArray);
    }
}

