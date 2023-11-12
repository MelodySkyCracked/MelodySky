/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import chrriis.dj.nativeswing.BackBufferManager;
import chrriis.dj.nativeswing.EmbeddableComponent;
import chrriis.dj.nativeswing.NativeComponentWrapper;
import chrriis.dj.nativeswing.lIIl;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JLayeredPane;
import javax.swing.RootPaneContainer;

abstract class NativeComponentProxy
extends EmbeddableComponent {
    private BackBufferManager backBufferManager;
    protected NativeComponentWrapper nativeComponentWrapper;

    protected NativeComponentProxy(NativeComponentWrapper nativeComponentWrapper) {
        this.nativeComponentWrapper = nativeComponentWrapper;
        nativeComponentWrapper.setNativeComponentProxy(this);
        this.backBufferManager = new BackBufferManager(nativeComponentWrapper, this);
        this.setFocusable(true);
        this.addFocusListener(new lIIl(this));
    }

    @Override
    protected void printComponent(Graphics graphics) {
        this.nativeComponentWrapper.getNativeComponent().print(graphics);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.backBufferManager.paintBackBuffer(graphics);
    }

    public BackBufferManager getBackBufferManager() {
        return this.backBufferManager;
    }

    protected abstract void dispose();

    protected static JLayeredPane findLayeredPane(Component component) {
        Component component2 = component;
        while ((component2 = component2.getParent()) != null) {
            if (component2.isLightweight() || !(component2 instanceof RootPaneContainer)) continue;
            return ((RootPaneContainer)((Object)component2)).getLayeredPane();
        }
        throw new IllegalStateException("The window ancestor must be a root pane container!");
    }
}

