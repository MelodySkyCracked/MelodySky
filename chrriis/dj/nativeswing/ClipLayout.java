/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.io.Serializable;

class ClipLayout
implements LayoutManager2,
Serializable {
    private Component component;
    private Rectangle clip;

    ClipLayout() {
    }

    @Override
    public void addLayoutComponent(Component component, Object object) {
        Object object2 = component.getTreeLock();
        synchronized (object2) {
            if (object != null && !(object instanceof String)) {
                object = null;
            }
            this.addLayoutComponent((String)object, component);
        }
    }

    @Override
    @Deprecated
    public void addLayoutComponent(String string, Component component) {
        Object object = component.getTreeLock();
        synchronized (object) {
            this.component = component;
        }
    }

    @Override
    public void removeLayoutComponent(Component component) {
        Object object = component.getTreeLock();
        synchronized (object) {
            if (component == this.component) {
                this.component = null;
            }
        }
    }

    @Override
    public void layoutContainer(Container container) {
        Object object = container.getTreeLock();
        synchronized (object) {
            int n;
            int n2;
            if (this.component == null) {
                return;
            }
            Insets insets = container.getInsets();
            int n3 = insets.top;
            int n4 = insets.left;
            if (this.clip != null) {
                n4 += this.clip.x;
                n3 += this.clip.y;
                n2 = this.clip.width;
                n = this.clip.height;
            } else {
                int n5 = container.getWidth() - insets.right;
                n2 = n5 - n4;
                int n6 = container.getHeight() - insets.bottom;
                n = n6 - n3;
            }
            this.component.setBounds(n4, n3, n2, n);
        }
    }

    @Override
    public Dimension minimumLayoutSize(Container container) {
        Object object = container.getTreeLock();
        synchronized (object) {
            Insets insets = container.getInsets();
            Dimension dimension = new Dimension(insets.left + insets.right, insets.top + insets.bottom);
            if (this.component != null) {
                Dimension dimension2 = this.component.getMinimumSize();
                dimension.width += dimension2.width;
                dimension.height += dimension2.height;
            }
            return dimension;
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container container) {
        Object object = container.getTreeLock();
        synchronized (object) {
            Insets insets = container.getInsets();
            Dimension dimension = new Dimension(insets.left + insets.right, insets.top + insets.bottom);
            if (this.component != null) {
                Dimension dimension2 = this.component.getPreferredSize();
                dimension.width += dimension2.width;
                dimension.height += dimension2.height;
            }
            return dimension;
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container container) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public void invalidateLayout(Container container) {
    }

    @Override
    public float getLayoutAlignmentX(Container container) {
        return 0.5f;
    }

    @Override
    public float getLayoutAlignmentY(Container container) {
        return 0.5f;
    }

    public void setClip(Rectangle rectangle) {
        this.clip = rectangle;
    }

    public String toString() {
        return this.getClass().getName() + ",clip=" + (this.clip == null ? "" : this.clip.toString());
    }
}

