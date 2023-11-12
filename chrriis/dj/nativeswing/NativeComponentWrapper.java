/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import chrriis.dj.nativeswing.BackBufferManager;
import chrriis.dj.nativeswing.EmbeddableComponent;
import chrriis.dj.nativeswing.NSOption;
import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.NativeComponentProxy;
import chrriis.dj.nativeswing.NativeComponentProxyFinalizationPanel;
import chrriis.dj.nativeswing.NativeComponentProxyPanel;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.lIllI;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import javax.swing.SwingUtilities;

public class NativeComponentWrapper {
    private static final boolean IS_DEBUGGING_OPTIONS = Boolean.parseBoolean(NSSystemProperty.COMPONENTS_DEBUG_PRINTOPTIONS.get());
    private boolean isRegistered;
    private Component nativeComponent;
    private Reference nativeComponentProxy;
    private BackBufferManager backBufferManager;

    private void checkParent() {
        Container container = this.nativeComponent.getParent();
        if (container != null && !(container instanceof NativeComponentHolder)) {
            throw new IllegalStateException("The native component cannot be added directly! Use the createEmbeddableComponent() method to get a component that can be added.");
        }
        if (container != null && SwingUtilities.getWindowAncestor(container) != null) {
            if (!this.isRegistered) {
                NativeSwing.addNativeComponentWrapper(this);
                this.isRegistered = true;
            }
        } else if (this.isRegistered && NativeSwing.removeNativeComponentWrapper(this)) {
            this.isRegistered = false;
        }
    }

    public NativeComponentWrapper(Component component) {
        this.nativeComponent = component;
        this.checkParent();
        component.addHierarchyListener(new lIllI(this));
    }

    Component getNativeComponent() {
        return this.nativeComponent;
    }

    protected void paintNativeComponent(BufferedImage bufferedImage, Rectangle[] rectangleArray) {
    }

    void setNativeComponentProxy(NativeComponentProxy nativeComponentProxy) {
        this.nativeComponentProxy = nativeComponentProxy == null ? null : new WeakReference<NativeComponentProxy>(nativeComponentProxy);
    }

    NativeComponentProxy getNativeComponentProxy() {
        return this.nativeComponentProxy == null ? null : (NativeComponentProxy)this.nativeComponentProxy.get();
    }

    protected void setNativeComponentEnabled(boolean bl) {
    }

    protected boolean isNativeComponentEnabled() {
        return true;
    }

    protected String getComponentDescription() {
        return this.getClass().getName() + "[" + this.hashCode() + "]";
    }

    public Component createEmbeddableComponent(NSOption ... nSOptionArray) {
        return this.createEmbeddableComponent(NSOption.createOptionMap(nSOptionArray));
    }

    public Component createEmbeddableComponent(Map map) {
        Object object2;
        CharSequence charSequence;
        if (IS_DEBUGGING_OPTIONS) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("NativeComponent ").append(this.getComponentDescription()).append(" options: ");
            boolean bl = true;
            for (Object object2 : map.keySet()) {
                if (bl) {
                    bl = false;
                } else {
                    ((StringBuilder)charSequence).append(", ");
                }
                Object v = map.get(object2);
                if (v instanceof NSOption) {
                    ((StringBuilder)charSequence).append(v);
                    continue;
                }
                ((StringBuilder)charSequence).append(object2).append('=').append(v);
            }
            if (bl) {
                ((StringBuilder)charSequence).append("<none>");
            }
            System.err.println(charSequence);
        }
        charSequence = NSSystemProperty.INTEGRATION_ACTIVE.get();
        if (map.get("Deactivate Native Integration") != null || charSequence != null && !Boolean.parseBoolean((String)charSequence)) {
            this.isRegistered = true;
            return new SimpleNativeComponentHolder(this);
        }
        Boolean bl = map.get("Destroy On Finalization") != null ? Boolean.TRUE : null;
        Object object3 = map.get("Proxy Component Hierarchy") != null ? Boolean.TRUE : null;
        Boolean bl2 = object2 = map.get("Constrain Visibility") != null ? Boolean.TRUE : null;
        if (Boolean.valueOf(NSSystemProperty.INTEGRATION_USEDEFAULTCLIPPING.get()).booleanValue() || object2 == null && object3 == null) {
            if (bl != null && object3 == null) {
                object3 = true;
            }
            if (Boolean.TRUE.equals(object3)) {
                return new NativeComponentProxyFinalizationPanel(this);
            }
            return new SimpleNativeComponentHolder(this);
        }
        boolean bl3 = NativeComponentWrapper.isJNAPresent();
        if (object2 == null && bl3 && object3 != null) {
            object2 = true;
        }
        if (object2 != null && !bl3) {
            throw new IllegalStateException("The JNA libraries are required to use the visibility constraints!");
        }
        if (bl != null && object3 == null) {
            object3 = true;
        }
        if (object3 != null) {
            return new NativeComponentProxyPanel(this, Boolean.TRUE.equals(object2), Boolean.TRUE.equals(bl), Boolean.TRUE.equals(object3));
        }
        if (object2 == null) {
            return new SimpleNativeComponentHolder(this);
        }
        return new NativeComponentProxyPanel(this, Boolean.TRUE.equals(object2), Boolean.TRUE.equals(bl), Boolean.TRUE.equals(object3));
    }

    private static boolean isJNAPresent() {
        try {
            Class.forName("chrriis.dj.nativeswing.jna.platform.WindowUtils");
            Class.forName("com.sun.jna.platform.WindowUtils");
            Class.forName("com.sun.jna.Platform");
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public void disposeNativeComponent() {
        NativeComponentProxy nativeComponentProxy = this.getNativeComponentProxy();
        if (nativeComponentProxy != null) {
            nativeComponentProxy.dispose();
        }
    }

    BackBufferManager getBackBufferManager() {
        NativeComponentProxy nativeComponentProxy = this.getNativeComponentProxy();
        if (nativeComponentProxy != null) {
            return nativeComponentProxy.getBackBufferManager();
        }
        if (this.backBufferManager == null) {
            this.backBufferManager = new BackBufferManager(this, this.getNativeComponent());
        }
        return this.backBufferManager;
    }

    public void paintBackBuffer(Graphics graphics, boolean bl) {
        NativeComponentProxy nativeComponentProxy = this.getNativeComponentProxy();
        if (nativeComponentProxy != null) {
            BackBufferManager backBufferManager = nativeComponentProxy.getBackBufferManager();
            if (backBufferManager != null) {
                backBufferManager.paintBackBuffer(graphics);
            }
            return;
        }
        if (this.backBufferManager != null) {
            this.backBufferManager.paintBackBuffer(graphics);
        }
    }

    public boolean hasBackBuffer() {
        NativeComponentProxy nativeComponentProxy = this.getNativeComponentProxy();
        if (nativeComponentProxy != null) {
            BackBufferManager backBufferManager = nativeComponentProxy.getBackBufferManager();
            if (backBufferManager != null) {
                return backBufferManager.hasBackBuffer();
            }
            return false;
        }
        return this.backBufferManager != null && this.backBufferManager.hasBackBuffer();
    }

    public void createBackBuffer() {
        this.getBackBufferManager().createBackBuffer();
    }

    public void updateBackBufferOnVisibleTranslucentAreas() {
        this.getBackBufferManager().updateBackBufferOnVisibleTranslucentAreas();
    }

    public void updateBackBuffer(Rectangle[] rectangleArray) {
        this.getBackBufferManager().updateBackBuffer(rectangleArray);
    }

    public void destroyBackBuffer() {
        this.getBackBufferManager().destroyBackBuffer();
    }

    public void transferFocus(boolean bl) {
        Component component = this.getNativeComponentProxy();
        if (component == null) {
            component = this.getNativeComponent();
        }
        if (bl) {
            component.transferFocus();
        } else {
            component.transferFocusBackward();
        }
    }

    protected void storeInHiddenParent() {
        throw new IllegalStateException("Storing to a hidden parent is not supported!");
    }

    protected void restoreFromHiddenParent() {
    }

    static void access$000(NativeComponentWrapper nativeComponentWrapper) {
        nativeComponentWrapper.checkParent();
    }

    static class SimpleNativeComponentHolder
    extends EmbeddableComponent
    implements NativeComponentHolder {
        private NativeComponentWrapper nativeComponent;

        public SimpleNativeComponentHolder(NativeComponentWrapper nativeComponentWrapper) {
            this.nativeComponent = nativeComponentWrapper;
            this.add(nativeComponentWrapper.getNativeComponent());
            this.enableEvents(131072L);
        }

        @Override
        protected void printComponent(Graphics graphics) {
            this.nativeComponent.getNativeComponent().print(graphics);
        }
    }

    static interface NativeComponentHolder {
    }
}

