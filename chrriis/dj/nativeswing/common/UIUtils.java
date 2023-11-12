/*
 * Decompiled with CFR 0.152.
 */
package chrriis.dj.nativeswing.common;

import chrriis.dj.nativeswing.common.Filter;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class UIUtils {
    private static String COMPONENT_TRANSPARENT_CLIENT_PROPERTY = "nsTransparent";

    private UIUtils() {
    }

    public static Rectangle[] subtract(Rectangle[] rectangleArray, Rectangle rectangle) {
        return UIUtils.subtract(rectangleArray, new Rectangle[]{rectangle});
    }

    public static Rectangle[] subtract(Rectangle[] rectangleArray, Rectangle[] rectangleArray2) {
        ArrayList<Rectangle> arrayList = new ArrayList<Rectangle>(Arrays.asList(rectangleArray));
        ArrayList<Rectangle> arrayList2 = new ArrayList<Rectangle>();
        for (int i = 0; i < rectangleArray2.length; ++i) {
            Rectangle rectangle = rectangleArray2[i];
            for (Rectangle rectangle2 : arrayList) {
                if (rectangle2.intersects(rectangle)) {
                    UIUtils.subtract(rectangle2, rectangle, arrayList2);
                    continue;
                }
                arrayList2.add((Rectangle)rectangle2.clone());
            }
            arrayList.clear();
            if (arrayList2.isEmpty()) break;
            arrayList.addAll(arrayList2);
            arrayList2.clear();
        }
        return arrayList.toArray(new Rectangle[0]);
    }

    private static void subtract(Rectangle rectangle, Rectangle rectangle2, List list) {
        boolean bl;
        boolean bl2 = rectangle2.x <= rectangle.x && rectangle2.x + rectangle2.width > rectangle.x;
        boolean bl3 = rectangle2.x < rectangle.x + rectangle.width && rectangle2.x + rectangle2.width >= rectangle.x + rectangle.width;
        boolean bl4 = rectangle2.y <= rectangle.y && rectangle2.y + rectangle2.height > rectangle.y;
        boolean bl5 = bl = rectangle2.y < rectangle.y + rectangle.height && rectangle2.y + rectangle2.height >= rectangle.y + rectangle.height;
        if (!(bl2 && bl3 && bl4 && bl)) {
            if (bl2 && bl3 && bl4) {
                int n = rectangle2.y + rectangle2.height;
                int n2 = rectangle.y + rectangle.height - n;
                list.add(new Rectangle(rectangle.x, n, rectangle.width, n2));
            } else if (bl2 && bl3 && bl) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle2.y - rectangle.y));
            } else if (bl4 && bl && bl2) {
                int n = rectangle2.x + rectangle2.width;
                int n3 = rectangle.x + rectangle.width - n;
                list.add(new Rectangle(n, rectangle.y, n3, rectangle.height));
            } else if (bl4 && bl && bl3) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle2.x - rectangle.x, rectangle.height));
            } else if (bl2 && bl4) {
                int n = rectangle2.x + rectangle2.width;
                int n4 = rectangle2.y + rectangle2.height;
                list.add(new Rectangle(n, rectangle.y, rectangle.x + rectangle.width - n, n4 - rectangle.y));
                list.add(new Rectangle(rectangle.x, n4, rectangle.width, rectangle.y + rectangle.height - n4));
            } else if (bl2 && bl) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle2.y - rectangle.y));
                int n = rectangle2.x + rectangle2.width;
                list.add(new Rectangle(n, rectangle2.y, rectangle.x + rectangle.width - n, rectangle.y + rectangle.height - rectangle2.y));
            } else if (bl3 && bl4) {
                int n = rectangle2.y + rectangle2.height;
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle2.x - rectangle.x, n - rectangle.y));
                list.add(new Rectangle(rectangle.x, n, rectangle.width, rectangle.y + rectangle.height - n));
            } else if (bl3 && bl) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle2.y - rectangle.y));
                list.add(new Rectangle(rectangle.x, rectangle2.y, rectangle2.x - rectangle.x, rectangle.y + rectangle.height - rectangle2.y));
            } else if (bl2 && bl3) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle2.y - rectangle.y));
                int n = rectangle2.y + rectangle2.height;
                list.add(new Rectangle(rectangle.x, n, rectangle.width, rectangle.y + rectangle.height - n));
            } else if (bl4 && bl) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle2.x - rectangle.x, rectangle.height));
                int n = rectangle2.x + rectangle2.width;
                list.add(new Rectangle(n, rectangle.y, rectangle.x + rectangle.width - n, rectangle.height));
            } else if (bl2) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle2.y - rectangle.y));
                int n = rectangle2.y + rectangle2.height;
                list.add(new Rectangle(rectangle.x, n, rectangle.width, rectangle.y + rectangle.height - n));
                int n5 = rectangle2.x + rectangle2.width;
                list.add(new Rectangle(n5, rectangle2.y, rectangle.x + rectangle.width - n5, rectangle2.height));
            } else if (bl3) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle2.y - rectangle.y));
                int n = rectangle2.y + rectangle2.height;
                list.add(new Rectangle(rectangle.x, n, rectangle.width, rectangle.y + rectangle.height - n));
                list.add(new Rectangle(rectangle.x, rectangle2.y, rectangle2.x - rectangle.x, rectangle2.height));
            } else if (bl4) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle2.x - rectangle.x, rectangle.height));
                int n = rectangle2.x + rectangle2.width;
                list.add(new Rectangle(n, rectangle.y, rectangle.x + rectangle.width - n, rectangle.height));
                int n6 = rectangle2.y + rectangle2.height;
                list.add(new Rectangle(rectangle2.x, n6, rectangle2.width, rectangle.y + rectangle.height - n6));
            } else if (bl) {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle2.y - rectangle.y));
                int n = rectangle.y + rectangle.height - rectangle2.y;
                list.add(new Rectangle(rectangle.x, rectangle2.y, rectangle2.x - rectangle.x, n));
                int n7 = rectangle2.x + rectangle2.width;
                list.add(new Rectangle(n7, rectangle2.y, rectangle.x + rectangle.width - n7, n));
            } else {
                list.add(new Rectangle(rectangle.x, rectangle.y, rectangle.width, rectangle2.y - rectangle.y));
                int n = rectangle2.y + rectangle2.height;
                list.add(new Rectangle(rectangle.x, n, rectangle.width, rectangle.y + rectangle.height - n));
                list.add(new Rectangle(rectangle.x, rectangle2.y, rectangle2.x - rectangle.x, rectangle2.height));
                int n8 = rectangle2.x + rectangle2.width;
                list.add(new Rectangle(n8, rectangle2.y, rectangle.x + rectangle.width - n8, rectangle2.height));
            }
        }
    }

    public static Rectangle[] getComponentVisibleArea(Component component, Filter filter) {
        Serializable serializable;
        Component component2;
        Window window = SwingUtilities.getWindowAncestor(component);
        int n = component.getWidth();
        int n2 = component.getHeight();
        if (window == null || !component.isShowing() || n <= 0 || n2 <= 0) {
            return new Rectangle[0];
        }
        Rectangle rectangle = new Rectangle(0, 0, n, n2);
        Rectangle[] rectangleArray = new Rectangle[]{new Rectangle(n, n2)};
        if (component instanceof Container) {
            component2 = (Container)component;
            block4: for (int i = ((Container)component2).getComponentCount() - 1; i >= 0; --i) {
                serializable = ((Container)component2).getComponent(i);
                if (!((Component)serializable).isVisible()) continue;
                switch (filter.accept(serializable)) {
                    case YES: {
                        rectangle.setBounds(((Component)serializable).getX(), ((Component)serializable).getY(), ((Component)serializable).getWidth(), ((Component)serializable).getHeight());
                        rectangleArray = UIUtils.subtract(rectangleArray, rectangle);
                        continue block4;
                    }
                    case TEST_CHILDREN: {
                        if (!(serializable instanceof Container)) continue block4;
                        rectangleArray = UIUtils.getChildrenVisibleArea(component, filter, rectangleArray, (Container)serializable, null);
                    }
                }
            }
        }
        if (rectangleArray.length == 0) {
            return rectangleArray;
        }
        component2 = component;
        Container container = component2.getParent();
        while (container != null && !(container instanceof Window)) {
            serializable = container.getSize();
            rectangle.setBounds(0, 0, ((Dimension)serializable).width, ((Dimension)serializable).height);
            Rectangle rectangle2 = SwingUtilities.convertRectangle(container, rectangle, component);
            ArrayList<Rectangle> arrayList = new ArrayList<Rectangle>();
            for (Rectangle rectangle3 : rectangleArray) {
                Rectangle rectangle4 = rectangle3.intersection(rectangle2);
                if (rectangle4.isEmpty()) continue;
                arrayList.add(rectangle4);
            }
            rectangleArray = arrayList.toArray(new Rectangle[0]);
            if (container instanceof JComponent && !((JComponent)container).isOptimizedDrawingEnabled()) {
                rectangleArray = UIUtils.getChildrenVisibleArea(component, filter, rectangleArray, container, component2);
            }
            if (rectangleArray.length == 0) {
                return rectangleArray;
            }
            component2 = container;
            container = component2.getParent();
        }
        return rectangleArray;
    }

    public static void setComponentTransparencyHint(Component component, TransparencyType transparencyType) {
        if (component instanceof JComponent) {
            ((JComponent)component).putClientProperty(COMPONENT_TRANSPARENT_CLIENT_PROPERTY, (Object)transparencyType);
        }
    }

    public static TransparencyType getComponentTransparency(Component component) {
        if (!(component instanceof JComponent) || component.isOpaque()) {
            return TransparencyType.OPAQUE;
        }
        TransparencyType transparencyType = (TransparencyType)((Object)((JComponent)component).getClientProperty(COMPONENT_TRANSPARENT_CLIENT_PROPERTY));
        if (transparencyType != null) {
            return transparencyType;
        }
        Container container = component.getParent();
        if (container instanceof JRootPane && ((JRootPane)container).getGlassPane() == component) {
            return TransparencyType.TRANSPARENT_WITH_OPAQUE_CHILDREN;
        }
        return TransparencyType.OPAQUE;
    }

    private static Rectangle[] getChildrenVisibleArea(Component component, Filter filter, Rectangle[] rectangleArray, Container container, Component component2) {
        Component component3;
        Component[] componentArray;
        Serializable serializable;
        if (container instanceof JLayeredPane) {
            serializable = (JLayeredPane)container;
            ArrayList<Component> arrayList = new ArrayList<Component>(((Container)serializable).getComponentCount() - 1);
            int n = component2 == null ? Integer.MIN_VALUE : ((JLayeredPane)serializable).getLayer(component2);
            for (int i = ((JLayeredPane)serializable).highestLayer(); i >= n; --i) {
                Component[] componentArray2;
                for (Component component4 : componentArray2 = ((JLayeredPane)serializable).getComponentsInLayer(i)) {
                    if (component4 == component2) break;
                    arrayList.add(component4);
                }
            }
            componentArray = arrayList.toArray(new Component[0]);
        } else {
            componentArray = container.getComponents();
        }
        serializable = new Rectangle();
        for (int i = 0; i < componentArray.length && (component3 = componentArray[i]) != component2; ++i) {
            if (!component3.isVisible()) continue;
            Filter.Acceptance acceptance = filter.accept(component3);
            if (acceptance == Filter.Acceptance.YES) {
                ((Rectangle)serializable).setBounds(component3.getX(), component3.getY(), component3.getWidth(), component3.getHeight());
                rectangleArray = UIUtils.subtract(rectangleArray, SwingUtilities.convertRectangle(container, (Rectangle)serializable, component));
                continue;
            }
            if (acceptance != Filter.Acceptance.TEST_CHILDREN || !(component3 instanceof Container)) continue;
            rectangleArray = UIUtils.getChildrenVisibleArea(component, filter, rectangleArray, (Container)component3, null);
        }
        return rectangleArray;
    }

    public static Rectangle getBounds(Rectangle[] rectangleArray) {
        Rectangle rectangle = new Rectangle();
        if (rectangleArray.length > 0) {
            rectangle.setBounds(rectangleArray[0]);
            for (int i = 1; i < rectangleArray.length; ++i) {
                Rectangle.union(rectangle, rectangleArray[i], rectangle);
            }
        }
        return rectangle;
    }

    public static void setPreferredLookAndFeel() {
        try {
            String string = UIManager.getSystemLookAndFeelClassName();
            if (!"com.sun.java.swing.plaf.gtk.GTKLookAndFeel".equals(string)) {
                UIManager.setLookAndFeel(string);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void revalidate(Component component) {
        if (component instanceof JComponent) {
            ((JComponent)component).revalidate();
        } else {
            component.invalidate();
            component.validate();
        }
    }

    public static Point2D.Double getScaledFactor(Component component) {
        AffineTransform affineTransform;
        GraphicsConfiguration graphicsConfiguration = component.getGraphicsConfiguration();
        if (graphicsConfiguration != null && (affineTransform = graphicsConfiguration.getDefaultTransform()) != null) {
            double d = affineTransform.getScaleX();
            double d2 = affineTransform.getScaleY();
            return new Point2D.Double(d, d2);
        }
        return new Point2D.Double(1.0, 1.0);
    }

    public static enum TransparencyType {
        OPAQUE,
        TRANSPARENT_WITH_OPAQUE_CHILDREN,
        NOT_VISIBLE;

    }
}

