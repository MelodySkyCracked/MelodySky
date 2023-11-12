/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.swtimpl;

import chrriis.dj.nativeswing.NSComponentOptions;
import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.swtimpl.NSComponent;
import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public abstract class NSPanelComponent
extends JPanel
implements NSComponent {
    private NativeComponent nativeComponent;

    public static NSOption destroyOnFinalization() {
        return NSComponentOptions.destroyOnFinalization();
    }

    public static NSOption proxyComponentHierarchy() {
        return NSComponentOptions.proxyComponentHierarchy();
    }

    public static NSOption constrainVisibility() {
        return NSComponentOptions.constrainVisibility();
    }

    public NSPanelComponent() {
        super(new BorderLayout());
    }

    protected void initialize(NativeComponent nativeComponent) {
        if (this.nativeComponent != null) {
            throw new IllegalStateException("The native component is already initialized!");
        }
        this.nativeComponent = nativeComponent;
    }

    @Override
    public void initializeNativePeer() {
        this.nativeComponent.initializeNativePeer();
    }

    @Override
    public void disposeNativePeer() {
        this.nativeComponent.disposeNativePeer();
    }

    @Override
    public boolean isNativePeerDisposed() {
        return this.nativeComponent.isNativePeerDisposed();
    }

    @Override
    public boolean isNativePeerInitialized() {
        return this.nativeComponent.isNativePeerInitialized();
    }

    @Override
    public boolean isNativePeerValid() {
        return this.nativeComponent.isNativePeerValid();
    }

    @Override
    public void runInSequence(Runnable runnable) {
        this.nativeComponent.runInSequence(runnable);
    }

    public NativeComponent getNativeComponent() {
        return this.nativeComponent;
    }
}

