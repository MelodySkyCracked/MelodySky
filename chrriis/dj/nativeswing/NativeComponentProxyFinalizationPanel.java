/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import chrriis.dj.nativeswing.NativeComponentProxy;
import chrriis.dj.nativeswing.NativeComponentWrapper;
import chrriis.dj.nativeswing.common.UIUtils;
import chrriis.dj.nativeswing.lI;
import chrriis.dj.nativeswing.lIlIl;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Panel;
import java.lang.reflect.Method;
import java.util.Hashtable;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

class NativeComponentProxyFinalizationPanel
extends NativeComponentProxy {
    private EmbeddedPanel embeddedPanel;
    private boolean isProxied;

    NativeComponentProxyFinalizationPanel(NativeComponentWrapper nativeComponentWrapper) {
        super(nativeComponentWrapper);
    }

    @Override
    public void addNotify() {
        Object object;
        JComponent jComponent;
        boolean bl;
        super.addNotify();
        JLayeredPane jLayeredPane = NativeComponentProxyFinalizationPanel.findLayeredPane(this);
        if (this.embeddedPanel != null && EmbeddedPanel.access$100(this.embeddedPanel)) {
            jLayeredPane.setLayer(this.embeddedPanel, Integer.MIN_VALUE);
            jLayeredPane.add(this.embeddedPanel);
            jLayeredPane.invalidate();
            jLayeredPane.validate();
            this.nativeComponentWrapper.restoreFromHiddenParent();
            EmbeddedPanel.access$102(this.embeddedPanel, false);
        }
        boolean bl2 = bl = this.embeddedPanel != null;
        if (bl) {
            jComponent = NativeComponentProxyFinalizationPanel.findLayeredPane(this.embeddedPanel);
            if (jLayeredPane != jComponent) {
                this.nativeComponentWrapper.storeInHiddenParent();
                object = this.embeddedPanel.getParent();
                ((Container)object).remove(this.embeddedPanel);
                UIUtils.revalidate((Component)object);
                ((Component)object).repaint();
                jLayeredPane.setLayer(this.embeddedPanel, Integer.MIN_VALUE);
                jLayeredPane.add(this.embeddedPanel);
                this.nativeComponentWrapper.restoreFromHiddenParent();
                UIUtils.revalidate(jLayeredPane);
                jLayeredPane.repaint();
                this.revalidate();
                this.repaint();
            }
        } else {
            this.embeddedPanel = new EmbeddedPanel(this.nativeComponentWrapper);
            this.embeddedPanel.add(this.nativeComponentWrapper.getNativeComponent(), "Center");
        }
        this.isProxied = false;
        jComponent = (JComponent)this.embeddedPanel.getParent();
        if (jComponent != this) {
            if (jComponent == null) {
                this.add(this.embeddedPanel);
            } else {
                this.setComponentZOrder(this.embeddedPanel, 0);
                if (jComponent instanceof JLayeredPane) {
                    try {
                        object = JLayeredPane.class.getDeclaredMethod("getComponentToLayer", new Class[0]);
                        ((Method)object).setAccessible(true);
                        ((Hashtable)((Method)object).invoke(jComponent, new Object[0])).remove(this.embeddedPanel);
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                jComponent.revalidate();
                jComponent.repaint();
            }
            this.revalidate();
            this.repaint();
            this.embeddedPanel.setVisible(true);
        }
    }

    @Override
    public void removeNotify() {
        block5: {
            try {
                if (this.embeddedPanel != null) {
                    this.nativeComponentWrapper.storeInHiddenParent();
                    EmbeddedPanel.access$102(this.embeddedPanel, true);
                }
            }
            catch (Exception exception) {
                if (this.isProxied) break block5;
                this.embeddedPanel.setVisible(false);
                this.isProxied = true;
                try {
                    JLayeredPane jLayeredPane = NativeComponentProxyFinalizationPanel.findLayeredPane(this);
                    jLayeredPane.setLayer(this.embeddedPanel, Integer.MIN_VALUE);
                    jLayeredPane.setComponentZOrder(this.embeddedPanel, 0);
                    jLayeredPane.revalidate();
                    jLayeredPane.repaint();
                    this.revalidate();
                    this.repaint();
                }
                catch (RuntimeException runtimeException) {
                    super.removeNotify();
                    throw runtimeException;
                }
            }
        }
        super.removeNotify();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.embeddedPanel != null) {
            SwingUtilities.invokeLater(new lI(this));
        }
    }

    @Override
    public void dispose() {
        if (this.embeddedPanel == null) {
            return;
        }
        EmbeddedPanel embeddedPanel = this.embeddedPanel;
        this.embeddedPanel.removeNotify();
        this.embeddedPanel = null;
        Container container = embeddedPanel.getParent();
        if (container != null) {
            EmbeddedPanel.access$202(embeddedPanel, true);
            container.remove(embeddedPanel);
            container.invalidate();
            container.validate();
            container.repaint();
        }
    }

    private static class EmbeddedPanel
    extends Panel
    implements NativeComponentWrapper.NativeComponentHolder {
        private NativeComponentWrapper nativeComponentWrapper;
        private boolean isHiddenReparenting;
        private boolean isRemovingFromParent;

        public EmbeddedPanel(NativeComponentWrapper nativeComponentWrapper) {
            super(new BorderLayout());
            this.nativeComponentWrapper = nativeComponentWrapper;
            this.enableEvents(131072L);
        }

        @Override
        public void removeNotify() {
            Container container;
            super.removeNotify();
            if (!this.isRemovingFromParent && (container = this.getParent()) != null) {
                this.isRemovingFromParent = true;
                container.remove(this);
                container.invalidate();
                container.validate();
                this.isRemovingFromParent = false;
            }
        }

        protected void finalize() throws Throwable {
            if (this.isHiddenReparenting) {
                SwingUtilities.invokeLater(new lIlIl(this));
            }
        }

        static NativeComponentWrapper access$000(EmbeddedPanel embeddedPanel) {
            return embeddedPanel.nativeComponentWrapper;
        }

        static boolean access$100(EmbeddedPanel embeddedPanel) {
            return embeddedPanel.isHiddenReparenting;
        }

        static boolean access$102(EmbeddedPanel embeddedPanel, boolean bl) {
            embeddedPanel.isHiddenReparenting = bl;
            return embeddedPanel.isHiddenReparenting;
        }

        static boolean access$202(EmbeddedPanel embeddedPanel, boolean bl) {
            embeddedPanel.isRemovingFromParent = bl;
            return embeddedPanel.isRemovingFromParent;
        }
    }
}

