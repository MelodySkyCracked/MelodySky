/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.core.MessagingInterface;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeInterface;
import chrriis.dj.nativeswing.swtimpl.core.lIlI;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.SwingUtilities;
import org.eclipse.swt.widgets.Display;

abstract class OutProcessSocketsMessagingInterface
extends MessagingInterface {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    private static final int OOS_RESET_THRESHOLD;
    private int oosByteCount;

    public OutProcessSocketsMessagingInterface(boolean bl, Socket socket, boolean bl2, int n) {
        super(bl, n);
        this.socket = socket;
        this.initialize(bl2);
    }

    @Override
    public void destroy() {
        this.setAlive(false);
        try {
            this.ois.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    protected void openChannel() {
        try {
            this.oos = new ObjectOutputStream(new lIlI(this, this.socket.getOutputStream()));
            this.oos.flush();
            this.ois = new ObjectInputStream(new BufferedInputStream(this.socket.getInputStream()));
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    @Override
    protected void closeChannel() {
        try {
            this.oos.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            this.ois.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        try {
            this.socket.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.socket = null;
    }

    @Override
    protected void writeMessageToChannel(Message message) throws IOException {
        ObjectOutputStream objectOutputStream = this.oos;
        synchronized (objectOutputStream) {
            this.oos.writeUnshared(message);
            this.oos.flush();
            if (this.oosByteCount > OOS_RESET_THRESHOLD) {
                this.oos.reset();
                this.oosByteCount = 0;
            }
        }
    }

    @Override
    protected Message readMessageFromChannel() throws IOException, ClassNotFoundException {
        Object object = this.ois.readUnshared();
        if (object instanceof Message) {
            Message message = (Message)object;
            if (IS_DEBUGGING_MESSAGES) {
                System.err.println("RECV: " + SWTNativeInterface.getMessageID(message) + ", " + message);
            }
            return message;
        }
        System.err.println("Unknown message: " + object);
        return null;
    }

    static int access$008(OutProcessSocketsMessagingInterface outProcessSocketsMessagingInterface) {
        return outProcessSocketsMessagingInterface.oosByteCount++;
    }

    static int access$000(OutProcessSocketsMessagingInterface outProcessSocketsMessagingInterface) {
        return outProcessSocketsMessagingInterface.oosByteCount;
    }

    static int access$002(OutProcessSocketsMessagingInterface outProcessSocketsMessagingInterface, int n) {
        outProcessSocketsMessagingInterface.oosByteCount = n;
        return outProcessSocketsMessagingInterface.oosByteCount;
    }

    static {
        String string = NSSystemPropertySWT.INTERFACE_STREAMRESETTHRESHOLD.get();
        OOS_RESET_THRESHOLD = string != null ? Integer.parseInt(string) : 500000;
    }

    static class SwingOutProcessSocketsMessagingInterface
    extends OutProcessSocketsMessagingInterface {
        private final Process process;

        public SwingOutProcessSocketsMessagingInterface(Socket socket, boolean bl, Process process, int n) {
            super(false, socket, bl, n);
            this.process = process;
        }

        @Override
        protected void asyncUIExec(Runnable runnable) {
            SwingUtilities.invokeLater(runnable);
        }

        @Override
        public boolean isUIThread() {
            return SwingUtilities.isEventDispatchThread();
        }

        @Override
        public void destroy() {
            super.destroy();
            if (this.process != null && Boolean.parseBoolean(NSSystemPropertySWT.INTERFACE_OUTPROCESS_SYNCCLOSING.get())) {
                while (true) {
                    try {
                        this.process.waitFor();
                    }
                    catch (InterruptedException interruptedException) {
                        continue;
                    }
                    break;
                }
            }
        }
    }

    static class SWTOutProcessSocketsMessagingInterface
    extends OutProcessSocketsMessagingInterface {
        private Display display;

        public SWTOutProcessSocketsMessagingInterface(Socket socket, boolean bl, Display display, int n) {
            super(true, socket, bl, n);
            this.display = display;
        }

        @Override
        protected void asyncUIExec(Runnable runnable) {
            this.display.asyncExec(runnable);
        }

        @Override
        public boolean isUIThread() {
            return Thread.currentThread() == this.display.getThread();
        }

        @Override
        protected void terminate() {
            if (this.isNativeSide() && Boolean.parseBoolean(NSSystemPropertySWT.PEERVM_DEBUG_PRINTSTOPMESSAGE.get())) {
                System.err.println("Stopping peer VM #" + this.getPID());
            }
            super.terminate();
        }
    }
}

