/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeInterface;
import chrriis.dj.nativeswing.swtimpl.core.lIIIIl;
import chrriis.dj.nativeswing.swtimpl.core.lIllll;
import chrriis.dj.nativeswing.swtimpl.core.lll;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.swt.SWT;

abstract class MessagingInterface {
    protected static final boolean IS_DEBUGGING_MESSAGES = Boolean.parseBoolean(NSSystemPropertySWT.INTERFACE_DEBUG_PRINTMESSAGES.get());
    private int pid;
    private volatile boolean isAlive;
    private Object RECEIVER_LOCK = new Object();
    private final boolean isNativeSide;
    private List receivedMessageList = new LinkedList();
    private boolean isWaitingResponse;
    private ObjectRegistry syncThreadRegistry = new ObjectRegistry();
    private final Object LOCK = new Object();

    public MessagingInterface(boolean bl, int n) {
        this.isNativeSide = bl;
        this.pid = n;
    }

    public abstract void destroy();

    public abstract boolean isUIThread();

    protected void setAlive(boolean bl) {
        this.isAlive = bl;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    protected void initialize(boolean bl) {
        this.setAlive(true);
        this.openChannel();
        this.createReceiverThread(bl);
    }

    /*
     * Enabled aggressive block sorting
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private CommandResultMessage processReceivedMessages() {
        while (true) {
            Object object = this.RECEIVER_LOCK;
            // MONITORENTER : object
            if (this.receivedMessageList.isEmpty()) {
                // MONITOREXIT : object
                return null;
            }
            Message message = (Message)this.receivedMessageList.remove(0);
            // MONITOREXIT : object
            if (message instanceof CommandResultMessage) {
                return (CommandResultMessage)message;
            }
            this.runMessage(message);
        }
    }

    private CommandResultMessage runMessage(Message message) {
        CommandResultMessage commandResultMessage;
        if (IS_DEBUGGING_MESSAGES) {
            System.err.println(">RUN: " + SWTNativeInterface.getMessageID(message) + ", " + message);
        }
        if (message instanceof CommandMessage) {
            CommandMessage commandMessage = (CommandMessage)message;
            Object object = null;
            Throwable throwable = null;
            if (SWTNativeInterface.isMessageValid(message)) {
                try {
                    object = SWTNativeInterface.runMessageCommand(commandMessage);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                }
            }
            if (SWTNativeInterface.isMessageSyncExec(commandMessage)) {
                commandResultMessage = new CommandResultMessage(SWTNativeInterface.getMessageID(commandMessage), object, throwable);
                this.asyncSend(commandResultMessage);
            } else {
                if (throwable != null) {
                    throwable.printStackTrace();
                }
                commandResultMessage = new CommandResultMessage(SWTNativeInterface.getMessageID(message), object, throwable);
            }
        } else {
            commandResultMessage = new CommandResultMessage(SWTNativeInterface.getMessageID(message), null, null);
            if (SWTNativeInterface.isMessageSyncExec(message)) {
                this.asyncSend(commandResultMessage);
            }
        }
        if (IS_DEBUGGING_MESSAGES) {
            System.err.println("<RUN: " + SWTNativeInterface.getMessageID(message));
        }
        return commandResultMessage;
    }

    protected abstract void asyncUIExec(Runnable var1);

    protected boolean isNativeSide() {
        return this.isNativeSide;
    }

    public void checkUIThread() {
        if (!this.isUIThread()) {
            if (this.isNativeSide()) {
                SWT.error(22);
                return;
            }
            throw new IllegalStateException("This call must happen in the AWT Event Dispatch Thread! Please refer to http://java.sun.com/docs/books/tutorial/uiswing/concurrency/index.html and http://java.sun.com/javase/6/docs/api/javax/swing/SwingUtilities.html#invokeLater(java.lang.Runnable)");
        }
    }

    private Object nonUISyncExec(Message message) {
        ThreadLock threadLock = new ThreadLock(null);
        int n = this.syncThreadRegistry.add(threadLock);
        CM_asyncExec cM_asyncExec = new CM_asyncExec(null);
        SWTNativeInterface.setMessageArgs(cM_asyncExec, n, message, this.isNativeSide());
        this.asyncSend(cM_asyncExec);
        ThreadLock threadLock2 = threadLock;
        synchronized (threadLock2) {
            while (this.syncThreadRegistry.get(n) instanceof ThreadLock) {
                try {
                    threadLock.wait();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                if (this.isAlive()) continue;
                this.syncThreadRegistry.remove(n);
                this.printFailedInvocation(message);
                return null;
            }
            CommandResultMessage commandResultMessage = (CommandResultMessage)this.syncThreadRegistry.get(n);
            this.syncThreadRegistry.remove(n);
            return this.processCommandResult(commandResultMessage);
        }
    }

    public Object syncSend(Message message) {
        SWTNativeInterface.computeMessageID(message, !this.isNativeSide());
        if (!this.isUIThread()) {
            return this.nonUISyncExec(message);
        }
        Object object = this.LOCK;
        synchronized (object) {
            SWTNativeInterface.setMessageUI(message, true);
            SWTNativeInterface.setMessageSyncExec(message, true);
            if (!this.isAlive()) {
                this.printFailedInvocation(message);
                return null;
            }
            CommandResultMessage commandResultMessage = null;
            try {
                Object object2;
                ArrayList<CommandResultMessage> arrayList;
                block25: {
                    this.writeMessage(message);
                    arrayList = new ArrayList<CommandResultMessage>();
                    do {
                        if ((commandResultMessage = this.processReceivedMessages()) != null) {
                            if (commandResultMessage.getOriginalID() != SWTNativeInterface.getMessageID(message)) {
                                arrayList.add(commandResultMessage);
                                commandResultMessage = null;
                                continue;
                            }
                            break block25;
                        }
                        object2 = this.RECEIVER_LOCK;
                        synchronized (object2) {
                            boolean bl = true;
                            while (this.receivedMessageList.isEmpty()) {
                                Object object3;
                                if (!this.isAlive()) {
                                    this.printFailedInvocation(message);
                                    return null;
                                }
                                if (!bl) {
                                    bl = true;
                                    if (this.isNativeSide()) {
                                        SWTNativeInterface.getInstance().getDisplay().readAndDispatch();
                                    } else {
                                        object3 = Toolkit.getDefaultToolkit().getSystemEventQueue();
                                        AWTEvent aWTEvent = ((EventQueue)object3).peekEvent();
                                        if (aWTEvent != null && (aWTEvent = ((EventQueue)object3).getNextEvent()) != null) {
                                            Method method = EventQueue.class.getDeclaredMethod("dispatchEvent", AWTEvent.class);
                                            method.setAccessible(true);
                                            method.invoke(object3, aWTEvent);
                                        }
                                    }
                                }
                                bl = false;
                                this.isWaitingResponse = true;
                                if (this.isNativeSide()) {
                                    object3 = NSSystemPropertySWT.INTERFACE_SYNCSEND_NATIVE_TIMEOUT.get();
                                    if (object3 != null) {
                                        this.RECEIVER_LOCK.wait(Long.parseLong((String)object3));
                                    } else {
                                        this.RECEIVER_LOCK.wait(500L);
                                    }
                                } else {
                                    object3 = NSSystemPropertySWT.INTERFACE_SYNCSEND_LOCAL_TIMEOUT.get();
                                    if (object3 != null) {
                                        this.RECEIVER_LOCK.wait(Long.parseLong((String)object3));
                                    } else {
                                        this.RECEIVER_LOCK.wait(5000L);
                                    }
                                }
                                this.isWaitingResponse = false;
                            }
                        }
                    } while (this.isAlive());
                    this.printFailedInvocation(message);
                    return null;
                }
                object2 = this.RECEIVER_LOCK;
                synchronized (object2) {
                    if (!arrayList.isEmpty()) {
                        this.receivedMessageList.addAll(0, arrayList);
                    } else if (!this.receivedMessageList.isEmpty()) {
                        this.asyncUIExec(new lIIIIl(this));
                    }
                }
            }
            catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
            return this.processCommandResult(commandResultMessage);
        }
    }

    private Object processCommandResult(CommandResultMessage commandResultMessage) {
        Throwable throwable;
        if (IS_DEBUGGING_MESSAGES) {
            System.err.println("<USE: " + SWTNativeInterface.getMessageID(commandResultMessage));
        }
        if ((throwable = commandResultMessage.getException()) != null) {
            throw new RuntimeException(throwable);
        }
        return commandResultMessage.getResult();
    }

    public void asyncSend(Message message) {
        SWTNativeInterface.computeMessageID(message, !this.isNativeSide());
        SWTNativeInterface.setMessageUI(message, this.isUIThread());
        SWTNativeInterface.setMessageSyncExec(message, false);
        try {
            this.writeMessage(message);
        }
        catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    private void writeMessage(Message message) throws IOException {
        if (!this.isAlive()) {
            this.printFailedInvocation(message);
            return;
        }
        if (IS_DEBUGGING_MESSAGES) {
            System.err.println((SWTNativeInterface.isMessageSyncExec(message) ? "SENDS" : "SENDA") + ": " + SWTNativeInterface.getMessageID(message) + ", " + message);
        }
        this.writeMessageToChannel(message);
    }

    protected abstract void writeMessageToChannel(Message var1) throws IOException;

    protected abstract Message readMessageFromChannel() throws IOException, ClassNotFoundException;

    private void printFailedInvocation(Message message) {
        System.err.println("Failed messaging: " + message);
    }

    protected int getPID() {
        return this.pid;
    }

    private void createReceiverThread(boolean bl) {
        Thread thread = new Thread(this::lambda$createReceiverThread$0, "NativeSwing[" + this.pid + "] " + (this.isNativeSide() ? "SWT" : "Swing") + " Receiver");
        thread.setDaemon(true);
        thread.start();
    }

    protected void terminate() {
    }

    protected abstract void openChannel();

    protected abstract void closeChannel();

    private void lambda$createReceiverThread$0() {
        while (this.isAlive()) {
            Object object;
            Message message = null;
            try {
                message = this.readMessageFromChannel();
            }
            catch (Exception exception) {
                exception.printStackTrace();
                boolean bl = false;
                this.setAlive(false);
                bl = SWTNativeInterface.getInstance().notifyKilled();
                Object object2 = this.RECEIVER_LOCK;
                synchronized (object2) {
                    this.receivedMessageList.clear();
                    this.RECEIVER_LOCK.notify();
                }
                for (Object object3 : (Object)this.syncThreadRegistry.getInstanceIDs()) {
                    Object object4 = this.syncThreadRegistry.get((int)object3);
                    if (!(object4 instanceof ThreadLock)) continue;
                    Object object5 = object4;
                    synchronized (object5) {
                        object4.notify();
                    }
                }
                if (!bl) break;
                SWTNativeInterface.getInstance().notifyRespawned();
                break;
            }
            if (message == null) continue;
            if (!SWTNativeInterface.isMessageUI(message)) {
                object = message;
                new lIllll(this, "NativeSwing[" + this.getPID() + "] Non-UI Message [" + SWTNativeInterface.getMessageID(message) + "] Executor", (Message)object).start();
                continue;
            }
            object = this.RECEIVER_LOCK;
            synchronized (object) {
                this.receivedMessageList.add(message);
                if (this.isWaitingResponse) {
                    this.RECEIVER_LOCK.notify();
                } else if (this.receivedMessageList.size() == 1) {
                    this.asyncUIExec(new lll(this));
                }
            }
        }
        this.closeChannel();
    }

    static ObjectRegistry access$000(MessagingInterface messagingInterface) {
        return messagingInterface.syncThreadRegistry;
    }

    static CommandResultMessage access$200(MessagingInterface messagingInterface, Message message) {
        return messagingInterface.runMessage(message);
    }

    static CommandResultMessage access$500(MessagingInterface messagingInterface) {
        return messagingInterface.processReceivedMessages();
    }

    static Object access$600(MessagingInterface messagingInterface) {
        return messagingInterface.RECEIVER_LOCK;
    }

    static List access$700(MessagingInterface messagingInterface) {
        return messagingInterface.receivedMessageList;
    }

    private static class ThreadLock {
        private ThreadLock() {
        }

        ThreadLock(lIIIIl lIIIIl2) {
            this();
        }
    }

    private static class CM_asyncExec
    extends CommandMessage {
        private CM_asyncExec() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Message message = (Message)objectArray[1];
            boolean bl = (Boolean)objectArray[2];
            SWTNativeInterface.setMessageSyncExec(message, false);
            MessagingInterface messagingInterface = SWTNativeInterface.getInstance().getMessagingInterface(!bl);
            CM_asyncExecResponse cM_asyncExecResponse = new CM_asyncExecResponse(null);
            SWTNativeInterface.setMessageArgs(cM_asyncExecResponse, objectArray[0], MessagingInterface.access$200(messagingInterface, message), messagingInterface.isNativeSide());
            messagingInterface.asyncSend(cM_asyncExecResponse);
            return null;
        }

        CM_asyncExec(lIIIIl lIIIIl2) {
            this();
        }
    }

    private static class CM_asyncExecResponse
    extends CommandMessage {
        private CM_asyncExecResponse() {
        }

        @Override
        public Object run(Object[] objectArray) {
            int n = (Integer)objectArray[0];
            boolean bl = (Boolean)objectArray[2];
            MessagingInterface messagingInterface = SWTNativeInterface.getInstance().getMessagingInterface(!bl);
            ThreadLock threadLock = (ThreadLock)MessagingInterface.access$000(messagingInterface).get(n);
            MessagingInterface.access$000(messagingInterface).remove(n);
            if (threadLock == null) {
                return null;
            }
            ThreadLock threadLock2 = threadLock;
            synchronized (threadLock2) {
                MessagingInterface.access$000(messagingInterface).add(objectArray[1], n);
                threadLock.notify();
            }
            return null;
        }

        CM_asyncExecResponse(lIIIIl lIIIIl2) {
            this();
        }
    }

    private static class CommandResultMessage
    extends Message {
        private final int originalID;
        private final Object result;
        private final Throwable exception;

        CommandResultMessage(int n, Object object, Throwable throwable) {
            this.originalID = n;
            this.result = object;
            this.exception = throwable;
        }

        int getOriginalID() {
            return this.originalID;
        }

        public Object getResult() {
            return this.result;
        }

        public Throwable getException() {
            return this.exception;
        }

        @Override
        public String toString() {
            return super.toString() + "(" + this.originalID + ")";
        }
    }
}

