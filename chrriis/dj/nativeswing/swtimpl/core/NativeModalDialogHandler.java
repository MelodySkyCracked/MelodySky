/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.swtimpl.core.ControlCommandMessage;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeComponent;
import chrriis.dj.nativeswing.swtimpl.core.llIIl;
import chrriis.dj.nativeswing.swtimpl.core.lllIIl;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

public abstract class NativeModalDialogHandler {
    public void showModalDialog(Component component, ControlCommandMessage controlCommandMessage, Object ... objectArray) {
        Serializable serializable;
        Window window;
        Window window2 = window = component instanceof Window ? (Window)component : SwingUtilities.getWindowAncestor(component);
        JDialog jDialog = Utils.IS_JAVA_6_OR_GREATER ? new JDialog(window, Dialog.ModalityType.APPLICATION_MODAL) : (window instanceof Dialog ? new JDialog((Dialog)window, true) : new JDialog((Frame)window, true));
        if (Utils.IS_MAC) {
            NativeModalComponent nativeModalComponent = new NativeModalComponent(null);
            jDialog.getContentPane().add(nativeModalComponent.createEmbeddableComponent(new HashMap()), "Center");
            nativeModalComponent.initializeNativePeer();
            this.processResult(controlCommandMessage.syncExec(nativeModalComponent, objectArray));
            jDialog.dispose();
            return;
        }
        jDialog.setUndecorated(true);
        jDialog.setSize(0, 0);
        if (Utils.IS_WINDOWS) {
            serializable = component.getLocationOnScreen();
            serializable.x += component.getWidth() / 2 - 280;
            serializable.y += component.getHeight() / 2 - 200;
            jDialog.setLocation((Point)serializable);
        } else {
            jDialog.setLocationRelativeTo(window);
        }
        serializable = Utils.IS_JAVA_6_OR_GREATER ? new MouseEvent(window, 503, System.currentTimeMillis(), 0, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, 0, false, 0) : new MouseEvent(window, 503, System.currentTimeMillis(), 0, Integer.MIN_VALUE, Integer.MIN_VALUE, 0, false, 0);
        window.dispatchEvent((AWTEvent)serializable);
        jDialog.addWindowListener(new llIIl(this, jDialog, controlCommandMessage, objectArray));
        jDialog.setVisible(true);
    }

    protected abstract void processResult(Object var1);

    private static class CMN_openDialog
    extends ControlCommandMessage {
        private volatile transient Object result;

        private CMN_openDialog() {
        }

        @Override
        public Object run(Object[] objectArray) throws Exception {
            Control control = this.getControl();
            if (control.isDisposed()) {
                return null;
            }
            Display display = control.getDisplay();
            if (display.getThread() != Thread.currentThread()) {
                try {
                    display.syncExec(new lllIIl(this, objectArray));
                }
                catch (RuntimeException runtimeException) {
                    throw (Exception)runtimeException.getCause();
                }
                return this.result;
            }
            ControlCommandMessage controlCommandMessage = (ControlCommandMessage)objectArray[0];
            controlCommandMessage.setControl(control);
            return controlCommandMessage.run((Object[])objectArray[1]);
        }

        static Object access$002(CMN_openDialog cMN_openDialog, Object object) {
            cMN_openDialog.result = object;
            return cMN_openDialog.result;
        }

        CMN_openDialog(llIIl llIIl2) {
            this();
        }
    }

    private static class NativeModalComponent
    extends SWTNativeComponent {
        private NativeModalComponent() {
        }

        protected static Control createControl(Composite composite, Object[] objectArray) {
            return new Composite(composite, 0);
        }

        @Override
        protected Component createEmbeddableComponent(Map map) {
            return super.createEmbeddableComponent(map);
        }

        NativeModalComponent(llIIl llIIl2) {
            this();
        }
    }
}

