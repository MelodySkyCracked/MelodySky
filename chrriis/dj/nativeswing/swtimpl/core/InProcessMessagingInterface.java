/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.core.MessagingInterface;
import chrriis.dj.nativeswing.swtimpl.core.NoSerializationTestMessage;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeComponent;
import chrriis.dj.nativeswing.swtimpl.core.lIIIl;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import javax.swing.SwingUtilities;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

abstract class InProcessMessagingInterface
extends MessagingInterface {
    private static final boolean IS_PRINTING_NON_SERIALIZABLE_MESSAGES = Boolean.parseBoolean(NSSystemPropertySWT.INTERFACE_INPROCESS_PRINTNONSERIALIZABLEMESSAGES.get());
    private volatile InProcessMessagingInterface mirrorMessagingInterface;
    private List sentMessageList = new LinkedList();

    public InProcessMessagingInterface(boolean bl, int n) {
        super(bl, n);
    }

    @Override
    public void destroy() {
        ObjectRegistry objectRegistry = SWTNativeComponent.getControlRegistry();
        for (int n : objectRegistry.getInstanceIDs()) {
            Control control = (Control)objectRegistry.get(n);
            objectRegistry.remove(n);
            control.getDisplay().asyncExec(new lIIIl(this, control));
        }
        Object object = this.getMirrorMessagingInterface();
        this.setAlive(false);
        ((MessagingInterface)object).setAlive(false);
        List list = this.sentMessageList;
        synchronized (list) {
            this.sentMessageList.notifyAll();
        }
        list = ((InProcessMessagingInterface)object).sentMessageList;
        synchronized (list) {
            ((InProcessMessagingInterface)object).sentMessageList.notifyAll();
        }
    }

    @Override
    protected void openChannel() {
    }

    @Override
    protected void closeChannel() {
    }

    protected void setMirrorMessagingInterface(InProcessMessagingInterface inProcessMessagingInterface) {
        this.mirrorMessagingInterface = inProcessMessagingInterface;
    }

    public InProcessMessagingInterface getMirrorMessagingInterface() {
        return this.mirrorMessagingInterface;
    }

    Message getNextMessage() {
        boolean bl = this.isAlive();
        List list = this.sentMessageList;
        synchronized (list) {
            while (this.sentMessageList.isEmpty()) {
                try {
                    this.sentMessageList.wait();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
                if (bl = this.isAlive()) continue;
            }
            if (!bl) {
                this.sentMessageList.clear();
                throw new IllegalStateException("The interface is closed.");
            }
            return (Message)this.sentMessageList.remove(0);
        }
    }

    @Override
    protected Message readMessageFromChannel() throws IOException, ClassNotFoundException {
        return this.mirrorMessagingInterface.getNextMessage();
    }

    @Override
    protected void writeMessageToChannel(Message message) throws IOException {
        Object object;
        if (IS_PRINTING_NON_SERIALIZABLE_MESSAGES && !(message instanceof NoSerializationTestMessage)) {
            object = new ObjectOutputStream(new ByteArrayOutputStream());
            try {
                ((ObjectOutputStream)object).writeObject(message);
            }
            catch (Exception exception) {
                System.err.println("Non-serializable message: " + message);
            }
            ((ObjectOutputStream)object).close();
        }
        object = this.sentMessageList;
        synchronized (object) {
            this.sentMessageList.add(message);
            this.sentMessageList.notifyAll();
        }
    }

    static class SwingInProcessMessagingInterface
    extends InProcessMessagingInterface {
        public SwingInProcessMessagingInterface(InProcessMessagingInterface inProcessMessagingInterface, int n) {
            super(false, n);
            this.setMirrorMessagingInterface(inProcessMessagingInterface);
            this.initialize(false);
        }

        @Override
        protected void asyncUIExec(Runnable runnable) {
            SwingUtilities.invokeLater(runnable);
        }

        @Override
        public boolean isUIThread() {
            return SwingUtilities.isEventDispatchThread();
        }
    }

    static class SWTInProcessMessagingInterface
    extends InProcessMessagingInterface {
        private Display display;

        public SWTInProcessMessagingInterface(Display display, int n) {
            super(true, n);
            this.display = display;
            this.setMirrorMessagingInterface(new SwingInProcessMessagingInterface(this, n));
            this.initialize(false);
        }

        @Override
        protected void asyncUIExec(Runnable runnable) {
            this.display.asyncExec(runnable);
        }

        @Override
        public boolean isUIThread() {
            return this.display.getThread() == Thread.currentThread();
        }
    }
}

