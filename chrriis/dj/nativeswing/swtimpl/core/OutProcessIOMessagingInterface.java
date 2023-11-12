/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.core.MessagingInterface;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeInterface;
import chrriis.dj.nativeswing.swtimpl.core.lIII;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import javax.swing.SwingUtilities;
import org.eclipse.swt.widgets.Display;

abstract class OutProcessIOMessagingInterface
extends MessagingInterface {
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private InputStream is;
    private OutputStream os;
    private static final int OOS_RESET_THRESHOLD;
    private int oosByteCount;

    public OutProcessIOMessagingInterface(boolean bl, InputStream inputStream, OutputStream outputStream, boolean bl2, int n) {
        super(bl, n);
        this.is = inputStream;
        this.os = outputStream;
        this.initialize(bl2);
    }

    @Override
    public void destroy() {
        this.setAlive(false);
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
    }

    @Override
    protected void openChannel() {
        try {
            this.oos = new ObjectOutputStream(new lIII(this, this.os));
            this.oos.flush();
            this.ois = new ObjectInputStream(new BufferedInputStream(this.is));
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
            this.is.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.is = null;
        try {
            this.os.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
        this.os = null;
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
        return null;
    }

    static int access$008(OutProcessIOMessagingInterface outProcessIOMessagingInterface) {
        return outProcessIOMessagingInterface.oosByteCount++;
    }

    static int access$000(OutProcessIOMessagingInterface outProcessIOMessagingInterface) {
        return outProcessIOMessagingInterface.oosByteCount;
    }

    static int access$002(OutProcessIOMessagingInterface outProcessIOMessagingInterface, int n) {
        outProcessIOMessagingInterface.oosByteCount = n;
        return outProcessIOMessagingInterface.oosByteCount;
    }

    static {
        String string = NSSystemPropertySWT.INTERFACE_STREAMRESETTHRESHOLD.get();
        OOS_RESET_THRESHOLD = string != null ? Integer.parseInt(string) : 500000;
    }

    static class SwingOutProcessIOMessagingInterface
    extends OutProcessIOMessagingInterface {
        private final Process process;

        public SwingOutProcessIOMessagingInterface(InputStream inputStream, OutputStream outputStream, boolean bl, Process process, int n) {
            super(false, inputStream, outputStream, bl, n);
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

    static class SWTOutProcessIOMessagingInterface
    extends OutProcessIOMessagingInterface {
        private Display display;

        public SWTOutProcessIOMessagingInterface(InputStream inputStream, OutputStream outputStream, boolean bl, Display display, int n) {
            super(true, inputStream, outputStream, bl, n);
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
    }
}

