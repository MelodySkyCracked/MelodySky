/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.custom.CTabFolder2Listener;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabFolderLayout;
import org.eclipse.swt.custom.CTabFolderListener;
import org.eclipse.swt.custom.CTabFolderRenderer;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.lI;
import org.eclipse.swt.custom.lIIl;
import org.eclipse.swt.custom.lIlII;
import org.eclipse.swt.custom.lIlll;
import org.eclipse.swt.custom.llI;
import org.eclipse.swt.custom.lllll;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.SWTEventListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.TypedListener;

public class CTabFolder
extends Composite {
    public int marginWidth = 0;
    public int marginHeight = 0;
    @Deprecated
    public int MIN_TAB_WIDTH = 4;
    @Deprecated
    public static RGB borderInsideRGB = new RGB(132, 130, 132);
    @Deprecated
    public static RGB borderMiddleRGB = new RGB(143, 141, 138);
    @Deprecated
    public static RGB borderOutsideRGB = new RGB(171, 168, 165);
    boolean onBottom = false;
    boolean single = false;
    boolean simple = true;
    int fixedTabHeight = -1;
    int tabHeight;
    int minChars = 20;
    boolean borderVisible = false;
    CTabFolderRenderer renderer;
    CTabItem[] items = new CTabItem[0];
    int firstIndex = -1;
    int selectedIndex = -1;
    int[] priority = new int[0];
    boolean mru = false;
    Listener listener;
    boolean ignoreTraverse;
    boolean useDefaultRenderer;
    CTabFolder2Listener[] folderListeners = new CTabFolder2Listener[0];
    CTabFolderListener[] tabListeners = new CTabFolderListener[0];
    Image selectionBgImage;
    Color[] selectionGradientColors;
    int[] selectionGradientPercents;
    boolean selectionGradientVertical;
    Color selectionForeground;
    Color selectionBackground;
    Color[] gradientColors;
    int[] gradientPercents;
    boolean gradientVertical;
    boolean showUnselectedImage = true;
    boolean showClose = false;
    boolean showUnselectedClose = true;
    boolean showMin = false;
    boolean minimized = false;
    boolean showMax = false;
    boolean maximized = false;
    ToolBar minMaxTb;
    ToolItem maxItem;
    ToolItem minItem;
    Image maxImage;
    Image minImage;
    boolean hoverTb;
    Rectangle hoverRect = new Rectangle(0, 0, 0, 0);
    boolean hovering;
    boolean hoverTimerRunning;
    boolean highlight;
    boolean highlightEnabled = true;
    boolean showChevron = false;
    Menu showMenu;
    ToolBar chevronTb;
    ToolItem chevronItem;
    int chevronCount;
    boolean chevronVisible = true;
    Image chevronImage;
    Control topRight;
    int topRightAlignment = 131072;
    boolean ignoreResize;
    Control[] controls;
    int[] controlAlignments;
    Rectangle[] controlRects;
    Rectangle[] bkImageBounds;
    Image[] controlBkImages;
    int updateFlags;
    static final int REDRAW = 2;
    static final int REDRAW_TABS = 4;
    static final int UPDATE_TAB_HEIGHT = 8;
    Runnable updateRun;
    boolean inDispose = false;
    Point oldSize;
    Font oldFont;
    static final int DEFAULT_WIDTH = 64;
    static final int DEFAULT_HEIGHT = 64;
    static final int SELECTION_FOREGROUND = 24;
    static final int SELECTION_BACKGROUND = 25;
    static final int FOREGROUND = 21;
    static final int BACKGROUND = 22;
    static final int SPACING = 3;

    public CTabFolder(Composite composite, int n) {
        super(composite, CTabFolder.checkStyle(composite, n));
        this.init(n);
    }

    void init(int n) {
        super.setLayout(new CTabFolderLayout());
        int n2 = super.getStyle();
        this.oldFont = this.getFont();
        this.onBottom = (n2 & 0x400) != 0;
        this.showClose = (n2 & 0x40) != 0;
        this.single = (n2 & 4) != 0;
        this.borderVisible = (n & 0x800) != 0;
        Display display = this.getDisplay();
        this.selectionForeground = display.getSystemColor(24);
        this.selectionBackground = display.getSystemColor(25);
        this.renderer = new CTabFolderRenderer(this);
        this.useDefaultRenderer = true;
        this.controls = new Control[0];
        this.controlAlignments = new int[0];
        this.controlRects = new Rectangle[0];
        this.controlBkImages = new Image[0];
        this.updateTabHeight(false);
        this.listener = this::lambda$init$0;
        int[] nArray = new int[]{12, 29, 15, 16, 1, 35, 8, 3, 6, 7, 32, 5, 4, 9, 11, 31, 26, 27};
        int[] nArray2 = nArray;
        for (int n3 : nArray) {
            this.addListener(n3, this.listener);
        }
        this.initAccessible();
    }

    void onDeactivate(Event event) {
        if (!this.highlightEnabled) {
            return;
        }
        this.highlight = false;
        this.redraw();
    }

    void onActivate(Event event) {
        if (!this.highlightEnabled) {
            return;
        }
        this.highlight = true;
        this.redraw();
    }

    static int checkStyle(Composite composite, int n) {
        int n2 = 109053126;
        if (((n &= 0x68004C6) & 0x80) != 0) {
            n &= 0xFFFFFBFF;
        }
        if ((n & 2) != 0) {
            n &= 0xFFFFFFFB;
        }
        if (((n |= 0x100000) & 0x4000000) != 0) {
            return n;
        }
        if ((composite.getStyle() & 0x8000000) != 0 && (n & 0x2000000) == 0) {
            return n;
        }
        return n | 0x20000000;
    }

    public void addCTabFolder2Listener(CTabFolder2Listener cTabFolder2Listener) {
        this.checkWidget();
        if (cTabFolder2Listener == null) {
            SWT.error(4);
        }
        CTabFolder2Listener[] cTabFolder2ListenerArray = new CTabFolder2Listener[this.folderListeners.length + 1];
        System.arraycopy(this.folderListeners, 0, cTabFolder2ListenerArray, 0, this.folderListeners.length);
        this.folderListeners = cTabFolder2ListenerArray;
        cTabFolder2ListenerArray[this.folderListeners.length - 1] = cTabFolder2Listener;
    }

    @Deprecated
    public void addCTabFolderListener(CTabFolderListener cTabFolderListener) {
        this.checkWidget();
        if (cTabFolderListener == null) {
            SWT.error(4);
        }
        CTabFolderListener[] cTabFolderListenerArray = new CTabFolderListener[this.tabListeners.length + 1];
        System.arraycopy(this.tabListeners, 0, cTabFolderListenerArray, 0, this.tabListeners.length);
        this.tabListeners = cTabFolderListenerArray;
        cTabFolderListenerArray[this.tabListeners.length - 1] = cTabFolderListener;
        if (!this.showClose) {
            this.showClose = true;
            this.updateFolder(2);
        }
    }

    public void addSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            SWT.error(4);
        }
        TypedListener typedListener = new TypedListener(selectionListener);
        this.addListener(13, typedListener);
        this.addListener(14, typedListener);
    }

    /*
     * WARNING - void declaration
     */
    Rectangle[] computeControlBounds(Point point, boolean[][] blArray) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        if (this.controls == null || this.controls.length == 0) {
            return new Rectangle[0];
        }
        Rectangle[] rectangleArray = new Rectangle[this.controls.length];
        for (int i = 0; i < rectangleArray.length; ++i) {
            rectangleArray[i] = new Rectangle(0, 0, 0, 0);
        }
        Rectangle rectangle = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
        int n7 = rectangle.width + rectangle.x;
        int n8 = -rectangle.x;
        int n9 = rectangle.height + rectangle.y;
        int n10 = -rectangle.y;
        Point[] pointArray = new Point[this.controls.length];
        boolean[] blArray2 = new boolean[this.controls.length];
        int n11 = 0;
        int n12 = n8 + 3;
        int n13 = 0;
        int n14 = 0;
        boolean bl = false;
        for (n6 = 0; n6 < this.controls.length; ++n6) {
            Object[] objectArray = pointArray;
            n5 = n6;
            Point point2 = !this.controls[n6].isDisposed() && this.controls[n6].getVisible() ? this.controls[n6].computeSize(-1, -1) : new Point(0, 0);
            objectArray[n5] = point2;
            Point object = point2;
            int n21 = this.controlAlignments[n6];
            if ((n21 & 0x4000) != 0) {
                rectangleArray[n6].width = object.x;
                rectangleArray[n6].height = this.getControlHeight(object);
                rectangleArray[n6].x = n12;
                rectangleArray[n6].y = this.getControlY(point, rectangleArray, n9, n10, n6);
                n12 += object.x;
                n11 += object.x;
                continue;
            }
            if ((n21 & 0x40) == 0 && object.x > 0) {
                bl = true;
            }
            if ((n21 & 0x44) == 0) {
                n13 += object.x;
            }
            n14 += object.x;
        }
        if (n11 > 0) {
            n11 += 6;
        }
        n6 = 0;
        for (CTabItem n18 : this.items) {
            if (!n18.showing) continue;
            n6 += n18.width;
        }
        int n16 = point.x - n8 - n11 - n7;
        n5 = Math.max(0, n16 - n6 - n13);
        if (bl) {
            n5 -= 6;
        }
        n12 = point.x - n7 - 3;
        if (n6 + n14 <= n16) {
            for (n4 = 0; n4 < this.controls.length; ++n4) {
                int n19 = this.controlAlignments[n4];
                if ((n19 & 0x20000) != 0) {
                    Point n23 = pointArray[n4];
                    rectangleArray[n4].width = n23.x;
                    rectangleArray[n4].height = this.getControlHeight(n23);
                    rectangleArray[n4].x = n12 -= n23.x;
                    rectangleArray[n4].y = this.getControlY(point, rectangleArray, n9, n10, n4);
                    if ((n19 & 0x44) != 0) {
                        n5 -= n23.x;
                    }
                }
                if (pointArray[n4].y < this.tabHeight || this.fixedTabHeight != -1) continue;
                blArray2[n4] = true;
            }
        } else {
            for (n4 = 0; n4 < this.controls.length; ++n4) {
                int n22 = this.controlAlignments[n4];
                Point point2 = pointArray[n4];
                if ((n22 & 0x20000) == 0) continue;
                if ((n22 & 0x44) == 0) {
                    rectangleArray[n4].width = point2.x;
                    rectangleArray[n4].height = this.getControlHeight(point2);
                    rectangleArray[n4].x = n12 -= point2.x;
                    rectangleArray[n4].y = this.getControlY(point, rectangleArray, n9, n10, n4);
                    continue;
                }
                if ((n22 & 0x40) != 0 && point2.x < n5) {
                    rectangleArray[n4].width = point2.x;
                    rectangleArray[n4].height = this.getControlHeight(point2);
                    rectangleArray[n4].x = n12 -= point2.x;
                    rectangleArray[n4].y = this.getControlY(point, rectangleArray, n9, n10, n4);
                    n5 -= point2.x;
                    continue;
                }
                if ((n22 & 4) != 0 && (n22 & 0x40) == 0) {
                    rectangleArray[n4].width = 0;
                    rectangleArray[n4].height = this.getControlHeight(point2);
                    rectangleArray[n4].x = n12;
                    rectangleArray[n4].y = this.getControlY(point, rectangleArray, n9, n10, n4);
                    continue;
                }
                if ((n22 & 0x40) == 0) continue;
                blArray2[n4] = true;
            }
        }
        if (n5 > 0) {
            void var20_30;
            n4 = 0;
            boolean bl2 = false;
            while (var20_30 < this.controls.length) {
                int n15 = this.controlAlignments[var20_30];
                if ((n15 & 0x20000) != 0 && (n15 & 4) != 0 && !blArray2[var20_30]) {
                    ++n4;
                }
                ++var20_30;
            }
            if (n4 != 0) {
                int n17 = n5 / n4;
                int n18 = 0;
                for (n3 = 0; n3 < this.controls.length; ++n3) {
                    Rectangle rectangle2;
                    n2 = this.controlAlignments[n3];
                    if ((n2 & 0x20000) == 0) continue;
                    if ((n2 & 4) != 0 && !blArray2[n3]) {
                        rectangle2 = rectangleArray[n3];
                        rectangle2.width += n17;
                        n18 += n17;
                    }
                    if (blArray2[n3]) continue;
                    rectangle2 = rectangleArray[n3];
                    rectangle2.x -= n18;
                }
            }
        }
        Rectangle rectangle3 = this.renderer.computeTrim(-1, 0, 0, 0, 0, 0);
        int n19 = rectangle3.width + rectangle3.x;
        int n20 = -rectangle3.x;
        n3 = point.x - n20 - n19;
        n12 = point.x - n19;
        n2 = this.onBottom ? this.getSize().y - this.getTabHeight() + 2 * rectangle3.y : -rectangle3.y;
        n5 = n3;
        int n21 = 0;
        for (n = 0; n < this.controls.length; ++n) {
            Point point3 = pointArray[n];
            if (!blArray2[n]) continue;
            if (n5 > point3.x) {
                rectangleArray[n].width = point3.x;
                rectangleArray[n].y = this.onBottom ? n2 - point3.y : n2;
                rectangleArray[n].height = point3.y;
                rectangleArray[n].x = n12 -= point3.x;
                n5 -= point3.x;
                n21 = Math.max(n21, point3.y);
                continue;
            }
            n12 = point.x - n19;
            n2 += n21;
            n21 = 0;
            n5 = n3;
            if (n5 > point3.x) {
                --n;
                continue;
            }
            point3 = this.controls[n].isDisposed() ? new Point(0, 0) : this.controls[n].computeSize(n3, -1);
            rectangleArray[n].width = n3;
            rectangleArray[n].y = this.onBottom ? n2 - point3.y : n2;
            rectangleArray[n].height = point3.y;
            rectangleArray[n].x = point.x - point3.x - n19;
            n2 += point3.y;
        }
        if (this.showChevron) {
            n = 0;
            int n22 = -1;
            while (n < this.priority.length && this.items[this.priority[n]].showing) {
                n22 = Math.max(n22, this.priority[n++]);
            }
            if (n22 == -1) {
                n22 = this.selectedIndex;
            }
            if (n22 != -1) {
                CTabItem cTabItem = this.items[n22];
                int n23 = cTabItem.x + cTabItem.width + 3;
                if (!this.simple && n22 == this.selectedIndex) {
                    n23 -= this.renderer.curveIndent - 7;
                }
                rectangleArray[this.controls.length - 1].x = n23;
            }
        }
        if (blArray != null) {
            blArray[0] = blArray2;
        }
        return rectangleArray;
    }

    int getControlHeight(Point point) {
        return this.fixedTabHeight == -1 ? Math.max(this.tabHeight - 1, point.y) : point.y;
    }

    @Override
    public Rectangle computeTrim(int n, int n2, int n3, int n4) {
        this.checkWidget();
        Rectangle rectangle = this.renderer.computeTrim(-1, 0, n, n2, n3, n4);
        Point point = new Point(n3, n4);
        int n5 = this.getWrappedHeight(point);
        if (this.onBottom) {
            Rectangle rectangle2 = rectangle;
            rectangle2.height += n5;
        } else {
            Rectangle rectangle3 = rectangle;
            rectangle3.y -= n5;
            Rectangle rectangle4 = rectangle;
            rectangle4.height += n5;
        }
        return rectangle;
    }

    Image createButtonImage(Display display, int n) {
        GC gC = new GC(this);
        Point point = this.renderer.computeSize(n, 0, gC, -1, -1);
        gC.dispose();
        Rectangle rectangle = this.renderer.computeTrim(n, 0, 0, 0, 0, 0);
        Image image = new Image((Device)display, point.x - rectangle.width, point.y - rectangle.height);
        GC gC2 = new GC(image);
        Color color = this.renderer.parent.getBackground();
        gC2.setBackground(color);
        gC2.fillRectangle(image.getBounds());
        this.renderer.draw(n, 0, new Rectangle(rectangle.x, rectangle.y, point.x, point.y), gC2);
        gC2.dispose();
        ImageData imageData = image.getImageData(DPIUtil.getDeviceZoom());
        imageData.transparentPixel = imageData.palette.getPixel(color.getRGB());
        image.dispose();
        image = new Image((Device)display, new DPIUtil.AutoScaleImageDataProvider(display, imageData, DPIUtil.getDeviceZoom()));
        return image;
    }

    void createItem(CTabItem cTabItem, int n) {
        if (0 > n || n > this.getItemCount()) {
            SWT.error(6);
        }
        cTabItem.parent = this;
        CTabItem[] cTabItemArray = new CTabItem[this.items.length + 1];
        System.arraycopy(this.items, 0, cTabItemArray, 0, n);
        cTabItemArray[n] = cTabItem;
        System.arraycopy(this.items, n, cTabItemArray, n + 1, this.items.length - n);
        this.items = cTabItemArray;
        if (this.selectedIndex >= n) {
            ++this.selectedIndex;
        }
        int[] nArray = new int[this.priority.length + 1];
        int n2 = 0;
        int n3 = this.priority.length;
        for (int n4 : this.priority) {
            if (!this.mru && n4 == n) {
                n3 = n2++;
            }
            nArray[n2++] = n4 >= n ? n4 + 1 : n4;
        }
        nArray[n3] = n;
        this.priority = nArray;
        if (this.items.length == 1) {
            this.updateFolder(10);
        } else {
            this.updateFolder(4);
        }
    }

    void destroyItem(CTabItem cTabItem) {
        if (this.inDispose) {
            return;
        }
        int n = this.indexOf(cTabItem);
        if (n == -1) {
            return;
        }
        if (this.items.length == 1) {
            this.items = new CTabItem[0];
            this.priority = new int[0];
            this.firstIndex = -1;
            this.selectedIndex = -1;
            Control control = cTabItem.control;
            if (control != null && !control.isDisposed()) {
                control.setVisible(false);
            }
            this.setToolTipText(null);
            this.updateButtons();
            this.setButtonBounds();
            this.redraw();
            return;
        }
        CTabItem[] cTabItemArray = new CTabItem[this.items.length - 1];
        System.arraycopy(this.items, 0, cTabItemArray, 0, n);
        System.arraycopy(this.items, n + 1, cTabItemArray, n, this.items.length - n - 1);
        this.items = cTabItemArray;
        int[] nArray = new int[this.priority.length - 1];
        int n2 = 0;
        for (int n3 : this.priority) {
            if (n3 == n) continue;
            nArray[n2++] = n3 > n ? n3 - 1 : n3;
        }
        this.priority = nArray;
        if (this.selectedIndex == n) {
            Object object = cTabItem.getControl();
            this.selectedIndex = -1;
            int n4 = this.mru ? this.priority[0] : Math.max(0, n - 1);
            this.setSelection(n4, true);
            if (object != null && !object.isDisposed()) {
                ((Control)object).setVisible(false);
            }
        } else if (this.selectedIndex > n) {
            --this.selectedIndex;
        }
        this.requestLayout();
        this.updateFolder(12);
    }

    public boolean getBorderVisible() {
        this.checkWidget();
        return this.borderVisible;
    }

    ToolBar getChevron() {
        if (this.chevronTb == null) {
            this.chevronTb = new ToolBar(this, 0x800000);
            this.initAccessibleChevronTb();
            this.addTabControl(this.chevronTb, 131072, -1, false);
        }
        if (this.chevronItem == null) {
            this.chevronItem = new ToolItem(this.chevronTb, 8);
            this.chevronItem.setToolTipText(SWT.getMessage("SWT_ShowList"));
            this.chevronItem.addListener(13, this.listener);
        }
        return this.chevronTb;
    }

    boolean getChevronVisible() {
        this.checkWidget();
        return this.chevronVisible;
    }

    @Override
    public Rectangle getClientArea() {
        Rectangle rectangle;
        this.checkWidget();
        Rectangle rectangle2 = this.renderer.computeTrim(-1, 4, 0, 0, 0, 0);
        Point point = this.getSize();
        int n = this.getWrappedHeight(point);
        if (this.onBottom) {
            rectangle = rectangle2;
            rectangle.height += n;
        } else {
            rectangle = rectangle2;
            rectangle.y -= n;
            Rectangle rectangle3 = rectangle2;
            rectangle3.height += n;
        }
        if (this.minimized) {
            return new Rectangle(-rectangle2.x, -rectangle2.y, 0, 0);
        }
        int n2 = point.x - rectangle2.width;
        int n3 = point.y - rectangle2.height;
        return new Rectangle(-rectangle2.x, -rectangle2.y, n2, n3);
    }

    public CTabItem getItem(int n) {
        if (n < 0 || n >= this.items.length) {
            SWT.error(6);
        }
        return this.items[n];
    }

    public CTabItem getItem(Point point) {
        if (this.items.length == 0) {
            return null;
        }
        this.runUpdate();
        Point point2 = this.getSize();
        Rectangle rectangle = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
        if (point2.x <= rectangle.width) {
            return null;
        }
        for (int n : this.priority) {
            CTabItem cTabItem = this.items[n];
            Rectangle rectangle2 = cTabItem.getBounds();
            if (!rectangle2.contains(point)) continue;
            return cTabItem;
        }
        return null;
    }

    public int getItemCount() {
        return this.items.length;
    }

    public CTabItem[] getItems() {
        CTabItem[] cTabItemArray = new CTabItem[this.items.length];
        System.arraycopy(this.items, 0, cTabItemArray, 0, this.items.length);
        return cTabItemArray;
    }

    int getLeftItemEdge(GC gC, int n) {
        Rectangle rectangle = this.renderer.computeTrim(n, 0, 0, 0, 0, 0);
        int n2 = -rectangle.x;
        int n3 = 0;
        for (int i = 0; i < this.controls.length; ++i) {
            if ((this.controlAlignments[i] & 0x4000) == 0 || this.controls[i].isDisposed() || !this.controls[i].getVisible()) continue;
            n3 += this.controls[i].computeSize((int)-1, (int)-1).x;
        }
        if (n3 != 0) {
            n3 += 6;
        }
        return Math.max(0, n2 += n3);
    }

    char _findMnemonic(String string) {
        if (string == null) {
            return '\u0000';
        }
        int n = 0;
        int n2 = string.length();
        while (true) {
            if (n < n2 && string.charAt(n) != '&') {
                ++n;
                continue;
            }
            if (++n >= n2) {
                return '\u0000';
            }
            if (string.charAt(n) != '&') {
                return Character.toLowerCase(string.charAt(n));
            }
            if (++n >= n2) break;
        }
        return '\u0000';
    }

    String stripMnemonic(String string) {
        int n = 0;
        int n2 = string.length();
        while (true) {
            if (n < n2 && string.charAt(n) != '&') {
                ++n;
                continue;
            }
            if (++n >= n2) {
                return string;
            }
            if (string.charAt(n) != '&') {
                return string.substring(0, n - 1) + string.substring(n, n2);
            }
            if (++n >= n2) break;
        }
        return string;
    }

    public boolean getMinimized() {
        this.checkWidget();
        return this.minimized;
    }

    public boolean getMinimizeVisible() {
        this.checkWidget();
        return this.showMin;
    }

    public int getMinimumCharacters() {
        this.checkWidget();
        return this.minChars;
    }

    public boolean getMaximized() {
        this.checkWidget();
        return this.maximized;
    }

    public boolean getMaximizeVisible() {
        this.checkWidget();
        return this.showMax;
    }

    public boolean getMRUVisible() {
        this.checkWidget();
        return this.mru;
    }

    public CTabFolderRenderer getRenderer() {
        this.checkWidget();
        return this.renderer;
    }

    int getRightItemEdge(GC gC) {
        Rectangle rectangle = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
        int n = this.getSize().x - (rectangle.width + rectangle.x);
        int n2 = 0;
        for (int i = 0; i < this.controls.length; ++i) {
            int n3 = this.controlAlignments[i];
            if ((n3 & 0x40) != 0 || (n3 & 0x4000) != 0 || this.controls[i].isDisposed() || !this.controls[i].getVisible()) continue;
            Point point = this.controls[i].computeSize(-1, -1);
            n2 += point.x;
        }
        if (n2 != 0) {
            n2 += 6;
        }
        return Math.max(0, n -= n2);
    }

    public CTabItem getSelection() {
        if (this.selectedIndex == -1) {
            return null;
        }
        return this.items[this.selectedIndex];
    }

    public Color getSelectionBackground() {
        this.checkWidget();
        return this.selectionBackground;
    }

    public Color getSelectionForeground() {
        this.checkWidget();
        return this.selectionForeground;
    }

    public int getSelectionIndex() {
        return this.selectedIndex;
    }

    public boolean getSimple() {
        this.checkWidget();
        return this.simple;
    }

    public boolean getSingle() {
        this.checkWidget();
        return this.single;
    }

    @Override
    public int getStyle() {
        int n = super.getStyle();
        n &= 0xFFFFFB7F;
        n |= this.onBottom ? 1024 : 128;
        n &= 0xFFFFFFF9;
        n |= this.single ? 4 : 2;
        if (this.borderVisible) {
            n |= 0x800;
        }
        n &= 0xFFFFFFBF;
        if (this.showClose) {
            n |= 0x40;
        }
        return n;
    }

    public int getTabHeight() {
        this.checkWidget();
        if (this.fixedTabHeight != -1) {
            return this.fixedTabHeight;
        }
        return this.tabHeight - 1;
    }

    public int getTabPosition() {
        this.checkWidget();
        return this.onBottom ? 1024 : 128;
    }

    public Control getTopRight() {
        this.checkWidget();
        return this.topRight;
    }

    public int getTopRightAlignment() {
        this.checkWidget();
        return this.topRightAlignment;
    }

    public boolean getUnselectedCloseVisible() {
        this.checkWidget();
        return this.showUnselectedClose;
    }

    public boolean getUnselectedImageVisible() {
        this.checkWidget();
        return this.showUnselectedImage;
    }

    public int indexOf(CTabItem cTabItem) {
        this.checkWidget();
        if (cTabItem == null) {
            SWT.error(4);
        }
        for (int i = 0; i < this.items.length; ++i) {
            if (this.items[i] != cTabItem) continue;
            return i;
        }
        return -1;
    }

    void initAccessible() {
        Accessible accessible = this.getAccessible();
        accessible.addAccessibleListener(new lllll(this));
        accessible.addAccessibleControlListener(new lIlll(this));
        this.addListener(13, arg_0 -> this.lambda$initAccessible$1(accessible, arg_0));
        this.addListener(15, arg_0 -> this.lambda$initAccessible$2(accessible, arg_0));
    }

    void initAccessibleMinMaxTb() {
        this.minMaxTb.getAccessible().addAccessibleListener(new lIlII(this));
    }

    void initAccessibleChevronTb() {
        this.chevronTb.getAccessible().addAccessibleListener(new llI(this));
    }

    void onKeyDown(Event event) {
        this.runUpdate();
        switch (event.keyCode) {
            case 0x1000003: 
            case 0x1000004: {
                int n;
                int n2;
                int n3 = this.items.length;
                if (n3 == 0) {
                    return;
                }
                if (this.selectedIndex == -1) {
                    return;
                }
                int n4 = (this.getStyle() & 0x4000000) != 0 ? 0x1000004 : 0x1000003;
                int n5 = n2 = event.keyCode == n4 ? -1 : 1;
                if (!this.mru) {
                    n = this.selectedIndex + n2;
                } else {
                    int[] nArray = new int[this.items.length];
                    int n6 = 0;
                    int n7 = -1;
                    for (int i = 0; i < this.items.length; ++i) {
                        if (!this.items[i].showing) continue;
                        if (i == this.selectedIndex) {
                            n7 = n6;
                        }
                        nArray[n6++] = i;
                    }
                    if (n7 + n2 < 0 || n7 + n2 >= n6) {
                        if (this.showChevron) {
                            Rectangle rectangle = this.chevronItem.getBounds();
                            rectangle = event.display.map((Control)this.chevronTb, (Control)this, rectangle);
                            CTabFolderEvent cTabFolderEvent = new CTabFolderEvent(this);
                            cTabFolderEvent.widget = this;
                            cTabFolderEvent.time = event.time;
                            cTabFolderEvent.x = rectangle.x;
                            cTabFolderEvent.y = rectangle.y;
                            cTabFolderEvent.width = rectangle.width;
                            cTabFolderEvent.height = rectangle.height;
                            cTabFolderEvent.doit = true;
                            for (CTabFolder2Listener cTabFolder2Listener : this.folderListeners) {
                                cTabFolder2Listener.showList(cTabFolderEvent);
                            }
                            if (cTabFolderEvent.doit && !this.isDisposed()) {
                                this.showList(rectangle);
                            }
                        }
                        return;
                    }
                    n = nArray[n7 + n2];
                }
                if (n < 0 || n >= n3) {
                    return;
                }
                this.setSelection(n, true);
                this.forceFocus();
                break;
            }
        }
    }

    void onDispose(Event event) {
        this.removeListener(12, this.listener);
        this.notifyListeners(12, event);
        event.type = 0;
        this.inDispose = true;
        if (this.showMenu != null && !this.showMenu.isDisposed()) {
            this.showMenu.dispose();
            this.showMenu = null;
        }
        int n = this.items.length;
        for (int i = 0; i < n; ++i) {
            if (this.items[i] == null) continue;
            this.items[i].dispose();
        }
        this.gradientColors = null;
        this.selectionGradientColors = null;
        this.selectionGradientPercents = null;
        this.selectionBgImage = null;
        this.selectionBackground = null;
        this.selectionForeground = null;
        if (this.controlBkImages != null) {
            for (n = 0; n < this.controlBkImages.length; ++n) {
                if (this.controlBkImages[n] == null) continue;
                this.controlBkImages[n].dispose();
                this.controlBkImages[n] = null;
            }
            this.controlBkImages = null;
        }
        this.controls = null;
        this.controlAlignments = null;
        this.controlRects = null;
        if (this.maxImage != null) {
            this.maxImage.dispose();
        }
        this.maxImage = null;
        if (this.minImage != null) {
            this.minImage.dispose();
        }
        this.minImage = null;
        if (this.chevronImage != null) {
            this.chevronImage.dispose();
        }
        this.chevronImage = null;
        if (this.renderer != null) {
            this.renderer.dispose();
        }
        this.renderer = null;
        this.minItem = null;
        this.maxItem = null;
        this.minMaxTb = null;
        this.chevronItem = null;
        this.chevronTb = null;
        if (this.folderListeners.length != 0) {
            this.folderListeners = new CTabFolder2Listener[0];
        }
        if (this.tabListeners.length != 0) {
            this.tabListeners = new CTabFolderListener[0];
        }
    }

    void onDragDetect(Event event) {
        boolean bl = false;
        for (CTabItem cTabItem : this.items) {
            if (!cTabItem.closeRect.contains(event.x, event.y)) continue;
            bl = true;
            break;
        }
        if (bl) {
            event.type = 0;
        }
    }

    void onFocus(Event event) {
        this.checkWidget();
        if (this.selectedIndex >= 0) {
            this.redraw();
        } else {
            this.setSelection(0, true);
        }
    }

    boolean onMnemonic(Event event, boolean bl) {
        char c = event.character;
        for (int i = 0; i < this.items.length; ++i) {
            char c2;
            if (this.items[i] == null || (c2 = this._findMnemonic(this.items[i].getText())) == '\u0000' || Character.toLowerCase(c) != c2) continue;
            if (bl) {
                this.setSelection(i, true);
                this.forceFocus();
            }
            return true;
        }
        return false;
    }

    void onMenuDetect(Event event) {
        if (event.detail == 1 && this.selectedIndex != -1) {
            CTabItem cTabItem = this.items[this.selectedIndex];
            Rectangle rectangle = this.getDisplay().map((Control)this, null, cTabItem.getBounds());
            if (!rectangle.contains(event.x, event.y)) {
                Rectangle rectangle2 = this.renderer.computeTrim(this.selectedIndex, 0, 0, 0, 0, 0);
                Rectangle rectangle3 = this.renderer.computeTrim(-8, 0, 0, 0, 0, 0);
                event.x = rectangle.x + rectangle.width - cTabItem.closeRect.width + rectangle2.x - rectangle3.width;
                event.y = rectangle.y - rectangle2.y - rectangle3.y;
            }
        }
    }

    void onMouseDoubleClick(Event event) {
        if (event.button != 1 || (event.stateMask & 0x100000) != 0 || (event.stateMask & 0x200000) != 0) {
            return;
        }
        Event event2 = new Event();
        event2.item = this.getItem(new Point(event.x, event.y));
        if (event2.item != null) {
            this.notifyListeners(14, event2);
        }
    }

    void onMouse(Event event) {
        if (this.isDisposed()) {
            return;
        }
        int n = event.x;
        int n2 = event.y;
        switch (event.type) {
            case 6: {
                this.setToolTipText(null);
                break;
            }
            case 7: {
                for (int i = 0; i < this.items.length; ++i) {
                    CTabItem cTabItem = this.items[i];
                    if (i != this.selectedIndex && cTabItem.closeImageState != 8) {
                        cTabItem.closeImageState = 8;
                        this.redraw(cTabItem.closeRect.x, cTabItem.closeRect.y, cTabItem.closeRect.width, cTabItem.closeRect.height, false);
                    }
                    if ((cTabItem.state & 0x20) != 0) {
                        CTabItem cTabItem2 = cTabItem;
                        cTabItem2.state &= 0xFFFFFFDF;
                        this.redraw(cTabItem.x, cTabItem.y, cTabItem.width, cTabItem.height, false);
                    }
                    if (i != this.selectedIndex || cTabItem.closeImageState == 0) continue;
                    cTabItem.closeImageState = 0;
                    this.redraw(cTabItem.closeRect.x, cTabItem.closeRect.y, cTabItem.closeRect.width, cTabItem.closeRect.height, false);
                }
                break;
            }
            case 3: 
            case 32: {
                if (this.hoverTb && this.hoverRect.contains(n, n2) && !this.hovering) {
                    this.hovering = true;
                    this.updateItems();
                    this.hoverTimerRunning = true;
                    event.display.timerExec(2000, new lI(this));
                    return;
                }
                if (event.button != 1) {
                    return;
                }
                CTabItem cTabItem = null;
                if (this.single) {
                    Object object;
                    if (this.selectedIndex != -1 && ((Rectangle)(object = this.items[this.selectedIndex].getBounds())).contains(n, n2)) {
                        cTabItem = this.items[this.selectedIndex];
                    }
                } else {
                    for (CTabItem cTabItem3 : this.items) {
                        Rectangle rectangle = cTabItem3.getBounds();
                        if (!rectangle.contains(n, n2)) continue;
                        cTabItem = cTabItem3;
                    }
                }
                if (cTabItem == null) break;
                if (cTabItem.closeRect.contains(n, n2)) {
                    cTabItem.closeImageState = 2;
                    this.redraw(cTabItem.closeRect.x, cTabItem.closeRect.y, cTabItem.closeRect.width, cTabItem.closeRect.height, false);
                    this.update();
                    return;
                }
                int n3 = this.indexOf(cTabItem);
                if (cTabItem.showing) {
                    int n4 = this.selectedIndex;
                    this.setSelection(n3, true);
                    if (n4 == this.selectedIndex) {
                        this.forceFocus();
                    }
                }
                return;
            }
            case 5: {
                this._setToolTipText(event.x, event.y);
                boolean bl = false;
                for (int i = 0; i < this.items.length; ++i) {
                    CTabItem cTabItem;
                    CTabItem cTabItem4 = this.items[i];
                    bl = false;
                    if (cTabItem4.getBounds().contains(n, n2)) {
                        bl = true;
                        if (cTabItem4.closeRect.contains(n, n2)) {
                            if (cTabItem4.closeImageState != 2 && cTabItem4.closeImageState != 32) {
                                cTabItem4.closeImageState = 32;
                                this.redraw(cTabItem4.closeRect.x, cTabItem4.closeRect.y, cTabItem4.closeRect.width, cTabItem4.closeRect.height, false);
                            }
                        } else if (cTabItem4.closeImageState != 0) {
                            cTabItem4.closeImageState = 0;
                            this.redraw(cTabItem4.closeRect.x, cTabItem4.closeRect.y, cTabItem4.closeRect.width, cTabItem4.closeRect.height, false);
                        }
                        if ((cTabItem4.state & 0x20) == 0) {
                            cTabItem = cTabItem4;
                            cTabItem.state |= 0x20;
                            this.redraw(cTabItem4.x, cTabItem4.y, cTabItem4.width, cTabItem4.height, false);
                        }
                    }
                    if (i != this.selectedIndex && cTabItem4.closeImageState != 8 && !bl) {
                        cTabItem4.closeImageState = 8;
                        this.redraw(cTabItem4.closeRect.x, cTabItem4.closeRect.y, cTabItem4.closeRect.width, cTabItem4.closeRect.height, false);
                    }
                    if ((cTabItem4.state & 0x20) != 0 && !bl) {
                        cTabItem = cTabItem4;
                        cTabItem.state &= 0xFFFFFFDF;
                        this.redraw(cTabItem4.x, cTabItem4.y, cTabItem4.width, cTabItem4.height, false);
                    }
                    if (i != this.selectedIndex || cTabItem4.closeImageState == 0 || bl) continue;
                    cTabItem4.closeImageState = 0;
                    this.redraw(cTabItem4.closeRect.x, cTabItem4.closeRect.y, cTabItem4.closeRect.width, cTabItem4.closeRect.height, false);
                }
                break;
            }
            case 4: {
                if (event.button != 1) {
                    return;
                }
                CTabItem cTabItem = null;
                if (this.single) {
                    Object object;
                    if (this.selectedIndex != -1 && ((Rectangle)(object = this.items[this.selectedIndex].getBounds())).contains(n, n2)) {
                        cTabItem = this.items[this.selectedIndex];
                    }
                } else {
                    for (CTabItem cTabItem5 : this.items) {
                        Rectangle rectangle = cTabItem5.getBounds();
                        if (!rectangle.contains(n, n2)) continue;
                        cTabItem = cTabItem5;
                    }
                }
                if (cTabItem == null || !cTabItem.closeRect.contains(n, n2)) break;
                boolean bl = cTabItem.closeImageState == 2;
                cTabItem.closeImageState = 32;
                this.redraw(cTabItem.closeRect.x, cTabItem.closeRect.y, cTabItem.closeRect.width, cTabItem.closeRect.height, false);
                if (!bl) {
                    return;
                }
                CTabFolderEvent cTabFolderEvent = new CTabFolderEvent(this);
                cTabFolderEvent.widget = this;
                cTabFolderEvent.time = event.time;
                cTabFolderEvent.item = cTabItem;
                cTabFolderEvent.doit = true;
                for (CTabFolder2Listener sWTEventListener : this.folderListeners) {
                    sWTEventListener.close(cTabFolderEvent);
                }
                for (SWTEventListener sWTEventListener : this.tabListeners) {
                    sWTEventListener.itemClosed(cTabFolderEvent);
                }
                if (cTabFolderEvent.doit) {
                    cTabItem.dispose();
                }
                if (this.isDisposed() || !cTabItem.isDisposed()) break;
                Display display = this.getDisplay();
                Point point = display.getCursorLocation();
                point = display.map(null, this, point.x, point.y);
                CTabItem cTabItem6 = this.getItem(point);
                if (cTabItem6 == null) break;
                if (cTabItem6.closeRect.contains(point)) {
                    if (cTabItem6.closeImageState == 2 || cTabItem6.closeImageState == 32) break;
                    cTabItem6.closeImageState = 32;
                    this.redraw(cTabItem6.closeRect.x, cTabItem6.closeRect.y, cTabItem6.closeRect.width, cTabItem6.closeRect.height, false);
                    break;
                }
                if (cTabItem6.closeImageState == 0) break;
                cTabItem6.closeImageState = 0;
                this.redraw(cTabItem6.closeRect.x, cTabItem6.closeRect.y, cTabItem6.closeRect.width, cTabItem6.closeRect.height, false);
            }
        }
    }

    void onPageTraversal(Event event) {
        int n = this.items.length;
        if (n == 0) {
            return;
        }
        int n2 = this.selectedIndex;
        if (n2 == -1) {
            n2 = 0;
        } else {
            int n3;
            int n4 = n3 = event.detail == 512 ? 1 : -1;
            if (!this.mru) {
                n2 = (this.selectedIndex + n3 + n) % n;
            } else {
                int[] nArray = new int[this.items.length];
                int n5 = 0;
                int n6 = -1;
                for (int i = 0; i < this.items.length; ++i) {
                    if (!this.items[i].showing) continue;
                    if (i == this.selectedIndex) {
                        n6 = n5;
                    }
                    nArray[n5++] = i;
                }
                if (n6 + n3 >= 0 && n6 + n3 < n5) {
                    n2 = nArray[n6 + n3];
                } else if (this.showChevron) {
                    Rectangle rectangle = this.chevronItem.getBounds();
                    rectangle = event.display.map((Control)this.chevronTb, (Control)this, rectangle);
                    CTabFolderEvent cTabFolderEvent = new CTabFolderEvent(this);
                    cTabFolderEvent.widget = this;
                    cTabFolderEvent.time = event.time;
                    cTabFolderEvent.x = rectangle.x;
                    cTabFolderEvent.y = rectangle.y;
                    cTabFolderEvent.width = rectangle.width;
                    cTabFolderEvent.height = rectangle.height;
                    cTabFolderEvent.doit = true;
                    for (CTabFolder2Listener cTabFolder2Listener : this.folderListeners) {
                        cTabFolder2Listener.showList(cTabFolderEvent);
                    }
                    if (cTabFolderEvent.doit && !this.isDisposed()) {
                        this.showList(rectangle);
                    }
                }
            }
        }
        this.setSelection(n2, true);
    }

    void onPaint(Event event) {
        if (this.inDispose) {
            return;
        }
        Font font = this.getFont();
        if (this.oldFont == null || !this.oldFont.equals(font)) {
            this.oldFont = font;
            if (this == false) {
                this.updateItems();
                this.redraw();
                return;
            }
        }
        GC gC = event.gc;
        Font font2 = gC.getFont();
        Color color = gC.getBackground();
        Color color2 = gC.getForeground();
        Point point = this.getSize();
        Rectangle rectangle = new Rectangle(0, 0, point.x, point.y);
        this.renderer.draw(-1, 24, rectangle, gC);
        gC.setFont(font2);
        gC.setForeground(color2);
        gC.setBackground(color);
        this.renderer.draw(-2, 24, rectangle, gC);
        gC.setFont(font2);
        gC.setForeground(color2);
        gC.setBackground(color);
        if (!this.single) {
            for (int i = 0; i < this.items.length; ++i) {
                Rectangle rectangle2 = this.items[i].getBounds();
                if (i == this.selectedIndex || !event.getBounds().intersects(rectangle2)) continue;
                this.renderer.draw(i, 0x18 | this.items[i].state, rectangle2, gC);
            }
        }
        gC.setFont(font2);
        gC.setForeground(color2);
        gC.setBackground(color);
        if (this.selectedIndex != -1) {
            this.renderer.draw(this.selectedIndex, this.items[this.selectedIndex].state | 8 | 0x10, this.items[this.selectedIndex].getBounds(), gC);
        }
        gC.setFont(font2);
        gC.setForeground(color2);
        gC.setBackground(color);
        if (this.hoverTb) {
            Rectangle rectangle3 = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
            int n = this.getSize().x - (rectangle3.width + rectangle3.x);
            this.hoverRect = new Rectangle(n - 16 - 3, 2, 16, this.getTabHeight() - 2);
            gC.setForeground(gC.getDevice().getSystemColor(18));
            n = this.hoverRect.x;
            int n2 = this.hoverRect.y;
            gC.setBackground(gC.getDevice().getSystemColor(1));
            gC.fillRectangle(n + this.hoverRect.width - 6, n2, 5, 5);
            gC.drawRectangle(n + this.hoverRect.width - 6, n2, 5, 5);
            gC.drawLine(n + this.hoverRect.width - 6, n2 + 2, n + this.hoverRect.width - 6 + 5, n2 + 2);
            gC.fillRectangle(n, n2, 5, 2);
            gC.drawRectangle(n, n2, 5, 2);
        }
        gC.setFont(font2);
        gC.setForeground(color2);
        gC.setBackground(color);
    }

    void onResize(Event event) {
        if (this.inDispose) {
            return;
        }
        if (this.ignoreResize) {
            return;
        }
        if (this.updateItems()) {
            this.redrawTabs();
        }
        Point point = this.getSize();
        if (this.oldSize == null) {
            this.redraw();
        } else if (this.onBottom && point.y != this.oldSize.y) {
            this.redraw();
        } else {
            int n = Math.min(point.x, this.oldSize.x);
            Rectangle rectangle = this.renderer.computeTrim(-1, 0, 0, 0, 0, 0);
            if (point.x != this.oldSize.x) {
                n -= rectangle.width + rectangle.x - this.marginWidth + 2;
            }
            if (!this.simple) {
                n -= 5;
            }
            int n2 = Math.min(point.y, this.oldSize.y);
            if (point.y != this.oldSize.y) {
                n2 -= rectangle.height + rectangle.y - this.marginHeight;
            }
            int n3 = Math.max(point.x, this.oldSize.x);
            int n4 = Math.max(point.y, this.oldSize.y);
            this.redraw(0, n2, n3, n4 - n2, false);
            this.redraw(n, 0, n3 - n, n4, false);
            if (this.hoverTb) {
                this.redraw(this.hoverRect.x, this.hoverRect.y, this.hoverRect.width, this.hoverRect.height, false);
            }
        }
        this.oldSize = point;
    }

    void onSelection(Event event) {
        if (this.hovering) {
            this.hovering = false;
            this.updateItems();
        }
        if (event.widget == this.maxItem) {
            CTabFolderEvent cTabFolderEvent = new CTabFolderEvent(this);
            cTabFolderEvent.widget = this;
            cTabFolderEvent.time = event.time;
            for (CTabFolder2Listener cTabFolder2Listener : this.folderListeners) {
                if (this.maximized) {
                    cTabFolder2Listener.restore(cTabFolderEvent);
                    continue;
                }
                cTabFolder2Listener.maximize(cTabFolderEvent);
            }
        } else if (event.widget == this.minItem) {
            CTabFolderEvent cTabFolderEvent = new CTabFolderEvent(this);
            cTabFolderEvent.widget = this;
            cTabFolderEvent.time = event.time;
            for (CTabFolder2Listener cTabFolder2Listener : this.folderListeners) {
                if (this.minimized) {
                    cTabFolder2Listener.restore(cTabFolderEvent);
                    continue;
                }
                cTabFolder2Listener.minimize(cTabFolderEvent);
            }
        } else if (event.widget == this.chevronItem) {
            Rectangle rectangle = this.chevronItem.getBounds();
            rectangle = event.display.map((Control)this.chevronTb, (Control)this, rectangle);
            CTabFolderEvent cTabFolderEvent = new CTabFolderEvent(this);
            cTabFolderEvent.widget = this;
            cTabFolderEvent.time = event.time;
            cTabFolderEvent.x = rectangle.x;
            cTabFolderEvent.y = rectangle.y;
            cTabFolderEvent.width = rectangle.width;
            cTabFolderEvent.height = rectangle.height;
            cTabFolderEvent.doit = true;
            for (CTabFolder2Listener cTabFolder2Listener : this.folderListeners) {
                cTabFolder2Listener.showList(cTabFolderEvent);
            }
            if (cTabFolderEvent.doit && !this.isDisposed()) {
                this.showList(rectangle);
            }
        }
    }

    void onTraverse(Event event) {
        if (this.ignoreTraverse) {
            return;
        }
        this.runUpdate();
        switch (event.detail) {
            case 2: 
            case 4: 
            case 8: 
            case 16: {
                Control control = this.getDisplay().getFocusControl();
                if (control != this) break;
                event.doit = true;
                break;
            }
            case 128: {
                event.doit = this.onMnemonic(event, false);
                break;
            }
            case 256: 
            case 512: {
                event.doit = this.items.length > 0;
            }
        }
        this.ignoreTraverse = true;
        this.notifyListeners(31, event);
        this.ignoreTraverse = false;
        event.type = 0;
        if (this.isDisposed()) {
            return;
        }
        if (!event.doit) {
            return;
        }
        switch (event.detail) {
            case 128: {
                this.onMnemonic(event, true);
                event.detail = 0;
                break;
            }
            case 256: 
            case 512: {
                this.onPageTraversal(event);
                event.detail = 0;
            }
        }
    }

    void redrawTabs() {
        Point point = this.getSize();
        Rectangle rectangle = this.renderer.computeTrim(-1, 0, 0, 0, 0, 0);
        if (this.onBottom) {
            int n = rectangle.height + rectangle.y - this.marginHeight;
            this.redraw(0, point.y - n - 1, point.x, n + 1, false);
        } else {
            this.redraw(0, 0, point.x, -rectangle.y - this.marginHeight + 1, false);
        }
    }

    public void removeCTabFolder2Listener(CTabFolder2Listener cTabFolder2Listener) {
        this.checkWidget();
        if (cTabFolder2Listener == null) {
            SWT.error(4);
        }
        if (this.folderListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.folderListeners.length; ++i) {
            if (cTabFolder2Listener != this.folderListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.folderListeners.length == 1) {
            this.folderListeners = new CTabFolder2Listener[0];
            return;
        }
        CTabFolder2Listener[] cTabFolder2ListenerArray = new CTabFolder2Listener[this.folderListeners.length - 1];
        System.arraycopy(this.folderListeners, 0, cTabFolder2ListenerArray, 0, n);
        System.arraycopy(this.folderListeners, n + 1, cTabFolder2ListenerArray, n, this.folderListeners.length - n - 1);
        this.folderListeners = cTabFolder2ListenerArray;
    }

    @Deprecated
    public void removeCTabFolderListener(CTabFolderListener cTabFolderListener) {
        this.checkWidget();
        if (cTabFolderListener == null) {
            SWT.error(4);
        }
        if (this.tabListeners.length == 0) {
            return;
        }
        int n = -1;
        for (int i = 0; i < this.tabListeners.length; ++i) {
            if (cTabFolderListener != this.tabListeners[i]) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (this.tabListeners.length == 1) {
            this.tabListeners = new CTabFolderListener[0];
            return;
        }
        CTabFolderListener[] cTabFolderListenerArray = new CTabFolderListener[this.tabListeners.length - 1];
        System.arraycopy(this.tabListeners, 0, cTabFolderListenerArray, 0, n);
        System.arraycopy(this.tabListeners, n + 1, cTabFolderListenerArray, n, this.tabListeners.length - n - 1);
        this.tabListeners = cTabFolderListenerArray;
    }

    public void removeSelectionListener(SelectionListener selectionListener) {
        this.checkWidget();
        if (selectionListener == null) {
            SWT.error(4);
        }
        this.removeListener(13, selectionListener);
        this.removeListener(14, selectionListener);
    }

    @Override
    public void reskin(int n) {
        super.reskin(n);
        for (CTabItem cTabItem : this.items) {
            cTabItem.reskin(n);
        }
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        this.renderer.createAntialiasColors();
        this.updateBkImages(true);
        this.redraw();
    }

    public void setBackground(Color[] colorArray, int[] nArray) {
        this.setBackground(colorArray, nArray, false);
    }

    public void setBackground(Color[] colorArray, int[] nArray, boolean bl) {
        int n;
        this.checkWidget();
        if (colorArray != null) {
            if (nArray == null || nArray.length != colorArray.length - 1) {
                SWT.error(5);
            }
            for (n = 0; n < nArray.length; ++n) {
                if (nArray[n] < 0 || nArray[n] > 100) {
                    SWT.error(5);
                }
                if (n <= 0 || nArray[n] >= nArray[n - 1]) continue;
                SWT.error(5);
            }
            if (this.getDisplay().getDepth() < 15) {
                colorArray = new Color[]{colorArray[colorArray.length - 1]};
                nArray = new int[]{};
            }
        }
        if (this.gradientColors != null && colorArray != null && this.gradientColors.length == colorArray.length) {
            int n2;
            n = 0;
            for (n2 = 0; n2 < this.gradientColors.length && (n = this.gradientColors[n2] == null ? (colorArray[n2] == null ? 1 : 0) : (int)(this.gradientColors[n2].equals(colorArray[n2]) ? 1 : 0)) != 0; ++n2) {
            }
            if (n != 0) {
                for (n2 = 0; n2 < this.gradientPercents.length; ++n2) {
                    int n3 = n = this.gradientPercents[n2] == nArray[n2] ? 1 : 0;
                    if (n == 0) break;
                }
            }
            if (n != 0 && this.gradientVertical == bl) {
                return;
            }
        }
        if (colorArray == null) {
            this.gradientColors = null;
            this.gradientPercents = null;
            this.gradientVertical = false;
            this.setBackground(null);
        } else {
            this.gradientColors = new Color[colorArray.length];
            for (n = 0; n < colorArray.length; ++n) {
                this.gradientColors[n] = colorArray[n];
            }
            this.gradientPercents = new int[nArray.length];
            for (n = 0; n < nArray.length; ++n) {
                this.gradientPercents[n] = nArray[n];
            }
            this.gradientVertical = bl;
            this.setBackground(this.gradientColors[this.gradientColors.length - 1]);
        }
        this.redraw();
    }

    @Override
    public void setBackgroundImage(Image image) {
        super.setBackgroundImage(image);
        this.renderer.createAntialiasColors();
        this.redraw();
    }

    public void setBorderVisible(boolean bl) {
        this.checkWidget();
        if (this.borderVisible == bl) {
            return;
        }
        this.borderVisible = bl;
        this.updateFolder(2);
    }

    void updateButtons() {
        Display display = this.getDisplay();
        if (this.showMax) {
            if (this.minMaxTb == null) {
                this.minMaxTb = new ToolBar(this, 0x800000);
                this.initAccessibleMinMaxTb();
                this.addTabControl(this.minMaxTb, 131072, 0, false);
            }
            if (this.maxItem == null) {
                this.maxItem = new ToolItem(this.minMaxTb, 8);
                if (this.maxImage == null) {
                    this.maxImage = this.createButtonImage(display, -5);
                }
                this.maxItem.setImage(this.maxImage);
                this.maxItem.setToolTipText(this.maximized ? SWT.getMessage("SWT_Restore") : SWT.getMessage("SWT_Maximize"));
                this.maxItem.addListener(13, this.listener);
            }
        } else if (this.maxItem != null) {
            this.maxItem.dispose();
            this.maxItem = null;
        }
        if (this.showMin) {
            if (this.minMaxTb == null) {
                this.minMaxTb = new ToolBar(this, 0x800000);
                this.initAccessibleMinMaxTb();
                this.addTabControl(this.minMaxTb, 131072, 0, false);
            }
            if (this.minItem == null) {
                this.minItem = new ToolItem(this.minMaxTb, 8, 0);
                if (this.minImage == null) {
                    this.minImage = this.createButtonImage(display, -6);
                }
                this.minItem.setImage(this.minImage);
                this.minItem.setToolTipText(this.minimized ? SWT.getMessage("SWT_Restore") : SWT.getMessage("SWT_Minimize"));
                this.minItem.addListener(13, this.listener);
            }
        } else if (this.minItem != null) {
            this.minItem.dispose();
            this.minItem = null;
        }
        if (this.minMaxTb != null && this.minMaxTb.getItemCount() == 0) {
            this.removeTabControl(this.minMaxTb, false);
            this.minMaxTb.dispose();
            this.minMaxTb = null;
        }
    }

    void setButtonBounds() {
        int n;
        int n2;
        int n3;
        if (this.showChevron) {
            this.updateChevronImage(false);
        }
        Point point = this.getSize();
        boolean[][] blArray = new boolean[1][0];
        Rectangle[] rectangleArray = this.computeControlBounds(point, blArray);
        if (this.fixedTabHeight != -1) {
            n3 = this.fixedTabHeight;
            if (!this.hovering) {
                this.hoverTb = false;
                Rectangle rectangle = this.getBounds();
                for (n2 = 0; n2 < rectangleArray.length; ++n2) {
                    if (blArray[0][n2] || rectangleArray[n2].height <= n3) continue;
                    this.hoverTb = true;
                    break;
                }
                if (this.hoverTb) {
                    for (n2 = 0; n2 < rectangleArray.length; ++n2) {
                        if (blArray[0][n2] || rectangleArray[n2].height <= n3) continue;
                        rectangleArray[n2].x = rectangle.width + 20;
                    }
                }
            }
        }
        n3 = 0;
        for (n = 0; n < rectangleArray.length; ++n) {
            if (blArray[0][n]) continue;
            n3 = Math.max(rectangleArray[n].height, n3);
        }
        n = 0;
        this.ignoreResize = true;
        for (n2 = 0; n2 < this.controls.length; ++n2) {
            if (!this.controls[n2].isDisposed()) {
                if (blArray[0][n2]) {
                    this.controls[n2].setBounds(rectangleArray[n2]);
                } else {
                    this.controls[n2].moveAbove(null);
                    this.controls[n2].setBounds(rectangleArray[n2].x, rectangleArray[n2].y, rectangleArray[n2].width, n3);
                }
            }
            if (n != 0 || rectangleArray[n2].equals(this.controlRects[n2])) continue;
            n = 1;
        }
        this.ignoreResize = false;
        this.controlRects = rectangleArray;
        if (n != 0 || this.hovering) {
            this.updateBkImages(false);
        }
    }

    int getChevronCount() {
        int n;
        int n2 = this.items.length;
        if (this.single) {
            n = this.selectedIndex == -1 ? n2 : n2 - 1;
        } else {
            int n3;
            for (n3 = 0; n3 < this.priority.length && this.items[this.priority[n3]].showing; ++n3) {
            }
            n = n2 - n3;
        }
        return n;
    }

    private void updateChevronImage(boolean bl) {
        if (bl && this.chevronImage == null) {
            return;
        }
        int n = this.getChevronCount();
        if (!bl && this.chevronCount == n) {
            return;
        }
        if (this.chevronImage != null) {
            this.chevronImage.dispose();
        }
        this.chevronImage = this.createButtonImage(this.getDisplay(), -7);
        this.chevronItem.setImage(this.chevronImage);
        this.chevronCount = n;
    }

    @Override
    public boolean setFocus() {
        CTabItem cTabItem;
        this.checkWidget();
        Control control = this.getDisplay().getFocusControl();
        boolean bl = this.isAncestor(control);
        if (bl && (cTabItem = this.getSelection()) != null && cTabItem.setFocus()) {
            return true;
        }
        return super.setFocus();
    }

    boolean isAncestor(Control control) {
        while (control != null && control != this && !(control instanceof Shell)) {
            control = control.getParent();
        }
        return control == this;
    }

    @Override
    public void setFont(Font font) {
        this.checkWidget();
        if (font != null && font.equals(this.getFont())) {
            return;
        }
        super.setFont(font);
        this.oldFont = this.getFont();
        this.renderer.resetChevronFont();
        this.updateChevronImage(true);
        this.updateFolder(2);
    }

    @Override
    public void setForeground(Color color) {
        super.setForeground(color);
        this.updateChevronImage(true);
        this.redraw();
    }

    public void setInsertMark(CTabItem cTabItem, boolean bl) {
        this.checkWidget();
    }

    public void setInsertMark(int n, boolean bl) {
        this.checkWidget();
        if (n < -1 || n >= this.getItemCount()) {
            SWT.error(5);
        }
    }

    boolean setItemLocation(GC gC) {
        boolean bl = false;
        if (this.items.length == 0) {
            return false;
        }
        Rectangle rectangle = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
        int n = rectangle.height + rectangle.y;
        int n2 = -rectangle.y;
        Point point = this.getSize();
        int n3 = this.onBottom ? Math.max(n, point.y - n - this.tabHeight) : n2;
        Point point2 = this.renderer.computeSize(-8, 0, gC, -1, -1);
        int n4 = this.getLeftItemEdge(gC, -3);
        if (this.single) {
            int n5 = this.getDisplay().getBounds().width + 10;
            for (int i = 0; i < this.items.length; ++i) {
                CTabItem cTabItem = this.items[i];
                if (i == this.selectedIndex) {
                    this.firstIndex = this.selectedIndex;
                    int n6 = cTabItem.x;
                    int n7 = cTabItem.y;
                    cTabItem.x = n4;
                    cTabItem.y = n3;
                    cTabItem.showing = true;
                    if (this.showClose || cTabItem.showClose) {
                        cTabItem.closeRect.x = n4 - this.renderer.computeTrim((int)i, (int)0, (int)0, (int)0, (int)0, (int)0).x;
                        int n8 = cTabItem.closeRect.y = this.onBottom ? point.y - n - this.tabHeight + (this.tabHeight - point2.y) / 2 : n2 + (this.tabHeight - point2.y) / 2;
                    }
                    if (cTabItem.x == n6 && cTabItem.y == n7) continue;
                    bl = true;
                    continue;
                }
                cTabItem.x = n5;
                cTabItem.showing = false;
            }
        } else {
            int n9;
            int n10 = this.getRightItemEdge(gC);
            int n11 = n10 - n4;
            int n12 = 0;
            for (n9 = 0; n9 < this.priority.length; ++n9) {
                CTabItem cTabItem = this.items[this.priority[n9]];
                cTabItem.showing = n9 == 0 || cTabItem.width > 0 && (n12 += cTabItem.width) <= n11;
            }
            n9 = this.getLeftItemEdge(gC, -2);
            int n13 = this.getDisplay().getBounds().width + 10;
            this.firstIndex = this.items.length - 1;
            for (int i = 0; i < this.items.length; ++i) {
                CTabItem cTabItem = this.items[i];
                if (!cTabItem.showing) {
                    if (cTabItem.x != n13) {
                        bl = true;
                    }
                    cTabItem.x = n13;
                    continue;
                }
                this.firstIndex = Math.min(this.firstIndex, i);
                if (cTabItem.x != n9 || cTabItem.y != n3) {
                    bl = true;
                }
                cTabItem.x = n9;
                cTabItem.y = n3;
                int n14 = 0;
                if (i == this.selectedIndex) {
                    n14 |= 2;
                }
                Rectangle rectangle2 = this.renderer.computeTrim(i, n14, 0, 0, 0, 0);
                cTabItem.closeRect.x = cTabItem.x + cTabItem.width - (rectangle2.width + rectangle2.x) - point2.x;
                cTabItem.closeRect.y = this.onBottom ? point.y - n - this.tabHeight + (this.tabHeight - point2.y) / 2 : n2 + (this.tabHeight - point2.y) / 2;
                n9 += cTabItem.width;
                if (this.simple || i != this.selectedIndex) continue;
                n9 -= this.renderer.curveIndent;
            }
        }
        return bl;
    }

    void setItemOrder(int[] nArray) {
        this.checkWidget();
        if (nArray == null) {
            SWT.error(4);
        }
        if (nArray.length != this.items.length) {
            SWT.error(5);
        }
        int n = -1;
        boolean[] blArray = new boolean[this.items.length];
        CTabItem[] cTabItemArray = new CTabItem[this.items.length];
        for (int i = 0; i < nArray.length; ++i) {
            int n2 = nArray[i];
            if (0 > n2 || n2 >= this.items.length) {
                SWT.error(5);
            }
            if (blArray[n2]) {
                SWT.error(5);
            }
            blArray[n2] = true;
            if (n2 == this.selectedIndex) {
                n = i;
            }
            cTabItemArray[i] = this.items[n2];
        }
        this.items = cTabItemArray;
        this.selectedIndex = n;
        this.updateFolder(2);
    }

    boolean setItemSize(GC gC) {
        int n;
        int n2;
        int n3;
        int n4;
        boolean bl = false;
        if (this.isDisposed()) {
            return bl;
        }
        Point point = this.getSize();
        if (point.x <= 0 || point.y <= 0) {
            return bl;
        }
        ToolBar toolBar = this.getChevron();
        if (toolBar != null) {
            toolBar.setVisible(false);
        }
        this.showChevron = false;
        if (this.single) {
            boolean bl2 = this.showChevron = this.chevronVisible && this.items.length > 1;
            if (this.showChevron) {
                toolBar.setVisible(true);
            }
            if (this.selectedIndex != -1) {
                CTabItem cTabItem = this.items[this.selectedIndex];
                int n5 = this.renderer.computeSize((int)this.selectedIndex, (int)2, (GC)gC, (int)-1, (int)-1).x;
                n5 = Math.min(n5, this.getRightItemEdge(gC) - this.getLeftItemEdge(gC, -3));
                if (cTabItem.height != this.tabHeight || cTabItem.width != n5) {
                    bl = true;
                    cTabItem.shortenedText = null;
                    cTabItem.shortenedTextWidth = 0;
                    cTabItem.height = this.tabHeight;
                    cTabItem.width = n5;
                    Rectangle rectangle = cTabItem.closeRect;
                    Rectangle rectangle2 = cTabItem.closeRect;
                    boolean bl3 = false;
                    rectangle2.height = 0;
                    rectangle.width = 0;
                    if (this.showClose || cTabItem.showClose) {
                        Point point2 = this.renderer.computeSize(-8, 2, gC, -1, -1);
                        cTabItem.closeRect.width = point2.x;
                        cTabItem.closeRect.height = point2.y;
                    }
                }
            }
            return bl;
        }
        if (this.items.length == 0) {
            return bl;
        }
        int n6 = Math.max(0, this.getRightItemEdge(gC) - this.getLeftItemEdge(gC, -3));
        int n7 = 0;
        int[] nArray = new int[this.items.length];
        int[] nArray2 = this.priority;
        int n8 = nArray2.length;
        for (int i = 0; i < n8; ++i) {
            n3 = n4 = nArray2[i];
            n2 = 0x1000000;
            if (n4 == this.selectedIndex) {
                n2 |= 2;
            }
            nArray[n4] = this.renderer.computeSize((int)n4, (int)n2, (GC)gC, (int)-1, (int)-1).x;
            if ((n7 += nArray[n4]) > n6) break;
        }
        if (n7 > n6) {
            boolean bl4 = this.showChevron = this.chevronVisible && this.items.length > 1;
            if (this.showChevron) {
                n6 -= toolBar.computeSize((int)-1, (int)-1).x;
                toolBar.setVisible(true);
            }
            nArray2 = nArray;
            int n9 = n8 = this.selectedIndex != -1 ? this.selectedIndex : 0;
            if (n6 < nArray2[n8]) {
                nArray2[n8] = Math.max(0, n6);
            }
        } else {
            n8 = 0;
            int[] nArray3 = new int[this.items.length];
            for (n4 = 0; n4 < this.items.length; ++n4) {
                n3 = 0;
                if (n4 == this.selectedIndex) {
                    n3 |= 2;
                }
                nArray3[n4] = this.renderer.computeSize((int)n4, (int)n3, (GC)gC, (int)-1, (int)-1).x;
                n8 += nArray3[n4];
            }
            if (n8 <= n6) {
                nArray2 = nArray3;
            } else {
                n4 = (n6 - n7) / this.items.length;
                while (true) {
                    n3 = 0;
                    n2 = 0;
                    for (n = 0; n < this.items.length; ++n) {
                        if (nArray3[n] > nArray[n] + n4) {
                            n2 += nArray[n] + n4;
                            ++n3;
                            continue;
                        }
                        n2 += nArray3[n];
                    }
                    if (n2 >= n6) {
                        --n4;
                        break;
                    }
                    if (n3 == 0 || n6 - n2 < n3) break;
                    ++n4;
                }
                nArray2 = new int[this.items.length];
                for (n3 = 0; n3 < this.items.length; ++n3) {
                    nArray2[n3] = Math.min(nArray3[n3], nArray[n3] + n4);
                }
            }
        }
        for (n8 = 0; n8 < this.items.length; ++n8) {
            CTabItem cTabItem = this.items[n8];
            n4 = nArray2[n8];
            if (cTabItem.height == this.tabHeight && cTabItem.width == n4) continue;
            bl = true;
            cTabItem.shortenedText = null;
            cTabItem.shortenedTextWidth = 0;
            cTabItem.height = this.tabHeight;
            cTabItem.width = n4;
            Rectangle rectangle = cTabItem.closeRect;
            Rectangle rectangle3 = cTabItem.closeRect;
            n = 0;
            rectangle3.height = 0;
            rectangle.width = 0;
            if (!this.showClose && !cTabItem.showClose || n8 != this.selectedIndex && !this.showUnselectedClose) continue;
            Point point3 = this.renderer.computeSize(-8, 0, gC, -1, -1);
            cTabItem.closeRect.width = point3.x;
            cTabItem.closeRect.height = point3.y;
        }
        return bl;
    }

    public void setMaximizeVisible(boolean bl) {
        this.checkWidget();
        if (this.showMax == bl) {
            return;
        }
        this.showMax = bl;
        this.updateFolder(10);
    }

    @Override
    public void setLayout(Layout layout) {
        this.checkWidget();
    }

    public void setMaximized(boolean bl) {
        this.checkWidget();
        if (this.maximized == bl) {
            return;
        }
        if (bl && this.minimized) {
            this.setMinimized(false);
        }
        this.maximized = bl;
        if (this.minMaxTb != null && this.maxItem != null) {
            if (this.maxImage != null) {
                this.maxImage.dispose();
            }
            this.maxImage = this.createButtonImage(this.getDisplay(), -5);
            this.maxItem.setImage(this.maxImage);
            this.maxItem.setToolTipText(this.maximized ? SWT.getMessage("SWT_Restore") : SWT.getMessage("SWT_Maximize"));
        }
    }

    public void setMinimizeVisible(boolean bl) {
        this.checkWidget();
        if (this.showMin == bl) {
            return;
        }
        this.showMin = bl;
        this.updateFolder(10);
    }

    public void setMinimized(boolean bl) {
        this.checkWidget();
        if (this.minimized == bl) {
            return;
        }
        if (bl && this.maximized) {
            this.setMaximized(false);
        }
        this.minimized = bl;
        if (this.minMaxTb != null && this.minItem != null) {
            if (this.minImage != null) {
                this.minImage.dispose();
            }
            this.minImage = this.createButtonImage(this.getDisplay(), -6);
            this.minItem.setImage(this.minImage);
            this.minItem.setToolTipText(this.minimized ? SWT.getMessage("SWT_Restore") : SWT.getMessage("SWT_Minimize"));
        }
    }

    public void setMinimumCharacters(int n) {
        this.checkWidget();
        if (n < 0) {
            SWT.error(6);
        }
        if (this.minChars == n) {
            return;
        }
        this.minChars = n;
        this.updateFolder(4);
    }

    public void setMRUVisible(boolean bl) {
        this.checkWidget();
        if (this.mru == bl) {
            return;
        }
        this.mru = bl;
        if (!this.mru) {
            if (this.firstIndex == -1) {
                return;
            }
            int n = this.firstIndex;
            int n2 = 0;
            int n3 = this.firstIndex;
            while (n3 < this.items.length) {
                this.priority[n2++] = n3++;
            }
            n3 = 0;
            while (n3 < n) {
                this.priority[n2++] = n3++;
            }
            this.updateFolder(4);
        }
    }

    public void setRenderer(CTabFolderRenderer cTabFolderRenderer) {
        this.checkWidget();
        if (this.renderer == cTabFolderRenderer || this.useDefaultRenderer && cTabFolderRenderer == null) {
            return;
        }
        if (this.renderer != null) {
            this.renderer.dispose();
        }
        boolean bl = this.useDefaultRenderer = cTabFolderRenderer == null;
        if (this.useDefaultRenderer) {
            cTabFolderRenderer = new CTabFolderRenderer(this);
        }
        this.renderer = cTabFolderRenderer;
        this.updateFolder(2);
    }

    public void setSelection(CTabItem cTabItem) {
        this.checkWidget();
        if (cTabItem == null) {
            SWT.error(4);
        }
        int n = this.indexOf(cTabItem);
        this.setSelection(n);
    }

    public void setSelection(int n) {
        CTabItem cTabItem;
        this.checkWidget();
        if (n < 0 || n >= this.items.length) {
            return;
        }
        CTabItem cTabItem2 = this.items[n];
        if (this.selectedIndex == n) {
            this.showItem(cTabItem2);
            return;
        }
        int n2 = this.selectedIndex;
        this.selectedIndex = n;
        if (n2 != -1) {
            this.items[n2].closeImageState = 8;
            cTabItem = this.items[n2];
            cTabItem.state &= 0xFFFFFFFD;
        }
        cTabItem2.closeImageState = 0;
        cTabItem2.showing = false;
        cTabItem = cTabItem2;
        cTabItem.state |= 2;
        Control control = cTabItem2.control;
        Control control2 = null;
        if (n2 != -1) {
            control2 = this.items[n2].control;
        }
        if (control != control2) {
            if (control != null && !control.isDisposed()) {
                control.setBounds(this.getClientArea());
                control.setVisible(true);
            }
            if (control2 != null && !control2.isDisposed()) {
                control2.setVisible(false);
            }
        }
        this.showItem(cTabItem2);
        this.redraw();
    }

    void setSelection(int n, boolean bl) {
        int n2 = this.selectedIndex;
        this.setSelection(n);
        if (bl && this.selectedIndex != n2 && this.selectedIndex != -1) {
            Event event = new Event();
            event.item = this.getItem(this.selectedIndex);
            this.notifyListeners(13, event);
        }
    }

    public void setSelectionBackground(Color color) {
        if (this.inDispose) {
            return;
        }
        this.checkWidget();
        this.setSelectionHighlightGradientColor(null);
        if (this.selectionBackground == color) {
            return;
        }
        if (color == null) {
            color = this.getDisplay().getSystemColor(25);
        }
        this.selectionBackground = color;
        this.renderer.createAntialiasColors();
        if (this.selectedIndex > -1) {
            this.redraw();
        }
    }

    public void setSelectionBackground(Color[] colorArray, int[] nArray) {
        this.setSelectionBackground(colorArray, nArray, false);
    }

    public void setSelectionBackground(Color[] colorArray, int[] nArray, boolean bl) {
        int n;
        int n2;
        this.checkWidget();
        Color color = null;
        if (colorArray != null) {
            if (nArray == null || nArray.length != colorArray.length - 1 && nArray.length != colorArray.length - 2) {
                SWT.error(5);
            }
            for (n2 = 0; n2 < nArray.length; ++n2) {
                if (nArray[n2] < 0 || nArray[n2] > 100) {
                    SWT.error(5);
                }
                if (n2 <= 0 || nArray[n2] >= nArray[n2 - 1]) continue;
                SWT.error(5);
            }
            if (nArray.length == colorArray.length - 2) {
                color = colorArray[colorArray.length - 1];
                n = colorArray.length - 1;
            } else {
                n = colorArray.length;
            }
            if (this.getDisplay().getDepth() < 15) {
                colorArray = new Color[]{colorArray[n - 1]};
                n = colorArray.length;
                nArray = new int[]{};
            }
        } else {
            n = 0;
        }
        if (this.selectionBgImage == null) {
            if (this.selectionGradientColors != null && colorArray != null && this.selectionGradientColors.length == n) {
                int n3;
                n2 = 0;
                for (n3 = 0; n3 < this.selectionGradientColors.length && (n2 = this.selectionGradientColors[n3] == null ? (colorArray[n3] == null ? 1 : 0) : (int)(this.selectionGradientColors[n3].equals(colorArray[n3]) ? 1 : 0)) != 0; ++n3) {
                }
                if (n2 != 0) {
                    for (n3 = 0; n3 < this.selectionGradientPercents.length; ++n3) {
                        int n4 = n2 = this.selectionGradientPercents[n3] == nArray[n3] ? 1 : 0;
                        if (n2 == 0) break;
                    }
                }
                if (n2 != 0 && this.selectionGradientVertical == bl) {
                    return;
                }
            }
        } else {
            this.selectionBgImage = null;
        }
        if (colorArray == null) {
            this.selectionGradientColors = null;
            this.selectionGradientPercents = null;
            this.selectionGradientVertical = false;
            this.setSelectionBackground((Color)null);
            this.setSelectionHighlightGradientColor(null);
        } else {
            this.selectionGradientColors = new Color[n];
            for (n2 = 0; n2 < n; ++n2) {
                this.selectionGradientColors[n2] = colorArray[n2];
            }
            this.selectionGradientPercents = new int[nArray.length];
            for (n2 = 0; n2 < nArray.length; ++n2) {
                this.selectionGradientPercents[n2] = nArray[n2];
            }
            this.selectionGradientVertical = bl;
            this.setSelectionBackground(this.selectionGradientColors[this.selectionGradientColors.length - 1]);
            this.setSelectionHighlightGradientColor(color);
        }
        if (this.selectedIndex > -1) {
            this.redraw();
        }
    }

    void setSelectionHighlightGradientColor(Color color) {
        if (this.inDispose) {
            return;
        }
        this.renderer.setSelectionHighlightGradientColor(color);
    }

    public void setSelectionBackground(Image image) {
        this.checkWidget();
        this.setSelectionHighlightGradientColor(null);
        if (image == this.selectionBgImage) {
            return;
        }
        if (image != null) {
            this.selectionGradientColors = null;
            this.selectionGradientPercents = null;
            this.renderer.disposeSelectionHighlightGradientColors();
        }
        this.selectionBgImage = image;
        this.renderer.createAntialiasColors();
        if (this.selectedIndex > -1) {
            this.redraw();
        }
    }

    public void setSelectionForeground(Color color) {
        this.checkWidget();
        if (this.selectionForeground == color) {
            return;
        }
        if (color == null) {
            color = this.getDisplay().getSystemColor(24);
        }
        this.selectionForeground = color;
        if (this.selectedIndex > -1) {
            this.redraw();
        }
    }

    public void setSimple(boolean bl) {
        this.checkWidget();
        if (this.simple != bl) {
            this.simple = bl;
            this.updateFolder(10);
        }
    }

    public void setSingle(boolean bl) {
        this.checkWidget();
        if (this.single != bl) {
            this.single = bl;
            if (!this.single) {
                for (int i = 0; i < this.items.length; ++i) {
                    if (i == this.selectedIndex || this.items[i].closeImageState != 0) continue;
                    this.items[i].closeImageState = 8;
                }
            }
            this.updateFolder(2);
        }
    }

    int getControlY(Point point, Rectangle[] rectangleArray, int n, int n2, int n3) {
        int n4 = this.fixedTabHeight != -1 ? 0 : (this.tabHeight - rectangleArray[n3].height) / 2;
        return this.onBottom ? point.y - n - this.tabHeight + n4 : 1 + n2 + n4;
    }

    public void setTabHeight(int n) {
        this.checkWidget();
        if (n < -1) {
            SWT.error(5);
        }
        this.fixedTabHeight = n;
        this.updateFolder(8);
    }

    public void setTabPosition(int n) {
        this.checkWidget();
        if (n != 128 && n != 1024) {
            SWT.error(5);
        }
        if (this.onBottom != (n == 1024)) {
            this.onBottom = n == 1024;
            this.updateFolder(2);
        }
    }

    public void setTopRight(Control control) {
        this.setTopRight(control, 131072);
    }

    public void setTopRight(Control control, int n) {
        this.checkWidget();
        if (n != 131072 && n != 4 && n != 131136) {
            SWT.error(5);
        }
        if (control != null && (control.isDisposed() || control.getParent() != this)) {
            SWT.error(5);
        }
        if (this.topRight == control && this.topRightAlignment == n) {
            return;
        }
        if (this.topRight != null && !this.topRight.isDisposed()) {
            this.removeTabControl(this.topRight, false);
        }
        this.topRight = control;
        this.topRightAlignment = n;
        n &= 0xFFFDFFFF;
        if (control != null) {
            this.addTabControl(control, 0x20000 | n, -1, false);
        }
        this.updateFolder(10);
    }

    public void setUnselectedCloseVisible(boolean bl) {
        this.checkWidget();
        if (this.showUnselectedClose == bl) {
            return;
        }
        this.showUnselectedClose = bl;
        this.updateFolder(2);
    }

    public void setUnselectedImageVisible(boolean bl) {
        this.checkWidget();
        if (this.showUnselectedImage == bl) {
            return;
        }
        this.showUnselectedImage = bl;
        this.updateFolder(2);
    }

    public void showItem(CTabItem cTabItem) {
        int n;
        this.checkWidget();
        if (cTabItem == null) {
            SWT.error(4);
        }
        if (cTabItem.isDisposed()) {
            SWT.error(5);
        }
        if ((n = this.indexOf(cTabItem)) == -1) {
            SWT.error(5);
        }
        int n2 = -1;
        for (int i = 0; i < this.priority.length; ++i) {
            if (this.priority[i] != n) continue;
            n2 = i;
            break;
        }
        if (this.mru) {
            int[] nArray = new int[this.priority.length];
            System.arraycopy(this.priority, 0, nArray, 1, n2);
            System.arraycopy(this.priority, n2 + 1, nArray, n2 + 1, this.priority.length - n2 - 1);
            nArray[0] = n;
            this.priority = nArray;
        }
        if (cTabItem.showing) {
            return;
        }
        this.updateFolder(4);
    }

    void showList(Rectangle rectangle) {
        if (this.items.length == 0 || !this.showChevron) {
            return;
        }
        if (this.showMenu == null || this.showMenu.isDisposed()) {
            this.showMenu = new Menu(this.getShell(), this.getStyle() & 0x6000000);
        } else {
            for (MenuItem menuItem : this.showMenu.getItems()) {
                menuItem.dispose();
            }
        }
        String string = "CTabFolder_showList_Index";
        for (CTabItem cTabItem : this.items) {
            if (cTabItem.showing) continue;
            MenuItem menuItem = new MenuItem(this.showMenu, 0);
            menuItem.setText(cTabItem.getText().replace("\n", " "));
            menuItem.setImage(cTabItem.getImage());
            menuItem.setData("CTabFolder_showList_Index", cTabItem);
            menuItem.addSelectionListener(new lIIl(this));
        }
        int n = rectangle.x;
        int n2 = rectangle.y + rectangle.height;
        Point point = this.getDisplay().map(this, null, n, n2);
        this.showMenu.setLocation(point.x, point.y);
        this.showMenu.setVisible(true);
    }

    public void showSelection() {
        this.checkWidget();
        if (this.selectedIndex != -1) {
            this.showItem(this.getSelection());
        }
    }

    void _setToolTipText(int n, int n2) {
        String string = this.getToolTipText();
        String string2 = this._getToolTip(n, n2);
        if (string2 == null || !string2.equals(string)) {
            this.setToolTipText(string2);
        }
    }

    boolean updateItems() {
        return this.updateItems(this.selectedIndex);
    }

    boolean updateItems(int n) {
        Object object;
        int n2;
        int n3;
        int n4;
        GC gC = new GC(this);
        if (!this.single && !this.mru && n != -1) {
            n4 = n;
            if (this.priority[0] < n4) {
                int n5;
                int n6;
                n3 = this.getRightItemEdge(gC) - this.getLeftItemEdge(gC, -3);
                n2 = 0;
                object = new int[this.items.length];
                for (n6 = this.priority[0]; n6 <= n; ++n6) {
                    n5 = 0x1000000;
                    if (n6 == this.selectedIndex) {
                        n5 |= 2;
                    }
                    object[n6] = this.renderer.computeSize((int)n6, (int)n5, (GC)gC, (int)-1, (int)-1).x;
                    if ((n2 += object[n6]) > n3) break;
                }
                if (n2 > n3) {
                    n2 = 0;
                    n6 = n;
                    while (n6 >= 0) {
                        n5 = 0x1000000;
                        if (n6 == this.selectedIndex) {
                            n5 |= 2;
                        }
                        if (object[n6] == 0) {
                            object[n6] = this.renderer.computeSize((int)n6, (int)n5, (GC)gC, (int)-1, (int)-1).x;
                        }
                        if ((n2 += object[n6]) <= n3) {
                            n4 = n6--;
                            continue;
                        }
                        break;
                    }
                } else {
                    n4 = this.priority[0];
                    for (n6 = n + 1; n6 < this.items.length; ++n6) {
                        n5 = 0x1000000;
                        if (n6 == this.selectedIndex) {
                            n5 |= 2;
                        }
                        object[n6] = this.renderer.computeSize((int)n6, (int)n5, (GC)gC, (int)-1, (int)-1).x;
                        if ((n2 += object[n6]) >= n3) break;
                    }
                    if (n2 < n3) {
                        n6 = this.priority[0] - 1;
                        while (n6 >= 0) {
                            n5 = 0x1000000;
                            if (n6 == this.selectedIndex) {
                                n5 |= 2;
                            }
                            if (object[n6] == 0) {
                                object[n6] = this.renderer.computeSize((int)n6, (int)n5, (GC)gC, (int)-1, (int)-1).x;
                            }
                            if ((n2 += object[n6]) <= n3) {
                                n4 = n6--;
                                continue;
                            }
                            break;
                        }
                    }
                }
            }
            if (n4 != this.priority[0]) {
                n3 = 0;
                n2 = n4;
                while (n2 < this.items.length) {
                    this.priority[n3++] = n2++;
                }
                n2 = n4 - 1;
                while (n2 >= 0) {
                    this.priority[n3++] = n2--;
                }
            }
        }
        n4 = this.showChevron;
        n3 = this.setItemSize(gC);
        this.updateButtons();
        int n7 = n2 = this.showChevron != n4 ? 1 : 0;
        if (n2 != 0 && this == false) {
            n3 |= this.setItemSize(gC);
        }
        n3 |= this.setItemLocation(gC);
        this.setButtonBounds();
        if ((n3 |= n2) != 0 && this.getToolTipText() != null) {
            object = this.getDisplay().getCursorLocation();
            object = this.toControl((Point)object);
            this._setToolTipText(object.x, object.y);
        }
        gC.dispose();
        return n3 != 0;
    }

    void updateFolder(int n) {
        this.updateFlags |= n;
        if (this.updateRun != null) {
            return;
        }
        this.updateRun = this::lambda$updateFolder$3;
        this.getDisplay().asyncExec(this.updateRun);
    }

    void runUpdate() {
        if (this.updateFlags == 0) {
            return;
        }
        int n = this.updateFlags;
        this.updateFlags = 0;
        Rectangle rectangle = this.getClientArea();
        this.updateButtons();
        this.updateTabHeight(false);
        this.updateItems(this.selectedIndex);
        if ((n & 2) != 0) {
            this.redraw();
        } else if ((n & 4) != 0) {
            this.redrawTabs();
        }
        Rectangle rectangle2 = this.getClientArea();
        if (!rectangle.equals(rectangle2)) {
            this.notifyListeners(11, new Event());
            this.layout();
        }
    }

    void updateBkImages(boolean bl) {
        if (this.controls != null && this.controls.length > 0) {
            if (this.bkImageBounds == null) {
                this.bkImageBounds = new Rectangle[this.controls.length];
            }
            if (this.bkImageBounds.length != this.controls.length) {
                this.bkImageBounds = new Rectangle[this.controls.length];
            }
            for (int i = 0; i < this.controls.length; ++i) {
                Object object;
                boolean bl2;
                Control control = this.controls[i];
                if (control.isDisposed()) continue;
                if (this.hovering) {
                    if (control instanceof Composite) {
                        ((Composite)control).setBackgroundMode(0);
                    }
                    control.setBackgroundImage(null);
                    control.setBackground(this.getBackground());
                    continue;
                }
                if (control instanceof Composite) {
                    ((Composite)control).setBackgroundMode(1);
                }
                Rectangle rectangle = control.getBounds();
                int n = this.getTabHeight();
                int n2 = this.getSize().y;
                boolean bl3 = this.onBottom ? rectangle.y + rectangle.height < n2 - n : (bl2 = rectangle.y > n);
                if (bl2 || this.gradientColors == null) {
                    this.bkImageBounds[i] = null;
                    control.setBackgroundImage(null);
                    control.setBackground(this.getBackground());
                    continue;
                }
                rectangle.width = 10;
                if (!this.onBottom) {
                    rectangle.y = -rectangle.y;
                    object = rectangle;
                    ((Rectangle)object).height -= 2 * rectangle.y - 1;
                } else {
                    object = rectangle;
                    ((Rectangle)object).height += n2 - (rectangle.y + rectangle.height);
                    rectangle.y = -1;
                }
                rectangle.x = 0;
                if (!bl && rectangle.equals(this.bkImageBounds[i])) continue;
                this.bkImageBounds[i] = rectangle;
                if (this.controlBkImages[i] != null) {
                    this.controlBkImages[i].dispose();
                }
                this.controlBkImages[i] = new Image((Device)control.getDisplay(), rectangle);
                object = new GC(this.controlBkImages[i]);
                this.renderer.draw(-4, 0, rectangle, (GC)object);
                ((Resource)object).dispose();
                control.setBackground(null);
                control.setBackgroundImage(this.controlBkImages[i]);
            }
        }
    }

    String _getToolTip(int n, int n2) {
        CTabItem cTabItem = this.getItem(new Point(n, n2));
        if (cTabItem == null) {
            return null;
        }
        if (!cTabItem.showing) {
            return null;
        }
        if ((this.showClose || cTabItem.showClose) && cTabItem.closeRect.contains(n, n2)) {
            return SWT.getMessage("SWT_Close");
        }
        return cTabItem.getToolTipText();
    }

    void addTabControl(Control control, int n) {
        this.checkWidget();
        this.addTabControl(control, n, -1, true);
    }

    void addTabControl(Control control, int n, int n2, boolean bl) {
        switch (n) {
            case 16384: 
            case 131072: 
            case 131076: 
            case 131136: 
            case 131140: {
                break;
            }
            default: {
                SWT.error(5);
            }
        }
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        for (Control rectangleArray2 : this.controls) {
            if (rectangleArray2 != control) continue;
            SWT.error(5);
        }
        int n3 = this.controls.length;
        control.addListener(11, this.listener);
        Control[] controlArray = new Control[n3 + 1];
        System.arraycopy(this.controls, 0, controlArray, 0, n3);
        this.controls = controlArray;
        int[] nArray = new int[n3 + 1];
        System.arraycopy(this.controlAlignments, 0, nArray, 0, n3);
        this.controlAlignments = nArray;
        Rectangle[] rectangleArray = new Rectangle[n3 + 1];
        System.arraycopy(this.controlRects, 0, rectangleArray, 0, n3);
        this.controlRects = rectangleArray;
        Image[] imageArray = new Image[n3 + 1];
        System.arraycopy(this.controlBkImages, 0, imageArray, 0, n3);
        this.controlBkImages = imageArray;
        if (n2 == -1) {
            n2 = n3;
            if (this.chevronTb != null && control != this.chevronTb) {
                --n2;
            }
        }
        System.arraycopy(this.controls, n2, this.controls, n2 + 1, n3 - n2);
        System.arraycopy(this.controlAlignments, n2, this.controlAlignments, n2 + 1, n3 - n2);
        System.arraycopy(this.controlRects, n2, this.controlRects, n2 + 1, n3 - n2);
        System.arraycopy(this.controlBkImages, n2, this.controlBkImages, n2 + 1, n3 - n2);
        this.controls[n2] = control;
        this.controlAlignments[n2] = n;
        this.controlRects[n2] = new Rectangle(0, 0, 0, 0);
        if (bl) {
            this.updateFolder(10);
        }
    }

    void removeTabControl(Control control) {
        this.checkWidget();
        this.removeTabControl(control, true);
    }

    void removeTabControl(Control control, boolean bl) {
        if (control != null && control.getParent() != this) {
            SWT.error(5);
        }
        int n = -1;
        for (int i = 0; i < this.controls.length; ++i) {
            if (this.controls[i] != control) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        if (!control.isDisposed()) {
            control.removeListener(11, this.listener);
            control.setBackground(null);
            control.setBackgroundImage(null);
            if (control instanceof Composite) {
                ((Composite)control).setBackgroundMode(0);
            }
        }
        if (this.controlBkImages[n] != null && !this.controlBkImages[n].isDisposed()) {
            this.controlBkImages[n].dispose();
        }
        if (this.controls.length == 1) {
            this.controls = new Control[0];
            this.controlAlignments = new int[0];
            this.controlRects = new Rectangle[0];
            this.controlBkImages = new Image[0];
        } else {
            Control[] controlArray = new Control[this.controls.length - 1];
            System.arraycopy(this.controls, 0, controlArray, 0, n);
            System.arraycopy(this.controls, n + 1, controlArray, n, this.controls.length - n - 1);
            this.controls = controlArray;
            int[] nArray = new int[this.controls.length];
            System.arraycopy(this.controlAlignments, 0, nArray, 0, n);
            System.arraycopy(this.controlAlignments, n + 1, nArray, n, this.controls.length - n);
            this.controlAlignments = nArray;
            Rectangle[] rectangleArray = new Rectangle[this.controls.length];
            System.arraycopy(this.controlRects, 0, rectangleArray, 0, n);
            System.arraycopy(this.controlRects, n + 1, rectangleArray, n, this.controls.length - n);
            this.controlRects = rectangleArray;
            Image[] imageArray = new Image[this.controls.length];
            System.arraycopy(this.controlBkImages, 0, imageArray, 0, n);
            System.arraycopy(this.controlBkImages, n + 1, imageArray, n, this.controls.length - n);
            this.controlBkImages = imageArray;
        }
        if (bl) {
            this.updateFolder(10);
        }
    }

    int getWrappedHeight(Point point) {
        boolean[][] blArrayArray = new boolean[][]{null};
        Rectangle[] rectangleArray = this.computeControlBounds(point, blArrayArray);
        int n = Integer.MAX_VALUE;
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < rectangleArray.length; ++i) {
            if (!blArrayArray[0][i]) continue;
            n = Math.min(n, rectangleArray[i].y);
            n2 = Math.max(n2, rectangleArray[i].y + rectangleArray[i].height);
            n3 = n2 - n;
        }
        return n3;
    }

    void setChevronVisible(boolean bl) {
        this.checkWidget();
        if (this.chevronVisible == bl) {
            return;
        }
        this.chevronVisible = bl;
        this.updateFolder(10);
    }

    boolean shouldHighlight() {
        return this.highlight && this.highlightEnabled;
    }

    public void setHighlightEnabled(boolean bl) {
        this.checkWidget();
        if (this.highlightEnabled == bl) {
            return;
        }
        this.highlightEnabled = bl;
        this.updateFolder(2);
    }

    public boolean getHighlightEnabled() {
        this.checkWidget();
        return this.highlightEnabled;
    }

    private void lambda$updateFolder$3() {
        this.updateRun = null;
        if (this.isDisposed()) {
            return;
        }
        this.runUpdate();
    }

    private void lambda$initAccessible$2(Accessible accessible, Event event) {
        if (this.selectedIndex == -1) {
            accessible.setFocus(-1);
        } else {
            accessible.setFocus(this.selectedIndex);
        }
    }

    private void lambda$initAccessible$1(Accessible accessible, Event event) {
        if (this.isFocusControl()) {
            if (this.selectedIndex == -1) {
                accessible.setFocus(-1);
            } else {
                accessible.setFocus(this.selectedIndex);
            }
        }
    }

    private void lambda$init$0(Event event) {
        switch (event.type) {
            case 12: {
                this.onDispose(event);
                break;
            }
            case 29: {
                this.onDragDetect(event);
                break;
            }
            case 15: {
                this.onFocus(event);
                break;
            }
            case 16: {
                this.onFocus(event);
                break;
            }
            case 1: {
                this.onKeyDown(event);
                break;
            }
            case 35: {
                this.onMenuDetect(event);
                break;
            }
            case 8: {
                this.onMouseDoubleClick(event);
                break;
            }
            case 3: {
                this.onMouse(event);
                break;
            }
            case 6: {
                this.onMouse(event);
                break;
            }
            case 7: {
                this.onMouse(event);
                break;
            }
            case 32: {
                this.onMouse(event);
                break;
            }
            case 5: {
                this.onMouse(event);
                break;
            }
            case 4: {
                this.onMouse(event);
                break;
            }
            case 9: {
                this.onPaint(event);
                break;
            }
            case 11: {
                this.onResize(event);
                break;
            }
            case 31: {
                this.onTraverse(event);
                break;
            }
            case 13: {
                this.onSelection(event);
                break;
            }
            case 26: {
                this.onActivate(event);
                break;
            }
            case 27: {
                this.onDeactivate(event);
            }
        }
    }
}

