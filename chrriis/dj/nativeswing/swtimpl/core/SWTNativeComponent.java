/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sun.jna.Native
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.NativeComponentWrapper;
import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.common.UIUtils;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.jna.platform.WindowUtils;
import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.LocalMessage;
import chrriis.dj.nativeswing.swtimpl.Message;
import chrriis.dj.nativeswing.swtimpl.NSSystemPropertySWT;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.NativeInterfaceAdapter;
import chrriis.dj.nativeswing.swtimpl.NativeInterfaceListener;
import chrriis.dj.nativeswing.swtimpl.core.ControlCommandMessage;
import chrriis.dj.nativeswing.swtimpl.core.I;
import chrriis.dj.nativeswing.swtimpl.core.NoSerializationTestMessage;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeInterface;
import chrriis.dj.nativeswing.swtimpl.core.SWTUtils;
import chrriis.dj.nativeswing.swtimpl.core.lII;
import chrriis.dj.nativeswing.swtimpl.core.lIIII;
import chrriis.dj.nativeswing.swtimpl.core.lIIIII;
import chrriis.dj.nativeswing.swtimpl.core.lIIIlI;
import chrriis.dj.nativeswing.swtimpl.core.lIIl;
import chrriis.dj.nativeswing.swtimpl.core.lIIlII;
import chrriis.dj.nativeswing.swtimpl.core.lIl;
import chrriis.dj.nativeswing.swtimpl.core.lIlIII;
import chrriis.dj.nativeswing.swtimpl.core.lIlIl;
import chrriis.dj.nativeswing.swtimpl.core.lIllII;
import chrriis.dj.nativeswing.swtimpl.core.lIllIl;
import chrriis.dj.nativeswing.swtimpl.core.lIlll;
import chrriis.dj.nativeswing.swtimpl.core.llIII;
import chrriis.dj.nativeswing.swtimpl.core.llIIll;
import chrriis.dj.nativeswing.swtimpl.core.llIlI;
import chrriis.dj.nativeswing.swtimpl.core.llIlIl;
import chrriis.dj.nativeswing.swtimpl.core.llIll;
import chrriis.dj.nativeswing.swtimpl.core.llIllI;
import chrriis.dj.nativeswing.swtimpl.core.lllI;
import chrriis.dj.nativeswing.swtimpl.core.lllII;
import chrriis.dj.nativeswing.swtimpl.core.lllIII;
import chrriis.dj.nativeswing.swtimpl.core.lllIlI;
import chrriis.dj.nativeswing.swtimpl.core.llllI;
import chrriis.dj.nativeswing.swtimpl.core.llllII;
import com.sun.jna.Native;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MenuComponent;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.dnd.DragSource;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.peer.ComponentPeer;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FilterOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Drawable;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class SWTNativeComponent
extends NativeComponent {
    private static final boolean IS_PRINTING_FAILED_MESSAGES = Boolean.parseBoolean(NSSystemPropertySWT.COMPONENTS_DEBUG_PRINTFAILEDMESSAGES.get());
    private NativeComponentWrapper nativeComponentWrapper = new lIIII(this, this);
    private volatile List initializationCommandMessageList = new ArrayList();
    private int componentID;
    private volatile Thread resizeThread;
    private volatile Thread repaintThread;
    private int additionCount;
    private boolean isForcingInitialization;
    private Method getAWTHandleMethod;
    private NativeInterfaceListener nativeInterfaceListener;
    private boolean isNativePeerValid;
    private String invalidNativePeerText;
    private boolean isNativePeerInitialized;
    private boolean isNativePeerDisposed;
    private boolean isControlParentEnabled = true;
    private boolean isStoredInHiddenParent;
    protected EventListenerList listenerList = new EventListenerList();

    protected static ObjectRegistry getControlRegistry() {
        return NativeComponent.getControlRegistry();
    }

    protected static ObjectRegistry getNativeComponentRegistry() {
        return NativeComponent.getNativeComponentRegistry();
    }

    @Override
    public void runInSequence(Runnable runnable) {
        this.runSync(new CMLocal_runInSequence(this, null), runnable);
    }

    @Override
    public Object runSync(CommandMessage commandMessage, Object ... objectArray) {
        SWTNativeInterface sWTNativeInterface = SWTNativeInterface.getInstance();
        if (sWTNativeInterface.isAlive()) {
            sWTNativeInterface.checkUIThread(false);
        }
        if (commandMessage instanceof ControlCommandMessage) {
            ((ControlCommandMessage)commandMessage).setNativeComponent(this);
        }
        if (this.initializationCommandMessageList != null) {
            SWTNativeInterface.setMessageSyncExec(commandMessage, true);
            SWTNativeInterface.setMessageArgs(commandMessage, objectArray);
            this.initializationCommandMessageList.add(commandMessage);
            return null;
        }
        if (this != false) {
            SWTNativeInterface.setMessageArgs(commandMessage, objectArray);
            this.printFailedInvocation(commandMessage);
            return null;
        }
        try {
            return commandMessage.syncExec(true, objectArray);
        }
        catch (RuntimeException runtimeException) {
            this.processFailedMessageException(runtimeException, commandMessage);
            return null;
        }
    }

    @Override
    public void runAsync(CommandMessage commandMessage, Object ... objectArray) {
        SWTNativeInterface sWTNativeInterface = SWTNativeInterface.getInstance();
        if (sWTNativeInterface.isAlive()) {
            sWTNativeInterface.checkUIThread(false);
        }
        if (commandMessage instanceof ControlCommandMessage) {
            ((ControlCommandMessage)commandMessage).setNativeComponent(this);
        }
        if (this.initializationCommandMessageList != null) {
            SWTNativeInterface.setMessageSyncExec(commandMessage, false);
            SWTNativeInterface.setMessageArgs(commandMessage, objectArray);
            this.initializationCommandMessageList.add(commandMessage);
        } else if (this != false) {
            SWTNativeInterface.setMessageArgs(commandMessage, objectArray);
            this.printFailedInvocation(commandMessage);
        } else {
            commandMessage.asyncExec(true, objectArray);
        }
    }

    private void printFailedInvocation(Message message) {
        if (IS_PRINTING_FAILED_MESSAGES) {
            System.err.println("Failed message to " + this.getComponentDescription() + ": " + message);
        }
    }

    static Control[] getControls() {
        ArrayList<Control> arrayList = new ArrayList<Control>();
        ObjectRegistry objectRegistry = SWTNativeComponent.getControlRegistry();
        for (int n : objectRegistry.getInstanceIDs()) {
            Control control = (Control)objectRegistry.get(n);
            if (control == null) continue;
            arrayList.add(control);
        }
        return arrayList.toArray(new Control[0]);
    }

    @Override
    protected int getComponentID() {
        return this.componentID;
    }

    public SWTNativeComponent() {
        DnDHandler.access$600();
        this.componentID = SWTNativeComponent.getNativeComponentRegistry().add(this);
        this.addFocusListener(new lII(this));
        this.enableEvents(8L);
        this.setFocusable(true);
    }

    @Override
    protected void processKeyEvent(java.awt.event.KeyEvent keyEvent) {
        java.awt.event.KeyEvent keyEvent2 = keyEvent;
        if (!(keyEvent2 instanceof CKeyEvent)) {
            ComponentPeer componentPeer = WindowUtils.getPeer(this);
            if (componentPeer != null) {
                componentPeer.handleEvent(keyEvent);
            }
            keyEvent.consume();
            return;
        }
        super.processKeyEvent(keyEvent2);
    }

    @Override
    public void reshape(int n, int n2, int n3, int n4) {
        if (this.resizeThread == null && (n3 != this.getWidth() || n4 != this.getHeight())) {
            this.resizeThread = new lllII(this, "NativeSwing Resize");
            this.resizeThread.start();
        }
        super.reshape(n, n2, n3, n4);
    }

    private void applyPendingReshape() {
        if (this.resizeThread == null) {
            return;
        }
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(new llIllI(this));
            return;
        }
        if (this.resizeThread == null) {
            return;
        }
        this.resizeThread = null;
        if (this != false) {
            int n = this.getWidth();
            int n2 = this.getHeight();
            Point2D.Double double_ = UIUtils.getScaledFactor(this);
            if (double_.x != 1.0 || double_.y != 1.0) {
                n = (int)((double)n * double_.x);
                n2 = (int)((double)n2 * double_.y);
            }
            new CMN_reshape(null).asyncExec(this, n, n2);
        }
    }

    private void repaintNativeComponent() {
        if (this.repaintThread == null && this.getWidth() > 0 && this.getHeight() > 0) {
            this.repaintThread = new llIlI(this, "NativeSwing Repaint");
            this.repaintThread.start();
        }
    }

    private void applyPendingRepaint() {
        if (this.repaintThread == null) {
            return;
        }
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(new lIllIl(this));
            return;
        }
        if (this.repaintThread == null) {
            return;
        }
        this.repaintThread = null;
        if (this != false) {
            new CMN_redraw(null).asyncExec(this, new Object[0]);
        }
    }

    @Override
    public void repaint() {
        super.repaint();
        this.repaintNativeComponent();
    }

    private static Object[] getKeyEventArgs(KeyEvent keyEvent, int n) {
        return new Object[]{n, keyEvent.stateMask, Character.valueOf(keyEvent.character), keyEvent.keyCode};
    }

    private static Object[] getMouseEventArgs(Control control, org.eclipse.swt.events.MouseEvent mouseEvent, int n) {
        Integer n2;
        org.eclipse.swt.events.MouseEvent mouseEvent2 = (org.eclipse.swt.events.MouseEvent)control.getData("NS_LastMouseEvent");
        if (mouseEvent2 != null && (n2 = (Integer)control.getData("NS_LastMouseEventType")) == n && n != 507 && mouseEvent2.x == mouseEvent.x && mouseEvent2.y == mouseEvent.y && mouseEvent2.button == mouseEvent.button && mouseEvent2.count == mouseEvent.count && mouseEvent2.stateMask == mouseEvent.stateMask) {
            return null;
        }
        control.setData("NS_LastMouseEvent", mouseEvent);
        control.setData("NS_LastMouseEventType", n);
        mouseEvent2 = mouseEvent;
        return new Object[]{n, mouseEvent.x, mouseEvent.y, mouseEvent.button, mouseEvent.count, mouseEvent.stateMask, mouseEvent.display.getCursorLocation()};
    }

    private static void configureControl(Control control, int n) {
        control.setData("NS_ID", n);
        control.setData("NS_EnabledEventsMask", 0L);
        control.addMouseListener(new I(control));
        control.addMouseTrackListener(new llllII(control));
        control.addMouseMoveListener(new llIII(control));
        control.addMouseWheelListener(new lllIII(control));
        control.addKeyListener(new lIIIII(control));
    }

    @Override
    public synchronized void addMouseMotionListener(MouseMotionListener mouseMotionListener) {
        if (this.getMouseMotionListeners().length == 0 && mouseMotionListener != null) {
            this.runAsync(new CMN_setEventsEnabled(null), 32L, true);
        }
        super.addMouseMotionListener(mouseMotionListener);
    }

    @Override
    public synchronized void removeMouseMotionListener(MouseMotionListener mouseMotionListener) {
        super.removeMouseMotionListener(mouseMotionListener);
        if (this.getMouseMotionListeners().length == 0) {
            this.runAsync(new CMN_setEventsEnabled(null), 32L, false);
        }
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        if (this != false) {
            String string = this.invalidNativePeerText;
            if (string == null) {
                string = "Invalid " + this.getComponentDescription();
            }
            FontMetrics fontMetrics = graphics.getFontMetrics();
            BufferedReader bufferedReader = new BufferedReader(new StringReader(string));
            int n = fontMetrics.getHeight();
            int n2 = fontMetrics.getAscent();
            try {
                String string2;
                int n3 = 0;
                while ((string2 = bufferedReader.readLine()) != null) {
                    graphics.drawString(string2, 5, n2 + 5 + n * n3);
                    ++n3;
                }
            }
            catch (Exception exception) {}
        } else {
            this.nativeComponentWrapper.paintBackBuffer(graphics, false);
        }
    }

    @Override
    public void print(Graphics graphics) {
        BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), 2);
        this.paintComponent(bufferedImage);
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        bufferedImage.flush();
    }

    private void throwDuplicateCreationException() {
        this.isNativePeerValid = false;
        this.invalidNativePeerText = "Failed to create " + this.getComponentDescription() + "\n\nReason:\nThe native component cannot be removed then re-added to a component hierarchy.";
        this.repaint();
        throw new IllegalStateException("The native component cannot be removed then re-added to a component hierarchy! To allow such reparenting, the component must be created with the \"destroyOnFinalization\" constructor option.");
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (this.isStoredInHiddenParent) {
            return;
        }
        if (this.isForcingInitialization) {
            return;
        }
        if (this.isNativePeerDisposed) {
            this.throwDuplicateCreationException();
        }
        ++this.additionCount;
        SwingUtilities.invokeLater(new lllIlI(this));
    }

    @Override
    public void initializeNativePeer() {
        block5: {
            Window window;
            SWTNativeInterface sWTNativeInterface = SWTNativeInterface.getInstance();
            if (sWTNativeInterface.isAlive()) {
                sWTNativeInterface.checkUIThread(false);
            }
            if ((window = SwingUtilities.getWindowAncestor(this)) == null) {
                throw new IllegalStateException("This method can only be called when the component has a Window ancestor!");
            }
            if (this.isNativePeerDisposed) {
                this.throwDuplicateCreationException();
            }
            if (this.isNativePeerInitialized) break block5;
            this.isForcingInitialization = true;
            if (Utils.IS_MAC) {
                class MacWindowInitMessage
                extends CommandMessage
                implements NoSerializationTestMessage {
                    final Window val$windowAncestor;
                    final SWTNativeComponent this$0;

                    MacWindowInitMessage(SWTNativeComponent sWTNativeComponent, Window window) {
                        this.this$0 = sWTNativeComponent;
                        this.val$windowAncestor = window;
                    }

                    @Override
                    public Object run(Object[] objectArray) throws Exception {
                        this.val$windowAncestor.addNotify();
                        return null;
                    }
                }
                new MacWindowInitMessage(this, window).syncSend(true);
            } else {
                window.addNotify();
            }
            this.createNativePeer();
            this.isForcingInitialization = false;
        }
    }

    private Object getHandle() {
        if (SWTNativeInterface.getInstance().isInProcess_()) {
            return this;
        }
        try {
            if (this.getAWTHandleMethod == null) {
                Method method = SWT_AWT.class.getDeclaredMethod("loadLibrary", new Class[0]);
                method.setAccessible(true);
                method.invoke(null, new Object[0]);
                this.getAWTHandleMethod = SWT_AWT.class.getDeclaredMethod("getAWTHandle", Canvas.class);
                this.getAWTHandleMethod.setAccessible(true);
            }
            return this.getAWTHandleMethod.invoke(null, this);
        }
        catch (Exception exception) {
            try {
                if (this.isDisplayable()) {
                    return Native.getComponentID((Component)this);
                }
            }
            catch (Exception exception2) {
                exception2.printStackTrace();
            }
            return 0;
        }
    }

    @Override
    protected Object[] getNativePeerCreationParameters() {
        return null;
    }

    private void createNativePeer() {
        SWTNativeInterface sWTNativeInterface = SWTNativeInterface.getInstance();
        boolean bl = sWTNativeInterface.isAlive();
        if (bl) {
            sWTNativeInterface.checkUIThread(false);
        }
        if (this.initializationCommandMessageList == null) {
            this.throwDuplicateCreationException();
        }
        List list = this.initializationCommandMessageList;
        this.initializationCommandMessageList = null;
        if (this.isNativePeerDisposed) {
            this.invalidNativePeerText = "Failed to create " + this.getComponentDescription() + "\n\nReason:\nThe native peer was disposed!";
        } else {
            this.isNativePeerInitialized = true;
            if (bl) {
                Serializable serializable;
                this.nativeInterfaceListener = new NNativeInterfaceListener(this);
                NativeInterface.addNativeInterfaceListener(this.nativeInterfaceListener);
                this.isNativePeerValid = true;
                try {
                    this.runSync(new CMN_createControl(null), this.componentID, this.getHandle(), this.getClass().getName(), this.getNativePeerCreationParameters());
                }
                catch (Exception exception) {
                    this.isNativePeerValid = false;
                    StringBuilder stringBuilder = new StringBuilder();
                    for (serializable = exception; serializable != null; serializable = serializable.getCause()) {
                        stringBuilder.append("    " + serializable.toString() + "\n");
                    }
                    this.invalidNativePeerText = "Failed to create " + this.getComponentDescription() + "\n\nReason:\n" + stringBuilder.toString();
                    exception.printStackTrace();
                }
                int n = this.getWidth();
                int n2 = this.getHeight();
                serializable = UIUtils.getScaledFactor(this);
                if (((Point2D.Double)serializable).x != 1.0 || ((Point2D.Double)serializable).y != 1.0) {
                    n = (int)((double)n * ((Point2D.Double)serializable).x);
                    n2 = (int)((double)n2 * ((Point2D.Double)serializable).y);
                }
                new CMN_reshape(null).asyncExec(this, n, n2);
            } else {
                this.invalidNativePeerText = "Failed to create " + this.getComponentDescription() + "\n\nReason:\nThe native interface is not open!";
            }
        }
        try {
            Thread.sleep(100L);
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
        for (CommandMessage commandMessage : list) {
            if (this != false) {
                this.printFailedInvocation(commandMessage);
                continue;
            }
            if (SWTNativeInterface.isMessageSyncExec(commandMessage)) {
                try {
                    commandMessage.syncSend(true);
                }
                catch (RuntimeException runtimeException) {
                    this.processFailedMessageException(runtimeException, commandMessage);
                }
                continue;
            }
            commandMessage.asyncSend(true);
        }
    }

    private void processFailedMessageException(RuntimeException runtimeException, CommandMessage commandMessage) {
        boolean bl = false;
        for (Throwable throwable = runtimeException; throwable != null; throwable = throwable.getCause()) {
            if (!(throwable instanceof ControlCommandMessage.DisposedControlException)) continue;
            bl = true;
            break;
        }
        if (!bl && Boolean.parseBoolean(NSSystemPropertySWT.COMPONENTS_SWALLOWRUNTIMEEXCEPTIONS.get())) {
            runtimeException.printStackTrace();
            bl = true;
        }
        if (!bl) {
            throw runtimeException;
        }
        this.printFailedInvocation(commandMessage);
    }

    @Override
    public void removeNotify() {
        if (this.isStoredInHiddenParent) {
            super.removeNotify();
            return;
        }
        this.disposeNativePeer();
        super.removeNotify();
    }

    @Override
    protected void disposeNativePeer() {
        if (!this.isNativePeerDisposed) {
            this.isNativePeerDisposed = true;
            if (this.isNativePeerInitialized) {
                NativeInterface.removeNativeInterfaceListener(this.nativeInterfaceListener);
                if (this != false) {
                    this.runSync(new CMN_destroyControl(null), new Object[0]);
                }
            }
            this.invalidateNativePeer("The native component was disposed.");
            SWTNativeComponent.getNativeComponentRegistry().remove(this.componentID);
            this.nativeComponentWrapper.disposeNativeComponent();
        }
    }

    @Override
    public boolean isNativePeerDisposed() {
        return this.isNativePeerDisposed;
    }

    @Override
    public boolean isNativePeerInitialized() {
        return this.isNativePeerInitialized;
    }

    private void invalidateNativePeer(String string) {
        if (this.isNativePeerValid) {
            this.isNativePeerValid = false;
            this.invalidNativePeerText = "Invalid " + this.getComponentDescription() + "\n\nReason:\n" + string;
            this.repaint();
        }
    }

    private String getComponentDescription() {
        return this.getClass().getName() + "[" + this.getComponentID() + "," + this.hashCode() + "]";
    }

    @Override
    public String toString() {
        return this.getComponentDescription();
    }

    @Override
    protected Component createEmbeddableComponent(Map map) {
        return this.nativeComponentWrapper.createEmbeddableComponent(map);
    }

    private boolean isControlParentEnabled() {
        return this.isControlParentEnabled;
    }

    private void setControlParentEnabled(boolean bl, boolean bl2) {
        if (bl == this.isControlParentEnabled) {
            return;
        }
        this.isControlParentEnabled = bl;
        if (!this.isNativePeerInitialized() || this != false) {
            this.runAsync(new CMN_setControlParentEnabled(null), bl, bl2);
        }
    }

    @Override
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        this.runAsync(new CMN_setEnabled(null), bl);
    }

    @Override
    public boolean hasFocus() {
        boolean bl = super.hasFocus();
        if (!bl && this != false && !this.isNativePeerDisposed) {
            return Boolean.TRUE.equals(new CMN_hasFocus(null).syncExec(this, new Object[0]));
        }
        return bl;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension dimension = null;
        if (this != false && !this.isNativePeerDisposed) {
            dimension = (Dimension)new CMN_getPreferredSize(null).syncExec(this, new Object[0]);
        }
        if (dimension == null) {
            dimension = super.getPreferredSize();
        }
        return dimension;
    }

    @Override
    public void paintComponent(BufferedImage bufferedImage) {
        this.paintComponent(bufferedImage, null);
    }

    @Override
    public void paintComponent(BufferedImage bufferedImage, Rectangle[] rectangleArray) {
        if (bufferedImage == null || this == false || this.isNativePeerDisposed) {
            return;
        }
        this.applyPendingReshape();
        int n = Math.min(this.getWidth(), bufferedImage.getWidth());
        int n2 = Math.min(this.getHeight(), bufferedImage.getHeight());
        if (n <= 0 || n2 <= 0) {
            return;
        }
        if (rectangleArray == null) {
            rectangleArray = new Rectangle[]{new Rectangle(n, n2)};
        }
        Rectangle rectangle = new Rectangle(n, n2);
        ArrayList<Rectangle> arrayList = new ArrayList<Rectangle>();
        for (Rectangle serializable : rectangleArray) {
            if (!serializable.intersects(rectangle)) continue;
            arrayList.add(serializable.intersection(rectangle));
        }
        if (arrayList.isEmpty()) {
            return;
        }
        rectangleArray = arrayList.toArray(new Rectangle[0]);
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(false);
            String string = Utils.getLocalHostAddress();
            if (string == null) {
                string = "127.0.0.1";
            }
            serverSocket.bind(new InetSocketAddress(InetAddress.getByName(string), 0));
            lIlll lIlll2 = new lIlll(this, serverSocket);
            CMN_getComponentImage cMN_getComponentImage = new CMN_getComponentImage(null);
            NativeInterface.addNativeInterfaceListener(lIlll2);
            AtomicReference<Boolean> atomicReference = new AtomicReference<Boolean>(true);
            if (Boolean.parseBoolean(NSSystemPropertySWT.COMPONENTS_USECOMPONENTIMAGECLOSINGTHREAD.get())) {
                new llIlIl(this, "NativeSwing[" + SWTNativeInterface.getInstance().getInterfaceID(false) + "] Component Image Socket Closing", atomicReference, serverSocket).start();
            }
            cMN_getComponentImage.asyncExec(this, serverSocket.getLocalPort(), rectangleArray, string);
            Socket socket = serverSocket.accept();
            atomicReference.set(false);
            byte[] byArray = new byte[3072];
            int n3 = 0;
            int n4 = 0;
            try {
                BufferedInputStream exception = new BufferedInputStream(socket.getInputStream());
                BufferedImage bufferedImage2 = bufferedImage;
                synchronized (bufferedImage2) {
                    for (Rectangle rectangle2 : rectangleArray) {
                        int[] nArray = new int[rectangle2.width];
                        for (int i = 0; i < rectangle2.height && n4 != -1; ++i) {
                            for (int j = 0; j < rectangle2.width && n4 != -1; ++j) {
                                int n5 = 0;
                                while (n4 != -1 && (n4 == 0 || n4 % 3 != 0)) {
                                    int n6;
                                    if (n5++ == 1000) {
                                        n4 = -1;
                                    }
                                    if ((n6 = exception.read(byArray, n4, byArray.length - n4)) == -1) {
                                        n4 = -1;
                                        continue;
                                    }
                                    n4 += n6;
                                }
                                if (n4 == -1) break;
                                nArray[j] = 0xFF000000 | (0xFF & byArray[n3]) << 16 | (0xFF & byArray[n3 + 1]) << 8 | 0xFF & byArray[n3 + 2];
                                if ((n3 += 3) != n4) continue;
                                n3 = 0;
                                n4 = 0;
                            }
                            if (n4 == -1) continue;
                            bufferedImage.setRGB(rectangle2.x, rectangle2.y + i, rectangle2.width, 1, nArray, 0, rectangle2.width);
                        }
                        if (n4 == -1) break;
                    }
                }
                NativeInterface.removeNativeInterfaceListener(lIlll2);
                exception.close();
                socket.close();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            serverSocket.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void storeInHiddenParent() {
        this.isStoredInHiddenParent = true;
        this.runSync(new CMN_reparentToHiddenShell(null), this.componentID, this.getHandle());
    }

    private void restoreFromHiddenParent() {
        if (!this.isDisplayable()) {
            this.isStoredInHiddenParent = false;
            return;
        }
        try {
            this.runSync(new CMN_createControl(null), this.componentID, this.getHandle());
            int n = this.getWidth();
            int n2 = this.getHeight();
            Point2D.Double double_ = UIUtils.getScaledFactor(this);
            if (double_.x != 1.0 || double_.y != 1.0) {
                n = (int)((double)n * double_.x);
                n2 = (int)((double)n2 * double_.y);
            }
            new CMN_reshape(null).asyncExec(this, n, n2);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Throwable throwable = exception; throwable != null; throwable = throwable.getCause()) {
                stringBuilder.append("    " + throwable.toString() + "\n");
            }
            this.invalidateNativePeer("Failed to reparent " + this.getComponentDescription() + "\n\nReason:\n" + stringBuilder.toString());
            exception.printStackTrace();
        }
        this.isStoredInHiddenParent = false;
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(0, 0);
    }

    @Override
    public void createBackBuffer() {
        this.nativeComponentWrapper.createBackBuffer();
    }

    @Override
    public boolean hasBackBuffer() {
        return this.nativeComponentWrapper.hasBackBuffer();
    }

    @Override
    public void updateBackBufferOnVisibleTranslucentAreas() {
        this.nativeComponentWrapper.updateBackBufferOnVisibleTranslucentAreas();
    }

    @Override
    public void updateBackBuffer(Rectangle[] rectangleArray) {
        this.nativeComponentWrapper.updateBackBuffer(rectangleArray);
    }

    @Override
    public void destroyBackBuffer() {
        this.nativeComponentWrapper.destroyBackBuffer();
    }

    public EventListener[] getListeners(Class clazz) {
        EventListener[] eventListenerArray = this.listenerList.getListeners(clazz);
        if (eventListenerArray.length == 0) {
            return super.getListeners(clazz);
        }
        return eventListenerArray;
    }

    @Override
    public java.awt.Point getLocationOnScreen() {
        Container container = this.getParent();
        if (container != null) {
            java.awt.Point point = container.getLocationOnScreen();
            point.x += this.getX();
            point.y += this.getY();
            return point;
        }
        return super.getLocationOnScreen();
    }

    static String access$000(SWTNativeComponent sWTNativeComponent) {
        return sWTNativeComponent.getComponentDescription();
    }

    static void access$100(SWTNativeComponent sWTNativeComponent, boolean bl, boolean bl2) {
        sWTNativeComponent.setControlParentEnabled(bl, bl2);
    }

    static boolean access$200(SWTNativeComponent sWTNativeComponent) {
        return sWTNativeComponent.isControlParentEnabled();
    }

    static void access$300(SWTNativeComponent sWTNativeComponent) {
        sWTNativeComponent.storeInHiddenParent();
    }

    static void access$400(SWTNativeComponent sWTNativeComponent) {
        sWTNativeComponent.restoreFromHiddenParent();
    }

    static void access$800(SWTNativeComponent sWTNativeComponent) {
        sWTNativeComponent.applyPendingReshape();
    }

    static void access$1000(SWTNativeComponent sWTNativeComponent) {
        sWTNativeComponent.applyPendingRepaint();
    }

    static NativeComponentWrapper access$1200(SWTNativeComponent sWTNativeComponent) {
        return sWTNativeComponent.nativeComponentWrapper;
    }

    static void access$1300(Control control, int n) {
        SWTNativeComponent.configureControl(control, n);
    }

    static Object[] access$1400(Control control, org.eclipse.swt.events.MouseEvent mouseEvent, int n) {
        return SWTNativeComponent.getMouseEventArgs(control, mouseEvent, n);
    }

    static Object[] access$1600(KeyEvent keyEvent, int n) {
        return SWTNativeComponent.getKeyEventArgs(keyEvent, n);
    }

    static boolean access$1900(SWTNativeComponent sWTNativeComponent) {
        return sWTNativeComponent.isNativePeerDisposed;
    }

    static int access$2000(SWTNativeComponent sWTNativeComponent) {
        return sWTNativeComponent.additionCount;
    }

    static void access$2100(SWTNativeComponent sWTNativeComponent) {
        sWTNativeComponent.throwDuplicateCreationException();
    }

    static boolean access$2200(SWTNativeComponent sWTNativeComponent) {
        return sWTNativeComponent.isNativePeerInitialized;
    }

    static void access$2300(SWTNativeComponent sWTNativeComponent) {
        sWTNativeComponent.createNativePeer();
    }

    static void access$2400(SWTNativeComponent sWTNativeComponent, String string) {
        sWTNativeComponent.invalidateNativePeer(string);
    }

    static boolean access$3700(SWTNativeComponent sWTNativeComponent) {
        return sWTNativeComponent.isControlParentEnabled;
    }

    private static class DnDHandler {
        private static boolean isDnDActive;
        private static DnDHandler dndHandler;
        private SWTNativeComponent[] nativeComponents;
        private boolean[] wereEnabled;

        private DnDHandler() {
        }

        private static void activateDragAndDrop() {
            if (isDnDActive) {
                return;
            }
            isDnDActive = true;
            DragSource dragSource = DragSource.getDefaultDragSource();
            lIIIlI lIIIlI2 = new lIIIlI();
            dragSource.addDragSourceMotionListener(lIIIlI2);
            dragSource.addDragSourceListener(lIIIlI2);
        }

        static void access$600() {
            DnDHandler.activateDragAndDrop();
        }

        static DnDHandler access$3500() {
            return dndHandler;
        }

        static DnDHandler access$3502(DnDHandler dnDHandler) {
            dndHandler = dnDHandler;
            return dndHandler;
        }

        DnDHandler(lIIII lIIII2) {
            this();
        }

        static SWTNativeComponent[] access$3802(DnDHandler dnDHandler, SWTNativeComponent[] sWTNativeComponentArray) {
            dnDHandler.nativeComponents = sWTNativeComponentArray;
            return sWTNativeComponentArray;
        }

        static boolean[] access$3902(DnDHandler dnDHandler, boolean[] blArray) {
            dnDHandler.wereEnabled = blArray;
            return blArray;
        }

        static SWTNativeComponent[] access$3800(DnDHandler dnDHandler) {
            return dnDHandler.nativeComponents;
        }

        static boolean[] access$3900(DnDHandler dnDHandler) {
            return dnDHandler.wereEnabled;
        }
    }

    private static class CMN_redraw
    extends ControlCommandMessage {
        private CMN_redraw() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Control control = this.getControl();
            if (control.isDisposed()) {
                return null;
            }
            Point point = control.getSize();
            control.redraw(0, 0, point.x, point.y, true);
            return null;
        }

        CMN_redraw(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_reparentToHiddenShell
    extends ControlCommandMessage {
        private CMN_reparentToHiddenShell() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Control control = this.getControl();
            Shell shell = control.getShell();
            Shell shell2 = new Shell(control.getDisplay(), 0);
            control.getParent().setParent(shell2);
            shell.dispose();
            return null;
        }

        CMN_reparentToHiddenShell(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_getComponentImage
    extends ControlCommandMessage {
        private CMN_getComponentImage() {
        }

        @Override
        protected boolean isValid() {
            return true;
        }

        private static boolean printRemoveClip(Control control, GC gC) {
            boolean bl = control.isFocusControl();
            org.eclipse.swt.graphics.Rectangle rectangle = control.getBounds();
            Display display = control.getDisplay();
            Composite composite = control.getParent();
            Shell shell = new Shell(786440);
            Shell shell2 = new Shell(shell, 786440);
            Point point = display.map(control, null, control.getLocation());
            shell2.setLocation(point);
            shell2.setSize(rectangle.width, rectangle.height);
            org.eclipse.swt.widgets.Canvas canvas = new org.eclipse.swt.widgets.Canvas(shell2, 262144);
            canvas.setSize(rectangle.width, rectangle.height);
            GC gC2 = new GC((Drawable)display);
            Image image = new Image((Device)display, rectangle.width, rectangle.height);
            gC2.copyArea(image, point.x, point.y);
            gC2.dispose();
            lllI lllI2 = new lllI(image);
            shell2.addPaintListener(lllI2);
            canvas.addPaintListener(lllI2);
            composite.addPaintListener(lllI2);
            org.eclipse.swt.widgets.Canvas canvas2 = new org.eclipse.swt.widgets.Canvas(composite, 262144);
            canvas2.setSize(rectangle.width, rectangle.height);
            canvas2.addPaintListener(lllI2);
            control.setRedraw(false);
            composite.setRedraw(false);
            control.setParent(shell2);
            control.setLocation(0, 0);
            control.moveBelow(canvas);
            shell2.setVisible(true);
            boolean bl2 = control.print(gC);
            if (composite.isDisposed()) {
                control.dispose();
            } else {
                control.setParent(composite);
                if (bl && !control.isFocusControl()) {
                    control.setFocus();
                }
                control.setLocation(rectangle.x, rectangle.y);
                control.moveAbove(canvas2);
                canvas2.dispose();
                composite.removePaintListener(lllI2);
                shell2.dispose();
                shell.dispose();
                composite.setRedraw(true);
                control.setRedraw(true);
                control.redraw();
                control.update();
                image.dispose();
            }
            return bl2;
        }

        private ImageData getImageData(Control control, Region region) {
            Object object;
            if (control.isDisposed()) {
                return null;
            }
            Point point = control.getSize();
            if (point.x <= 0 || point.y <= 0) {
                return null;
            }
            org.eclipse.swt.graphics.Rectangle rectangle = region.getBounds();
            Display display = control.getDisplay();
            Image image = new Image((Device)display, rectangle.x + rectangle.width, rectangle.y + rectangle.height);
            GC gC = new GC(image);
            gC.setClipping(region);
            if (Boolean.parseBoolean(NSSystemPropertySWT.COMPONENTS_PRINTINGHACK.get())) {
                CMN_getComponentImage.printRemoveClip(control, gC);
            } else if (Utils.IS_WINDOWS) {
                object = control.getBounds();
                boolean bl = control.isFocusControl();
                Composite composite = control.getParent();
                control.setRedraw(false);
                composite.setRedraw(false);
                control.print(gC);
                if (bl && !control.isFocusControl()) {
                    control.setFocus();
                }
                control.setLocation(((org.eclipse.swt.graphics.Rectangle)object).x, ((org.eclipse.swt.graphics.Rectangle)object).y);
                composite.setRedraw(true);
                control.setRedraw(true);
                control.redraw(0, 0, ((org.eclipse.swt.graphics.Rectangle)object).width, ((org.eclipse.swt.graphics.Rectangle)object).height, true);
                control.update();
            } else {
                control.print(gC);
            }
            gC.dispose();
            object = image.getImageData();
            image.dispose();
            return object;
        }

        @Override
        public Object run(Object[] objectArray) throws Exception {
            ImageData imageData;
            int n = (Integer)objectArray[0];
            Rectangle[] rectangleArray = (Rectangle[])objectArray[1];
            String string = (String)objectArray[2];
            Control control = this.getControl();
            Region region = new Region();
            for (Rectangle rectangle : rectangleArray) {
                region.add(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            }
            if (!NativeInterface.isUIThread(true)) {
                AtomicReference atomicReference = new AtomicReference();
                AtomicReference atomicReference2 = new AtomicReference();
                if (control == null || control.isDisposed()) {
                    new Socket(string, n).close();
                    return null;
                }
                AtomicReference<Boolean> atomicReference3 = new AtomicReference<Boolean>(false);
                control.getDisplay().syncExec(new lIl(this, control, string, n, atomicReference3, atomicReference2, region, atomicReference));
                if (!atomicReference3.get().booleanValue() && control.isDisposed()) {
                    new Socket(string, n).close();
                    return null;
                }
                if (atomicReference.get() != null) {
                    new Socket(string, n).close();
                    throw (Exception)atomicReference.get();
                }
                imageData = (ImageData)atomicReference2.get();
            } else {
                imageData = this.getImageData(control, region);
            }
            region.dispose();
            if (imageData == null) {
                new Socket(string, n).close();
                return null;
            }
            this.sendImageData(string, n, imageData, rectangleArray);
            return null;
        }

        private void sendImageData(String string, int n, ImageData imageData, Rectangle[] rectangleArray) {
            if (NativeInterface.isUIThread(true)) {
                new lIIl(this, "NativeSwing[" + SWTNativeInterface.getInstance().getInterfaceID(true) + "] Component Image Data Transfer", string, n, imageData, rectangleArray).start();
                return;
            }
            int n2 = 0;
            byte[] byArray = new byte[3072];
            PaletteData paletteData = imageData.palette;
            Socket socket = null;
            FilterOutputStream filterOutputStream = null;
            int n3 = imageData.width;
            int n4 = imageData.height;
            try {
                socket = new Socket(string, n);
                filterOutputStream = new BufferedOutputStream(socket.getOutputStream());
                for (Rectangle rectangle : rectangleArray) {
                    for (int i = 0; i < rectangle.height; ++i) {
                        int n5 = rectangle.y + i;
                        for (int j = 0; j < rectangle.width; ++j) {
                            int n6 = rectangle.x + j;
                            if (n6 < n3 && n5 < n4) {
                                int n7 = imageData.getPixel(n6, n5);
                                if (paletteData.isDirect) {
                                    int n8 = n7 & paletteData.redMask;
                                    byArray[n2++] = (byte)(paletteData.redShift < 0 ? n8 >>> -paletteData.redShift : n8 << paletteData.redShift);
                                    int n9 = n7 & paletteData.greenMask;
                                    byArray[n2++] = (byte)(paletteData.greenShift < 0 ? n9 >>> -paletteData.greenShift : n9 << paletteData.greenShift);
                                    int n10 = n7 & paletteData.blueMask;
                                    byArray[n2++] = (byte)(paletteData.blueShift < 0 ? n10 >>> -paletteData.blueShift : n10 << paletteData.blueShift);
                                } else {
                                    RGB rGB = paletteData.colors[n7];
                                    byArray[n2++] = (byte)rGB.red;
                                    byArray[n2++] = (byte)rGB.green;
                                    byArray[n2++] = (byte)rGB.blue;
                                }
                            } else {
                                n2 += 3;
                            }
                            if (n2 != byArray.length) continue;
                            filterOutputStream.write(byArray);
                            n2 = 0;
                        }
                    }
                }
                ((BufferedOutputStream)filterOutputStream).write(byArray, 0, n2);
                ((BufferedOutputStream)filterOutputStream).flush();
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
            try {
                if (filterOutputStream != null) {
                    filterOutputStream.close();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }

        static ImageData access$3100(CMN_getComponentImage cMN_getComponentImage, Control control, Region region) {
            return cMN_getComponentImage.getImageData(control, region);
        }

        static void access$3200(CMN_getComponentImage cMN_getComponentImage, String string, int n, ImageData imageData, Rectangle[] rectangleArray) {
            cMN_getComponentImage.sendImageData(string, n, imageData, rectangleArray);
        }

        CMN_getComponentImage(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_getPreferredSize
    extends ControlCommandMessage {
        private CMN_getPreferredSize() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Control control = this.getControl();
            if (control.isDisposed()) {
                return null;
            }
            if (!NativeInterface.isUIThread(true)) {
                AtomicReference atomicReference = new AtomicReference();
                control.getDisplay().syncExec(new llllI(this, control, atomicReference));
                return atomicReference.get();
            }
            Point point = control.computeSize(-1, -1);
            return new Dimension(point.x, point.y);
        }

        CMN_getPreferredSize(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_hasFocus
    extends ControlCommandMessage {
        private CMN_hasFocus() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Control control = this.getControl();
            if (control.isDisposed()) {
                return false;
            }
            if (!NativeInterface.isUIThread(true)) {
                AtomicBoolean atomicBoolean = new AtomicBoolean();
                control.getDisplay().syncExec(new lIlIl(this, atomicBoolean, control));
                return atomicBoolean.get();
            }
            return control.isFocusControl();
        }

        CMN_hasFocus(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_setEnabled
    extends ControlCommandMessage {
        private CMN_setEnabled() {
        }

        @Override
        public Object run(Object[] objectArray) {
            this.getControl().setEnabled((Boolean)objectArray[0]);
            return null;
        }

        CMN_setEnabled(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_setControlParentEnabled
    extends ControlCommandMessage {
        private CMN_setControlParentEnabled() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Control control = this.getControl();
            if (control == null || control.isDisposed()) {
                return null;
            }
            control.getShell().setEnabled((Boolean)objectArray[0]);
            if (((Boolean)objectArray[1]).booleanValue()) {
                Point point = control.getParent().getSize();
                --point.y;
                control.setSize(point);
                lIIlII lIIlII2 = new lIIlII(this, "Native Swing Repaint fix", control);
                lIIlII2.setDaemon(true);
                lIIlII2.start();
            }
            return null;
        }

        CMN_setControlParentEnabled(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_destroyControl
    extends ControlCommandMessage {
        private CMN_destroyControl() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Control control = this.getControl();
            SWTNativeComponent.getControlRegistry().remove(this.getComponentID());
            if (control != null) {
                Shell shell = control.getShell();
                if (shell != null) {
                    shell.dispose();
                }
                control.dispose();
            }
            return null;
        }

        CMN_destroyControl(lIIII lIIII2) {
            this();
        }
    }

    private static class NNativeInterfaceListener
    extends NativeInterfaceAdapter {
        protected Reference nativeComponent;

        protected NNativeInterfaceListener(SWTNativeComponent sWTNativeComponent) {
            this.nativeComponent = new WeakReference<SWTNativeComponent>(sWTNativeComponent);
        }

        @Override
        public void nativeInterfaceClosed() {
            NativeInterface.removeNativeInterfaceListener(this);
            SwingUtilities.invokeLater(new lIlIII(this));
        }
    }

    private static class CMN_setEventsEnabled
    extends ControlCommandMessage {
        private CMN_setEventsEnabled() {
        }

        @Override
        public Object run(Object[] objectArray) {
            long l2 = (Long)objectArray[0];
            boolean bl = (Boolean)objectArray[1];
            long l3 = (Long)this.getControl().getData("NS_EnabledEventsMask");
            l3 = bl ? (l3 |= l2) : (l3 &= l2 ^ 0xFFFFFFFFFFFFFFFFL);
            this.getControl().setData("NS_EnabledEventsMask", l3);
            return null;
        }

        CMN_setEventsEnabled(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_createControl
    extends CommandMessage
    implements NoSerializationTestMessage {
        private CMN_createControl() {
        }

        private static Shell createShell(Object object) throws Exception {
            SWTNativeInterface sWTNativeInterface = SWTNativeInterface.getInstance();
            Display display = sWTNativeInterface.getDisplay();
            if (sWTNativeInterface.isInProcess_()) {
                Canvas canvas = (Canvas)object;
                ComponentListener[] componentListenerArray = canvas.getComponentListeners();
                Shell shell = SWT_AWT.new_Shell(display, canvas);
                for (ComponentListener componentListener : canvas.getComponentListeners()) {
                    canvas.removeComponentListener(componentListener);
                }
                for (ComponentListener componentListener : componentListenerArray) {
                    canvas.addComponentListener(componentListener);
                }
                return shell;
            }
            Method method = null;
            try {
                method = Shell.class.getMethod(SWT.getPlatform() + "_new", Display.class, Integer.TYPE);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (method != null) {
                return (Shell)method.invoke(null, display, ((Number)object).intValue());
            }
            try {
                method = Shell.class.getMethod(SWT.getPlatform() + "_new", Display.class, Long.TYPE);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (method != null) {
                return (Shell)method.invoke(null, display, ((Number)object).longValue());
            }
            Constructor constructor = null;
            try {
                constructor = Shell.class.getConstructor(Display.class, Shell.class, Integer.TYPE, Integer.TYPE);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (constructor != null) {
                constructor.setAccessible(true);
                return (Shell)constructor.newInstance(display, null, 8, ((Number)object).intValue());
            }
            try {
                constructor = Shell.class.getConstructor(Display.class, Shell.class, Integer.TYPE, Long.TYPE);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (constructor != null) {
                constructor.setAccessible(true);
                return (Shell)constructor.newInstance(display, null, 8, ((Number)object).longValue());
            }
            throw new IllegalStateException("Failed to create a Shell!");
        }

        @Override
        public Object run(Object[] objectArray) throws Exception {
            ObjectRegistry objectRegistry;
            ObjectRegistry objectRegistry2 = objectRegistry = SWTNativeComponent.getControlRegistry();
            synchronized (objectRegistry2) {
                int n = (Integer)objectArray[0];
                Object object = objectArray[1];
                Shell shell = CMN_createControl.createShell(object);
                shell.addControlListener(new llIIll(this));
                shell.setLayout(new FillLayout());
                Composite composite = new Composite(shell, 0);
                composite.setLayout(new FillLayout());
                Control control = (Control)objectRegistry.get(n);
                if (control != null) {
                    Shell shell2 = control.getShell();
                    control.setParent(composite);
                    shell2.dispose();
                } else {
                    String string = (String)objectArray[2];
                    Object object2 = objectArray[3];
                    Method method = Class.forName(string).getDeclaredMethod("createControl", Composite.class, Object[].class);
                    method.setAccessible(true);
                    control = (Control)method.invoke(null, composite, object2);
                    if (Boolean.parseBoolean(NSSystemPropertySWT.COMPONENTS_DEBUG_PRINTCREATION.get())) {
                        System.err.println("Created control: " + n);
                    }
                    control.addDisposeListener(new llIll(this, n));
                    objectRegistry.add(control, n);
                    SWTNativeComponent.access$1300(control, n);
                }
                shell.setVisible(true);
                shell.getDisplay().asyncExec(new lIllII(this, shell));
            }
            return null;
        }

        CMN_createControl(lIIII lIIII2) {
            this();
        }
    }

    private static class CKeyEvent
    extends java.awt.event.KeyEvent {
        public CKeyEvent(Component component, int n, long l2, int n2, int n3, char c) {
            super(component, n, l2, n2, n3, c);
        }

        @Override
        public String toString() {
            String string = null;
            if (this.source instanceof Component) {
                string = ((Component)this.source).getName();
            } else if (this.source instanceof MenuComponent) {
                string = ((MenuComponent)this.source).getName();
            }
            return java.awt.event.KeyEvent.class.getName() + "[" + this.paramString() + "] on " + (string != null ? string : this.source);
        }
    }

    private static class CMJ_dispatchKeyEvent
    extends ControlCommandMessage {
        private CMJ_dispatchKeyEvent() {
        }

        @Override
        public Object run(Object[] objectArray) {
            int n;
            NativeComponent nativeComponent = this.getNativeComponent();
            if (nativeComponent == null || !nativeComponent.isShowing()) {
                return null;
            }
            int n2 = (Integer)objectArray[0];
            int n3 = (Integer)objectArray[1];
            char c = ((Character)objectArray[2]).charValue();
            int n4 = (Integer)objectArray[3];
            if (n4 == 9) {
                if (n2 == 401 && (n3 & 0x40000) != 0) {
                    boolean bl = (n3 & 0x20000) == 0;
                    SWTNativeComponent.access$1200((SWTNativeComponent)nativeComponent).transferFocus(!bl);
                }
                return null;
            }
            char c2 = c;
            if (n2 == 400) {
                if (c2 == '\u0000') {
                    return null;
                }
                n = 0;
            } else {
                n = SWTUtils.translateSWTKeyCode(n4);
            }
            CKeyEvent cKeyEvent = new CKeyEvent(nativeComponent, n2, System.currentTimeMillis(), SWTUtils.translateSWTModifiers(n3), n, c2);
            nativeComponent.dispatchEvent(cKeyEvent);
            return null;
        }

        CMJ_dispatchKeyEvent(lIIII lIIII2) {
            this();
        }
    }

    private static class CMJ_dispatchMouseEvent
    extends ControlCommandMessage {
        private static int buttonPressedCount;

        private CMJ_dispatchMouseEvent() {
        }

        @Override
        public Object run(Object[] objectArray) {
            boolean bl;
            MouseEvent mouseEvent;
            NativeComponent nativeComponent = this.getNativeComponent();
            if (nativeComponent == null || !nativeComponent.isShowing()) {
                return null;
            }
            int n = (Integer)objectArray[0];
            int n2 = (Integer)objectArray[1];
            int n3 = (Integer)objectArray[2];
            Point2D.Double double_ = UIUtils.getScaledFactor(nativeComponent);
            if (double_.x != 1.0 || double_.y != 1.0) {
                n2 = (int)((double)n2 / double_.x);
                n3 = (int)((double)n3 / double_.y);
            }
            int n4 = (Integer)objectArray[3];
            int n5 = (Integer)objectArray[4];
            int n6 = (Integer)objectArray[5];
            Point point = (Point)objectArray[6];
            if (double_.x != 1.0 || double_.y != 1.0) {
                point = new Point((int)((double)point.x / double_.x), (int)((double)point.y / double_.y));
            }
            switch (n) {
                case 501: {
                    ++buttonPressedCount;
                    break;
                }
                case 502: {
                    if (--buttonPressedCount >= 0) break;
                    buttonPressedCount = 0;
                    break;
                }
            }
            int n7 = SWTUtils.translateSWTMouseButton(n4);
            if (n7 == 0) {
                switch (n) {
                    case 500: 
                    case 501: 
                    case 502: {
                        return null;
                    }
                }
            }
            if (buttonPressedCount != 0 && n == 503) {
                n = 506;
            }
            if (Utils.IS_JAVA_6_OR_GREATER) {
                if (n == 507) {
                    mouseEvent = new MouseWheelEvent(nativeComponent, n, System.currentTimeMillis(), SWTUtils.translateSWTModifiers(n6), n2, n3, point.x, point.y, 0, false, 0, Math.abs(n5), n5 < 0 ? 1 : -1);
                } else {
                    bl = n == 502 && n7 == 3;
                    mouseEvent = new MouseEvent(nativeComponent, n, System.currentTimeMillis(), SWTUtils.translateSWTModifiers(n6), n2, n3, point.x, point.y, n5, bl, n7);
                }
            } else if (n == 507) {
                mouseEvent = new MouseWheelEvent((Component)nativeComponent, n, System.currentTimeMillis(), SWTUtils.translateSWTModifiers(n6), n2, n3, 0, false, 0, Math.abs(n5), n5 < 0 ? 1 : -1);
            } else {
                bl = n == 502 && n7 == 3;
                mouseEvent = new MouseEvent(nativeComponent, n, System.currentTimeMillis(), SWTUtils.translateSWTModifiers(n6), n2, n3, n5, bl, n7);
            }
            switch (mouseEvent.getID()) {
                case 501: {
                    MenuSelectionManager.defaultManager().clearSelectedPath();
                    for (MouseListener mouseListener : nativeComponent.getMouseListeners()) {
                        mouseListener.mousePressed(mouseEvent);
                    }
                    break;
                }
                case 502: {
                    for (MouseListener mouseListener : nativeComponent.getMouseListeners()) {
                        mouseListener.mouseReleased(mouseEvent);
                    }
                    break;
                }
                case 500: {
                    for (MouseListener mouseListener : nativeComponent.getMouseListeners()) {
                        mouseListener.mouseClicked(mouseEvent);
                    }
                    break;
                }
                case 504: {
                    for (MouseListener mouseListener : nativeComponent.getMouseListeners()) {
                        mouseListener.mouseEntered(mouseEvent);
                    }
                    break;
                }
                case 505: {
                    for (MouseListener mouseListener : nativeComponent.getMouseListeners()) {
                        mouseListener.mouseExited(mouseEvent);
                    }
                    break;
                }
                case 503: {
                    for (MouseMotionListener mouseMotionListener : nativeComponent.getMouseMotionListeners()) {
                        mouseMotionListener.mouseMoved(mouseEvent);
                    }
                    break;
                }
                case 506: {
                    for (MouseMotionListener mouseMotionListener : nativeComponent.getMouseMotionListeners()) {
                        mouseMotionListener.mouseDragged(mouseEvent);
                    }
                    break;
                }
                case 507: {
                    for (MouseWheelListener mouseWheelListener : nativeComponent.getMouseWheelListeners()) {
                        mouseWheelListener.mouseWheelMoved((MouseWheelEvent)mouseEvent);
                    }
                    break;
                }
            }
            return null;
        }

        CMJ_dispatchMouseEvent(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_transferFocus
    extends ControlCommandMessage {
        private CMN_transferFocus() {
        }

        @Override
        public Object run(Object[] objectArray) {
            this.getControl().traverse(16);
            return null;
        }

        CMN_transferFocus(lIIII lIIII2) {
            this();
        }
    }

    private static class CMN_reshape
    extends ControlCommandMessage {
        private CMN_reshape() {
        }

        @Override
        public Object run(Object[] objectArray) {
            Shell shell = this.getControl().getShell();
            if (!shell.isDisposed()) {
                shell.setSize((Integer)objectArray[0], (Integer)objectArray[1]);
            }
            return null;
        }

        CMN_reshape(lIIII lIIII2) {
            this();
        }
    }

    private class CMLocal_runInSequence
    extends LocalMessage {
        final SWTNativeComponent this$0;

        private CMLocal_runInSequence(SWTNativeComponent sWTNativeComponent) {
            this.this$0 = sWTNativeComponent;
        }

        @Override
        public Object run(Object[] objectArray) {
            ((Runnable)objectArray[0]).run();
            return null;
        }

        CMLocal_runInSequence(SWTNativeComponent sWTNativeComponent, lIIII lIIII2) {
            this(sWTNativeComponent);
        }
    }
}

