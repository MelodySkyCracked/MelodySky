/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import chrriis.dj.nativeswing.ClipLayout;
import chrriis.dj.nativeswing.NSSystemProperty;
import chrriis.dj.nativeswing.NativeComponentProxy;
import chrriis.dj.nativeswing.NativeComponentWrapper;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.common.UIUtils;
import chrriis.dj.nativeswing.common.Utils;
import chrriis.dj.nativeswing.jna.platform.WindowUtils;
import chrriis.dj.nativeswing.lII;
import chrriis.dj.nativeswing.lIII;
import chrriis.dj.nativeswing.lIIIl;
import chrriis.dj.nativeswing.lIIll;
import chrriis.dj.nativeswing.lIl;
import chrriis.dj.nativeswing.lIlII;
import chrriis.dj.nativeswing.llI;
import chrriis.dj.nativeswing.llIl;
import chrriis.dj.nativeswing.lll;
import chrriis.dj.nativeswing.llll;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;
import java.util.Arrays;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

class NativeComponentProxyPanel
extends NativeComponentProxy {
    private static final boolean IS_DEBUGGING_SHAPE = Boolean.parseBoolean(NSSystemProperty.COMPONENTS_DEBUG_PRINTSHAPECOMPUTING.get());
    private boolean isProxiedFiliation;
    private AWTEventListener shapeAdjustmentEventListener;
    private boolean isDestructionOnFinalization;
    private boolean isVisibilityConstrained;
    private HierarchyBoundsListener hierarchyBoundsListener = new lIlII(this);
    private MouseAdapter mouseListener = new llI(this);
    private volatile boolean isInvoking;
    private Rectangle[] lastArea = new Rectangle[]{new Rectangle(this.getSize())};
    private EmbeddedPanel embeddedPanel;
    private HierarchyListener hierarchyListener;

    NativeComponentProxyPanel(NativeComponentWrapper nativeComponentWrapper, boolean bl, boolean bl2, boolean bl3) {
        super(nativeComponentWrapper);
        this.isDestructionOnFinalization = bl2;
        this.isVisibilityConstrained = bl;
        this.hierarchyListener = new lll(this);
        if (bl) {
            this.shapeAdjustmentEventListener = new lIIll(this);
        }
        this.isProxiedFiliation = bl3;
    }

    private void adjustEmbeddedPanelBounds() {
        Container container;
        boolean bl;
        if (this.embeddedPanel == null) {
            return;
        }
        if (!this.isVisibilityConstrained && (bl = this.isShowing()) != this.embeddedPanel.isVisible()) {
            this.embeddedPanel.setVisible(bl);
        }
        if ((container = this.embeddedPanel.getParent()) != null) {
            Point point = SwingUtilities.convertPoint(this, new Point(0, 0), container);
            Dimension dimension = this.getSize();
            Rectangle rectangle = new Rectangle(point.x, point.y, dimension.width, dimension.height);
            Rectangle rectangle2 = this.embeddedPanel.getRectangularClip();
            if (rectangle2 != null) {
                rectangle.x += rectangle2.x;
                rectangle.y += rectangle2.y;
                rectangle.width = rectangle2.width;
                rectangle.height = rectangle2.height;
            }
            if (!this.embeddedPanel.getBounds().equals(rectangle)) {
                this.embeddedPanel.setBounds(rectangle);
                this.embeddedPanel.invalidate();
                this.embeddedPanel.validate();
                this.embeddedPanel.repaint();
                if (this.isVisibilityConstrained) {
                    this.adjustEmbeddedPanelShape();
                }
            }
        }
    }

    private void adjustEmbeddedPanelShape() {
        if (this.isInvoking) {
            return;
        }
        this.isInvoking = true;
        SwingUtilities.invokeLater(new lIl(this));
    }

    private void adjustEmbeddedPanelShape_() {
        if (this.embeddedPanel == null) {
            return;
        }
        Object[] objectArray = this.computePeerShapeArea();
        if (Arrays.equals(this.lastArea, objectArray)) {
            EmbeddedPanel.access$500(this.embeddedPanel).getNativeComponent().repaint();
            return;
        }
        this.lastArea = objectArray;
        if (objectArray.length == 0) {
            this.embeddedPanel.setVisible(false);
        } else {
            if (!this.embeddedPanel.isVisible()) {
                this.embeddedPanel.setVisible(true);
            }
            this.embeddedPanel.applyShape((Rectangle[])objectArray);
        }
    }

    private Rectangle[] computePeerShapeArea() {
        if (IS_DEBUGGING_SHAPE) {
            System.err.println("Computing shape: [" + this.getWidth() + "x" + this.getHeight() + "] " + this.nativeComponentWrapper.getComponentDescription());
        }
        Rectangle[] rectangleArray = UIUtils.getComponentVisibleArea(this, new lIII(this));
        return rectangleArray;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        for (NativeComponentWrapper nativeComponentWrapper : NativeSwing.getNativeComponentWrappers()) {
            NativeComponentProxy nativeComponentProxy = nativeComponentWrapper.getNativeComponentProxy();
            if (!(nativeComponentProxy instanceof NativeComponentProxyPanel) || !((NativeComponentProxyPanel)nativeComponentProxy).isVisibilityConstrained) continue;
            ((NativeComponentProxyPanel)nativeComponentProxy).adjustEmbeddedPanelShape();
        }
    }

    @Override
    public void reshape(int n, int n2, int n3, int n4) {
        if (n == this.getX() && n2 == this.getY() && n3 == this.getWidth() && n4 == this.getHeight()) {
            return;
        }
        super.reshape(n, n2, n3, n4);
        this.adjustEmbeddedPanelBounds();
    }

    @Override
    public void addNotify() {
        boolean bl;
        super.addNotify();
        if (this.hierarchyListener != null) {
            this.addHierarchyListener(this.hierarchyListener);
        }
        if (this.shapeAdjustmentEventListener != null) {
            Toolkit.getDefaultToolkit().addAWTEventListener(this.shapeAdjustmentEventListener, 3L);
        }
        JLayeredPane jLayeredPane = null;
        if (this.isProxiedFiliation) {
            jLayeredPane = NativeComponentProxyPanel.findLayeredPane(this);
        }
        boolean bl2 = bl = this.embeddedPanel != null;
        if (bl) {
            if (this.isProxiedFiliation) {
                JLayeredPane jLayeredPane2;
                if (this.embeddedPanel.getParent() == null) {
                    jLayeredPane.setLayer(this.embeddedPanel, Integer.MIN_VALUE);
                    jLayeredPane.add(this.embeddedPanel);
                    jLayeredPane.invalidate();
                    jLayeredPane.validate();
                }
                if (jLayeredPane != (jLayeredPane2 = NativeComponentProxyPanel.findLayeredPane(this.embeddedPanel))) {
                    EmbeddedPanel.access$702(this.embeddedPanel, true);
                    this.nativeComponentWrapper.storeInHiddenParent();
                    Container container = this.embeddedPanel.getParent();
                    container.remove(this.embeddedPanel);
                    UIUtils.revalidate(container);
                    container.repaint();
                    jLayeredPane.setLayer(this.embeddedPanel, Integer.MIN_VALUE);
                    jLayeredPane.add(this.embeddedPanel);
                    this.nativeComponentWrapper.restoreFromHiddenParent();
                    EmbeddedPanel.access$702(this.embeddedPanel, false);
                    UIUtils.revalidate(jLayeredPane);
                    jLayeredPane.repaint();
                    this.revalidate();
                    this.repaint();
                }
            }
        } else {
            this.embeddedPanel = new EmbeddedPanel(this.nativeComponentWrapper, this.isDestructionOnFinalization);
            this.embeddedPanel.add(this.nativeComponentWrapper.getNativeComponent(), "Center");
        }
        this.lastArea = null;
        this.adjustEmbeddedPanelBounds();
        SwingUtilities.invokeLater(new lIIIl(this));
        this.nativeComponentWrapper.getNativeComponent().addMouseListener(this.mouseListener);
        if (!bl) {
            if (this.isProxiedFiliation) {
                jLayeredPane.setLayer(this.embeddedPanel, Integer.MIN_VALUE);
                jLayeredPane.add(this.embeddedPanel);
                UIUtils.revalidate(jLayeredPane);
                jLayeredPane.repaint();
            } else {
                this.add(this.embeddedPanel);
                this.revalidate();
                this.repaint();
            }
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (this.hierarchyListener != null) {
            this.removeHierarchyListener(this.hierarchyListener);
        }
        if (this.shapeAdjustmentEventListener != null) {
            Toolkit.getDefaultToolkit().removeAWTEventListener(this.shapeAdjustmentEventListener);
        }
        if (this.isDestructionOnFinalization) {
            SwingUtilities.invokeLater(new llll(this));
            this.nativeComponentWrapper.getNativeComponent().removeMouseListener(this.mouseListener);
            if (this.isVisibilityConstrained) {
                this.adjustEmbeddedPanelShape();
            } else {
                this.adjustEmbeddedPanelBounds();
            }
            return;
        }
        this.dispose();
    }

    protected void finalize() throws Throwable {
        super.finalize();
        if (this.embeddedPanel != null) {
            SwingUtilities.invokeLater(new lII(this));
        }
    }

    @Override
    public void dispose() {
        if (this.embeddedPanel == null) {
            return;
        }
        EmbeddedPanel embeddedPanel = this.embeddedPanel;
        this.embeddedPanel = null;
        Container container = embeddedPanel.getParent();
        if (container != null) {
            EmbeddedPanel.access$902(embeddedPanel, true);
            container.remove(embeddedPanel);
            container.invalidate();
            container.validate();
            container.repaint();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (this.embeddedPanel != null) {
            return this.embeddedPanel.getPreferredSize();
        }
        return super.getPreferredSize();
    }

    static boolean access$000(NativeComponentProxyPanel nativeComponentProxyPanel) {
        return nativeComponentProxyPanel.isVisibilityConstrained;
    }

    static void access$100(NativeComponentProxyPanel nativeComponentProxyPanel) {
        nativeComponentProxyPanel.adjustEmbeddedPanelShape();
    }

    static void access$200(NativeComponentProxyPanel nativeComponentProxyPanel) {
        nativeComponentProxyPanel.adjustEmbeddedPanelBounds();
    }

    static boolean access$302(NativeComponentProxyPanel nativeComponentProxyPanel, boolean bl) {
        nativeComponentProxyPanel.isInvoking = bl;
        return nativeComponentProxyPanel.isInvoking;
    }

    static void access$400(NativeComponentProxyPanel nativeComponentProxyPanel) {
        nativeComponentProxyPanel.adjustEmbeddedPanelShape_();
    }

    static boolean access$600() {
        return IS_DEBUGGING_SHAPE;
    }

    static HierarchyBoundsListener access$800(NativeComponentProxyPanel nativeComponentProxyPanel) {
        return nativeComponentProxyPanel.hierarchyBoundsListener;
    }

    private static class EmbeddedPanel
    extends Panel
    implements NativeComponentWrapper.NativeComponentHolder {
        private NativeComponentWrapper nativeComponentWrapper;
        private boolean isDestructionOnFinalization;
        private boolean isCrossWindowReparenting;
        private boolean isHiddenReparenting;
        private boolean isRemovingFromParent;
        private Rectangle clip;
        private static final boolean RESTRICT_SHAPE_TO_SINGLE_RECTANGLE = Boolean.parseBoolean(NSSystemProperty.COMPONENTS_FORCESINGLERECTANGLESHAPES.get());

        public EmbeddedPanel(NativeComponentWrapper nativeComponentWrapper, boolean bl) {
            super(new ClipLayout());
            this.nativeComponentWrapper = nativeComponentWrapper;
            this.isDestructionOnFinalization = bl;
            this.enableEvents(131072L);
        }

        @Override
        public boolean contains(int n, int n2) {
            return false;
        }

        @Override
        public boolean contains(Point point) {
            return false;
        }

        @Override
        public void removeNotify() {
            if (this.isRemovingFromParent) {
                super.removeNotify();
                return;
            }
            if (this.isDestructionOnFinalization && !this.isCrossWindowReparenting) {
                try {
                    this.nativeComponentWrapper.storeInHiddenParent();
                    this.isHiddenReparenting = true;
                }
                catch (Exception exception) {
                    // empty catch block
                }
                super.removeNotify();
                this.isRemovingFromParent = true;
                Container container = this.getParent();
                if (container != null) {
                    container.remove(this);
                    container.invalidate();
                    container.validate();
                }
                this.isRemovingFromParent = false;
            } else {
                super.removeNotify();
            }
        }

        @Override
        public void addNotify() {
            super.addNotify();
            if (this.isHiddenReparenting) {
                this.isHiddenReparenting = false;
                this.nativeComponentWrapper.restoreFromHiddenParent();
            }
        }

        protected void finalize() throws Throwable {
            if (this.isHiddenReparenting) {
                SwingUtilities.invokeLater(new llIl(this));
            }
        }

        public void applyShape(Rectangle[] rectangleArray) {
            Container container;
            int n;
            Rectangle rectangle;
            if (!Utils.IS_MAC && !RESTRICT_SHAPE_TO_SINGLE_RECTANGLE) {
                if (rectangleArray.length > 0) {
                    Point2D.Double double_ = UIUtils.getScaledFactor(this);
                    if (double_.x != 1.0 || double_.y != 1.0) {
                        Rectangle[] rectangleArray2 = new Rectangle[rectangleArray.length];
                        for (int i = 0; i < rectangleArray.length; ++i) {
                            rectangleArray2[i] = new Rectangle((int)((double)rectangleArray[i].x * double_.x), (int)((double)rectangleArray[i].y * double_.y), (int)((double)rectangleArray[i].width * double_.x), (int)((double)rectangleArray[i].height * double_.y));
                        }
                        rectangleArray = rectangleArray2;
                    }
                }
                WindowUtils.setComponentMask((Component)this, rectangleArray);
                this.nativeComponentWrapper.getNativeComponent().repaint();
                return;
            }
            if (rectangleArray.length == 0) {
                rectangle = null;
            } else {
                rectangle = new Rectangle(rectangleArray[0]);
                if (rectangleArray.length > 1) {
                    System.err.println("Non-rectangular clip detected on a system that does not support this feature.");
                    for (n = 1; n < rectangleArray.length; ++n) {
                        rectangle = rectangle.union(rectangleArray[n]);
                    }
                }
            }
            if (Utils.equals(this.clip, rectangle)) {
                return;
            }
            n = this.clip == null ? 0 : this.clip.x;
            int n2 = this.clip == null ? 0 : this.clip.y;
            this.clip = rectangle;
            int n3 = rectangle == null ? 0 : rectangle.x;
            int n4 = rectangle == null ? 0 : rectangle.y;
            NativeComponentProxy nativeComponentProxy = this.nativeComponentWrapper.getNativeComponentProxy();
            if (nativeComponentProxy != null) {
                ((ClipLayout)this.getLayout()).setClip(rectangle == null ? null : new Rectangle(-rectangle.x, -rectangle.y, nativeComponentProxy.getWidth(), nativeComponentProxy.getHeight()));
            }
            if ((container = this.getParent()) != null) {
                LayoutManager layoutManager = container.getLayout();
                if (layoutManager instanceof ClipLayout) {
                    ((ClipLayout)layoutManager).setClip(rectangle);
                } else {
                    int n5 = n3 - n;
                    int n6 = n4 - n2;
                    this.setBounds(this.getX() + n5, this.getY() + n6, this.getWidth() - n5, this.getHeight() - n6);
                }
                this.doLayout();
                UIUtils.revalidate(container);
            }
        }

        public Rectangle getRectangularClip() {
            return this.clip;
        }

        static NativeComponentWrapper access$500(EmbeddedPanel embeddedPanel) {
            return embeddedPanel.nativeComponentWrapper;
        }

        static boolean access$702(EmbeddedPanel embeddedPanel, boolean bl) {
            embeddedPanel.isCrossWindowReparenting = bl;
            return embeddedPanel.isCrossWindowReparenting;
        }

        static boolean access$902(EmbeddedPanel embeddedPanel, boolean bl) {
            embeddedPanel.isRemovingFromParent = bl;
            return embeddedPanel.isRemovingFromParent;
        }
    }
}

