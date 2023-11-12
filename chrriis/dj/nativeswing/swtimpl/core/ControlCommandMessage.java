/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl.core;

import chrriis.dj.nativeswing.common.ObjectRegistry;
import chrriis.dj.nativeswing.swtimpl.CommandMessage;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeComponent;
import chrriis.dj.nativeswing.swtimpl.core.SWTNativeInterface;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Control;

public abstract class ControlCommandMessage
extends CommandMessage {
    private int componentID;
    private transient Boolean isTargetNativeSide;

    int getComponentID() {
        return this.componentID;
    }

    public void setControl(Control control) {
        this.componentID = (Integer)control.getData("NS_ID");
        this.setTargetNativeSide(false);
    }

    public void setNativeComponent(NativeComponent nativeComponent) {
        this.componentID = ((SWTNativeComponent)nativeComponent).getComponentID();
        this.setTargetNativeSide(true);
    }

    private boolean isTargetNativeSide() {
        if (this.isTargetNativeSide == null) {
            throw new IllegalStateException("The target must be specified!");
        }
        return this.isTargetNativeSide;
    }

    private void setTargetNativeSide(boolean bl) {
        this.isTargetNativeSide = bl;
    }

    public Control getControl() {
        ObjectRegistry objectRegistry = SWTNativeComponent.getControlRegistry();
        return objectRegistry == null ? null : (Control)objectRegistry.get(this.componentID);
    }

    public NativeComponent getNativeComponent() {
        ObjectRegistry objectRegistry = SWTNativeComponent.getNativeComponentRegistry();
        return objectRegistry == null ? null : (NativeComponent)objectRegistry.get(this.componentID);
    }

    public void asyncExec(NativeComponent nativeComponent, Object ... objectArray) {
        this.setNativeComponent(nativeComponent);
        this.asyncExec(objectArray);
    }

    public void asyncExec(Control control, Object ... objectArray) {
        this.setControl(control);
        this.asyncExec(objectArray);
    }

    public Object syncExec(NativeComponent nativeComponent, Object ... objectArray) {
        this.setNativeComponent(nativeComponent);
        return this.syncExec(objectArray);
    }

    public Object syncExec(Control control, Object ... objectArray) {
        this.setControl(control);
        return this.syncExec(objectArray);
    }

    private Object syncExec(Object ... objectArray) {
        return this.syncExec(this.isTargetNativeSide(), objectArray);
    }

    @Override
    public Object syncExec(boolean bl, Object ... objectArray) {
        this.checkComponentID();
        return super.syncExec(bl, objectArray);
    }

    private void asyncExec(Object ... objectArray) {
        super.asyncExec(this.isTargetNativeSide(), objectArray);
    }

    @Override
    public void asyncExec(boolean bl, Object ... objectArray) {
        this.checkComponentID();
        super.asyncExec(bl, objectArray);
    }

    private void checkComponentID() {
        if (this.componentID == 0) {
            throw new IllegalStateException("The component was not specified!");
        }
    }

    @Override
    protected Object runCommand() throws Exception {
        try {
            return super.runCommand();
        }
        catch (RuntimeException runtimeException) {
            SWTNativeInterface sWTNativeInterface = SWTNativeInterface.getInstance();
            if (sWTNativeInterface.isInProcess_() || sWTNativeInterface.isOutProcessNativeSide_()) {
                for (Throwable throwable = runtimeException; throwable != null; throwable = throwable.getCause()) {
                    if (!(throwable instanceof SWTException) || ((SWTException)throwable).code != 24) continue;
                    throw new DisposedControlException(throwable);
                }
            }
            throw runtimeException;
        }
    }

    @Override
    protected boolean isValid() {
        SWTNativeInterface sWTNativeInterface = SWTNativeInterface.getInstance();
        if (sWTNativeInterface.isInProcess_()) {
            return this.getControl() != null || this.getNativeComponent() != null;
        }
        if (sWTNativeInterface.isOutProcessNativeSide_()) {
            return this.getControl() != null;
        }
        return this.getNativeComponent() != null;
    }

    static class DisposedControlException
    extends IllegalStateException {
        public DisposedControlException(Throwable throwable) {
            super("Widget is disposed", throwable);
        }
    }
}

