/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCROLLINFO;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Tree;

public class TreeItem
extends Item {
    public long handle;
    Tree parent;
    String[] strings;
    Image[] images;
    Font font;
    Font[] cellFont;
    boolean cached;
    int background = -1;
    int foreground = -1;
    int[] cellBackground;
    int[] cellForeground;

    public TreeItem(Tree tree, int n) {
        this(tree, n, -65536L, -65534L, 0L);
    }

    public TreeItem(Tree tree, int n, int n2) {
        this(tree, n, -65536L, TreeItem.findPrevious(tree, n2), 0L);
    }

    public TreeItem(TreeItem treeItem, int n) {
        this(TreeItem.checkNull((TreeItem)treeItem).parent, n, treeItem.handle, -65534L, 0L);
    }

    public TreeItem(TreeItem treeItem, int n, int n2) {
        this(TreeItem.checkNull((TreeItem)treeItem).parent, n, treeItem.handle, TreeItem.findPrevious(treeItem, n2), 0L);
    }

    TreeItem(Tree tree, int n, long l2, long l3, long l4) {
        super(tree, n);
        this.parent = tree;
        this.parent.createItem(this, l2, l3, l4);
    }

    static TreeItem checkNull(TreeItem treeItem) {
        if (treeItem == null) {
            SWT.error(4);
        }
        return treeItem;
    }

    static long findPrevious(Tree tree, int n) {
        if (tree == null) {
            return 0L;
        }
        if (n < 0) {
            SWT.error(6);
        }
        if (n == 0) {
            return -65535L;
        }
        long l2 = tree.handle;
        long l3 = OS.SendMessage(l2, 4362, 0L, 0L);
        long l4 = tree.findItem(l3, n - 1);
        if (l4 == 0L) {
            SWT.error(6);
        }
        return l4;
    }

    static long findPrevious(TreeItem treeItem, int n) {
        if (treeItem == null) {
            return 0L;
        }
        if (n < 0) {
            SWT.error(6);
        }
        if (n == 0) {
            return -65535L;
        }
        Tree tree = treeItem.parent;
        long l2 = tree.handle;
        long l3 = treeItem.handle;
        long l4 = OS.SendMessage(l2, 4362, 4L, l3);
        long l5 = tree.findItem(l4, n - 1);
        if (l5 == 0L) {
            SWT.error(6);
        }
        return l5;
    }

    @Override
    protected void checkSubclass() {
        if (!this.isValidSubclass()) {
            this.error(43);
        }
    }

    void clear() {
        this.text = "";
        this.image = null;
        this.strings = null;
        this.images = null;
        if ((this.parent.style & 0x20) != 0) {
            long l2 = this.parent.handle;
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 24;
            tVITEM.stateMask = 61440;
            tVITEM.state = 4096;
            tVITEM.hItem = this.handle;
            OS.SendMessage(l2, 4415, 0L, tVITEM);
        }
        int n = -1;
        this.foreground = -1;
        this.background = -1;
        this.font = null;
        Object var2_4 = null;
        this.cellForeground = var2_4;
        this.cellBackground = var2_4;
        this.cellFont = null;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = false;
        }
    }

    public void clear(int n, boolean bl) {
        this.checkWidget();
        long l2 = this.parent.handle;
        long l3 = OS.SendMessage(l2, 4362, 4L, this.handle);
        if (l3 == 0L) {
            this.error(6);
        }
        if ((l3 = this.parent.findItem(l3, n)) == 0L) {
            this.error(6);
        }
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 20;
        this.parent.clear(l3, tVITEM);
        if (bl) {
            l3 = OS.SendMessage(l2, 4362, 4L, l3);
            this.parent.clearAll(l3, tVITEM, bl);
        }
    }

    public void clearAll(boolean bl) {
        this.checkWidget();
        long l2 = this.parent.handle;
        long l3 = OS.SendMessage(l2, 4362, 4L, this.handle);
        if (l3 == 0L) {
            return;
        }
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 20;
        this.parent.clearAll(l3, tVITEM, bl);
    }

    @Override
    void destroyWidget() {
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 20;
        this.parent.releaseItem(this.handle, tVITEM, false);
        this.parent.destroyItem(this, this.handle);
        this.releaseHandle();
    }

    long fontHandle(int n) {
        if (this.cellFont != null && this.cellFont[n] != null) {
            return this.cellFont[n].handle;
        }
        if (this.font != null) {
            return this.font.handle;
        }
        return -1L;
    }

    public Color getBackground() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if (this.background == -1) {
            return this.parent.getBackground();
        }
        return Color.win32_new(this.display, this.background);
    }

    public Color getBackground(int n) {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        int n2 = Math.max(1, this.parent.getColumnCount());
        if (0 > n || n > n2 - 1) {
            return this.getBackground();
        }
        int n3 = this.cellBackground != null ? this.cellBackground[n] : -1;
        return n3 == -1 ? this.getBackground() : Color.win32_new(this.display, n3);
    }

    public Rectangle getBounds() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels());
    }

    Rectangle getBoundsInPixels() {
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        RECT rECT = this.getBounds(0, true, false, false);
        int n = rECT.right - rECT.left;
        int n2 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n, n2);
    }

    public Rectangle getBounds(int n) {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels(n));
    }

    Rectangle getBoundsInPixels(int n) {
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        RECT rECT = this.getBounds(n, true, true, true);
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n2, n3);
    }

    RECT getBounds(int n, boolean bl, boolean bl2, boolean bl3) {
        return this.getBounds(n, bl, bl2, bl3, false, true, 0L);
    }

    RECT getBounds(int n, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, long l2) {
        int n2;
        if (!bl && !bl2) {
            return new RECT();
        }
        long l3 = this.parent.handle;
        if ((this.parent.style & 0x10000000) == 0 && !this.cached && !this.parent.painted) {
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 17;
            tVITEM.hItem = this.handle;
            tVITEM.pszText = -1L;
            this.parent.ignoreCustomDraw = true;
            OS.SendMessage(l3, 4415, 0L, tVITEM);
            this.parent.ignoreCustomDraw = false;
        }
        boolean bl6 = n == 0;
        int n3 = 0;
        long l4 = this.parent.hwndHeader;
        if (l4 != 0L) {
            n3 = this.parent.columnCount;
            bl6 = (long)n == OS.SendMessage(l4, 4623, 0L, 0L);
        }
        RECT rECT = new RECT();
        if (bl6) {
            Object object;
            n2 = n3 == 0 && bl && bl2 && bl3 && bl4 ? 1 : 0;
            if (!OS.TreeView_GetItemRect(l3, this.handle, rECT, n2 == 0)) {
                return new RECT();
            }
            if (bl2 && !bl4) {
                if (OS.SendMessage(l3, 4360, 0L, 0L) != 0L) {
                    object = this.parent.getImageSize();
                    RECT rECT2 = rECT;
                    rECT2.left -= ((Point)object).x + 3;
                    if (!bl) {
                        rECT.right = rECT.left + ((Point)object).x;
                    }
                } else if (!bl) {
                    rECT.right = rECT.left;
                }
            }
            if ((bl3 || bl4 || bl5) && l4 != 0L) {
                object = new RECT();
                if (n3 != 0) {
                    if (OS.SendMessage(l4, 4615, (long)n, (RECT)object) == 0L) {
                        return new RECT();
                    }
                } else {
                    ((RECT)object).right = this.parent.scrollWidth;
                    if (((RECT)object).right == 0) {
                        object = rECT;
                    }
                }
                if (bl3 && bl5) {
                    rECT.right = ((RECT)object).right;
                }
                if (bl4) {
                    rECT.left = ((RECT)object).left;
                }
                if (bl5 && ((RECT)object).right < rECT.right) {
                    rECT.right = ((RECT)object).right;
                }
            }
        } else {
            if (0 > n || n >= n3) {
                return new RECT();
            }
            RECT rECT3 = new RECT();
            if (OS.SendMessage(l4, 4615, (long)n, rECT3) == 0L) {
                return new RECT();
            }
            if (!OS.TreeView_GetItemRect(l3, this.handle, rECT, false)) {
                return new RECT();
            }
            rECT.left = rECT3.left;
            if (bl3 && bl2 && bl5) {
                rECT.right = rECT3.right;
            } else {
                RECT rECT4;
                Object object;
                rECT.right = rECT3.left;
                Image image = null;
                if (n == 0) {
                    image = this.image;
                } else if (this.images != null) {
                    image = this.images[n];
                }
                if (image != null) {
                    object = this.parent.getImageSize();
                    rECT4 = rECT;
                    rECT4.right += ((Point)object).x;
                }
                if (bl) {
                    if (bl3 && bl5) {
                        rECT.left = rECT.right + 3;
                        rECT.right = rECT3.right;
                    } else {
                        Object object2 = n == 0 ? this.text : (object = this.strings != null ? this.strings[n] : null);
                        if (object != null) {
                            rECT4 = new RECT();
                            char[] cArray = ((String)object).toCharArray();
                            int n4 = 3104;
                            long l5 = l2;
                            long l6 = 0L;
                            if (l2 == 0L) {
                                l5 = OS.GetDC(l3);
                                l6 = this.fontHandle(n);
                                if (l6 == -1L) {
                                    l6 = OS.SendMessage(l3, 49, 0L, 0L);
                                }
                                l6 = OS.SelectObject(l5, l6);
                            }
                            OS.DrawText(l5, cArray, cArray.length, rECT4, 3104);
                            if (l2 == 0L) {
                                OS.SelectObject(l5, l6);
                                OS.ReleaseDC(l3, l5);
                            }
                            if (bl2) {
                                RECT rECT5 = rECT;
                                rECT5.right += rECT4.right - rECT4.left + 9;
                            } else {
                                rECT.left = rECT.right + 3;
                                rECT.right = rECT.left + (rECT4.right - rECT4.left) + 3;
                            }
                        }
                    }
                }
                if (bl5 && rECT3.right < rECT.right) {
                    rECT.right = rECT3.right;
                }
            }
        }
        int n5 = n2 = this.parent.linesVisible && n3 != 0 ? 1 : 0;
        if (bl || !bl2) {
            rECT.right = Math.max(rECT.left, rECT.right - n2);
        }
        rECT.bottom = Math.max(rECT.top, rECT.bottom - n2);
        return rECT;
    }

    public boolean getChecked() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((this.parent.style & 0x20) == 0) {
            return false;
        }
        long l2 = this.parent.handle;
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.stateMask = 61440;
        tVITEM.hItem = this.handle;
        long l3 = OS.SendMessage(l2, 4414, 0L, tVITEM);
        return l3 != 0L && (tVITEM.state >> 12 & 1) == 0;
    }

    public boolean getExpanded() {
        this.checkWidget();
        long l2 = this.parent.handle;
        int n = (int)OS.SendMessage(l2, 4391, this.handle, 32L);
        return (n & 0x20) != 0;
    }

    public Font getFont() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        return this.font != null ? this.font : this.parent.getFont();
    }

    public Font getFont(int n) {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        int n2 = Math.max(1, this.parent.getColumnCount());
        if (0 > n || n > n2 - 1) {
            return this.getFont();
        }
        if (this.cellFont == null || this.cellFont[n] == null) {
            return this.getFont();
        }
        return this.cellFont[n];
    }

    public Color getForeground() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if (this.foreground == -1) {
            return this.parent.getForeground();
        }
        return Color.win32_new(this.display, this.foreground);
    }

    public Color getForeground(int n) {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        int n2 = Math.max(1, this.parent.getColumnCount());
        if (0 > n || n > n2 - 1) {
            return this.getForeground();
        }
        int n3 = this.cellForeground != null ? this.cellForeground[n] : -1;
        return n3 == -1 ? this.getForeground() : Color.win32_new(this.display, n3);
    }

    public boolean getGrayed() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((this.parent.style & 0x20) == 0) {
            return false;
        }
        long l2 = this.parent.handle;
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.stateMask = 61440;
        tVITEM.hItem = this.handle;
        long l3 = OS.SendMessage(l2, 4414, 0L, tVITEM);
        return l3 != 0L && tVITEM.state >> 12 > 2;
    }

    public TreeItem getItem(int n) {
        long l2;
        long l3;
        long l4;
        this.checkWidget();
        if (n < 0) {
            this.error(6);
        }
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((l4 = OS.SendMessage(l3 = this.parent.handle, 4362, 4L, this.handle)) == 0L) {
            this.error(6);
        }
        if ((l2 = this.parent.findItem(l4, n)) == 0L) {
            this.error(6);
        }
        return this.parent._getItem(l2);
    }

    public int getItemCount() {
        long l2;
        long l3;
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((l3 = OS.SendMessage(l2 = this.parent.handle, 4362, 4L, this.handle)) == 0L) {
            return 0;
        }
        return this.parent.getItemCount(l3);
    }

    public TreeItem[] getItems() {
        long l2;
        long l3;
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((l3 = OS.SendMessage(l2 = this.parent.handle, 4362, 4L, this.handle)) == 0L) {
            return new TreeItem[0];
        }
        return this.parent.getItems(l3);
    }

    @Override
    public Image getImage() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        return super.getImage();
    }

    public Image getImage(int n) {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if (n == 0) {
            return this.getImage();
        }
        if (this.images != null && 0 <= n && n < this.images.length) {
            return this.images[n];
        }
        return null;
    }

    public Rectangle getImageBounds(int n) {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getImageBoundsInPixels(n));
    }

    Rectangle getImageBoundsInPixels(int n) {
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        RECT rECT = this.getBounds(n, false, true, false);
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n2, n3);
    }

    public Tree getParent() {
        this.checkWidget();
        return this.parent;
    }

    public TreeItem getParentItem() {
        this.checkWidget();
        long l2 = this.parent.handle;
        long l3 = OS.SendMessage(l2, 4362, 3L, this.handle);
        return l3 != 0L ? this.parent._getItem(l3) : null;
    }

    @Override
    public String getText() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        return super.getText();
    }

    public String getText(int n) {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if (n == 0) {
            return this.getText();
        }
        if (this.strings != null && 0 <= n && n < this.strings.length) {
            String string = this.strings[n];
            return string != null ? string : "";
        }
        return "";
    }

    public Rectangle getTextBounds(int n) {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getTextBoundsInPixels(n));
    }

    Rectangle getTextBoundsInPixels(int n) {
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        RECT rECT = this.getBounds(n, true, false, true);
        if (n == 0) {
            RECT rECT2 = rECT;
            rECT2.left += 2;
        }
        rECT.left = Math.min(rECT.left, rECT.right);
        rECT.right = rECT.right - 3 + 1;
        int n2 = Math.max(0, rECT.right - rECT.left);
        int n3 = Math.max(0, rECT.bottom - rECT.top);
        return new Rectangle(rECT.left, rECT.top, n2, n3);
    }

    public int indexOf(TreeItem treeItem) {
        long l2;
        long l3;
        this.checkWidget();
        if (treeItem == null) {
            this.error(4);
        }
        if (treeItem.isDisposed()) {
            this.error(5);
        }
        return (l3 = OS.SendMessage(l2 = this.parent.handle, 4362, 4L, this.handle)) == 0L ? -1 : this.parent.findIndex(l3, treeItem.handle);
    }

    void redraw() {
        RECT rECT;
        boolean bl;
        if (this.parent.currentItem == this || !this.parent.getDrawing()) {
            return;
        }
        long l2 = this.parent.handle;
        if (!OS.IsWindowVisible(l2)) {
            return;
        }
        boolean bl2 = bl = (this.parent.style & 0x10010000) != 0;
        if (!bl) {
            boolean bl3 = bl = this.parent.columnCount != 0;
            if (!bl && (this.parent.hooks(40) || this.parent.hooks(42))) {
                bl = true;
            }
        }
        if (OS.TreeView_GetItemRect(l2, this.handle, rECT = new RECT(), !bl)) {
            OS.InvalidateRect(l2, rECT, true);
        }
    }

    void redraw(int n, boolean bl, boolean bl2) {
        if (this.parent.currentItem == this || !this.parent.getDrawing()) {
            return;
        }
        long l2 = this.parent.handle;
        if (!OS.IsWindowVisible(l2)) {
            return;
        }
        boolean bl3 = n == 0 && bl && bl2;
        RECT rECT = this.getBounds(n, bl, bl2, true, bl3, true, 0L);
        OS.InvalidateRect(l2, rECT, true);
    }

    @Override
    void releaseChildren(boolean bl) {
        if (bl) {
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 20;
            this.parent.releaseItems(this.handle, tVITEM);
        }
        super.releaseChildren(bl);
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.handle = 0L;
        this.parent = null;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.strings = null;
        this.images = null;
        Object var1_1 = null;
        this.cellForeground = var1_1;
        this.cellBackground = var1_1;
        this.cellFont = null;
    }

    public void removeAll() {
        block4: {
            boolean bl;
            this.checkWidget();
            long l2 = this.parent.handle;
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 20;
            tVITEM.hItem = OS.SendMessage(l2, 4362, 4L, this.handle);
            boolean bl2 = bl = this.parent.itemCount > 30;
            if (bl) {
                this.parent.setRedraw(false);
            }
            while (tVITEM.hItem != 0L) {
                TreeItem treeItem;
                OS.SendMessage(l2, 4414, 0L, tVITEM);
                TreeItem treeItem2 = treeItem = tVITEM.lParam != -1L ? this.parent.items[(int)tVITEM.lParam] : null;
                if (treeItem != null && !treeItem.isDisposed()) {
                    treeItem.dispose();
                } else {
                    this.parent.releaseItem(tVITEM.hItem, tVITEM, false);
                    this.parent.destroyItem(null, tVITEM.hItem);
                }
                tVITEM.hItem = OS.SendMessage(l2, 4362, 4L, this.handle);
            }
            if (!bl) break block4;
            this.parent.setRedraw(true);
        }
    }

    public void setBackground(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            this.error(5);
        }
        int n = -1;
        if (color != null) {
            this.parent.customDraw = true;
            n = color.handle;
        }
        if (this.background == n) {
            return;
        }
        this.background = n;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        this.redraw();
    }

    public void setBackground(int n, Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            this.error(5);
        }
        int n2 = Math.max(1, this.parent.getColumnCount());
        if (0 > n || n > n2 - 1) {
            return;
        }
        int n3 = -1;
        if (color != null) {
            this.parent.customDraw = true;
            n3 = color.handle;
        }
        if (this.cellBackground == null) {
            this.cellBackground = new int[n2];
            for (int i = 0; i < n2; ++i) {
                this.cellBackground[i] = -1;
            }
        }
        if (this.cellBackground[n] == n3) {
            return;
        }
        this.cellBackground[n] = n3;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        this.redraw(n, true, true);
    }

    public void setChecked(boolean bl) {
        RECT rECT;
        this.checkWidget();
        if ((this.parent.style & 0x20) == 0) {
            return;
        }
        long l2 = this.parent.handle;
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.stateMask = 61440;
        tVITEM.hItem = this.handle;
        OS.SendMessage(l2, 4414, 0L, tVITEM);
        int n = tVITEM.state >> 12;
        if (bl) {
            if ((n & 1) != 0) {
                ++n;
            }
        } else if ((n & 1) == 0) {
            --n;
        }
        if (tVITEM.state == (n <<= 12)) {
            return;
        }
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        tVITEM.state = n;
        OS.SendMessage(l2, 4415, 0L, tVITEM);
        if ((this.parent.style & 0x10000000) != 0 && this.parent.currentItem == this && OS.IsWindowVisible(l2) && OS.TreeView_GetItemRect(l2, this.handle, rECT = new RECT(), false)) {
            OS.InvalidateRect(l2, rECT, true);
        }
    }

    public void setExpanded(boolean bl) {
        long l2;
        long l3;
        Object object;
        int n;
        this.checkWidget();
        long l4 = this.parent.handle;
        if (OS.SendMessage(l4, 4362, 4L, this.handle) == 0L) {
            return;
        }
        int n2 = (int)OS.SendMessage(l4, 4391, this.handle, 32L);
        if ((n2 & 0x20) != 0 == bl) {
            return;
        }
        RECT rECT = null;
        RECT[] rECTArray = null;
        SCROLLINFO sCROLLINFO = null;
        int n3 = 0;
        long l5 = 0L;
        boolean bl2 = false;
        boolean bl3 = true;
        long l6 = OS.SendMessage(l4, 4362, 5L, 0L);
        if (l6 != 0L) {
            sCROLLINFO = new SCROLLINFO();
            sCROLLINFO.cbSize = SCROLLINFO.sizeof;
            sCROLLINFO.fMask = 23;
            if (!OS.GetScrollInfo(l4, 0, sCROLLINFO)) {
                sCROLLINFO = null;
            }
            if (this.parent.getDrawing() && OS.IsWindowVisible(l4)) {
                boolean bl4 = true;
                n3 = (int)OS.SendMessage(l4, 4368, 0L, 0L);
                rECTArray = new RECT[n3 + 1];
                long l7 = l6;
                n = 0;
                while (l7 != 0L && n < n3) {
                    object = new RECT();
                    if (OS.TreeView_GetItemRect(l4, l7, (RECT)object, true)) {
                        rECTArray[n++] = object;
                    }
                    l7 = OS.SendMessage(l4, 4362, 6L, l7);
                }
                bl2 = true;
                n3 = n;
                l5 = l7;
                rECT = new RECT();
                OS.GetClientRect(l4, rECT);
                l3 = this.parent.topHandle();
                OS.UpdateWindow(l3);
                OS.DefWindowProc(l3, 11, 0L, 0L);
                if (l4 != l3) {
                    OS.UpdateWindow(l4);
                    OS.DefWindowProc(l4, 11, 0L, 0L);
                }
            }
        }
        long l8 = OS.SendMessage(l4, 4362, 9L, 0L);
        this.parent.ignoreExpand = true;
        OS.SendMessage(l4, 4354, bl ? 2L : 1L, this.handle);
        this.parent.ignoreExpand = false;
        if (l6 != 0L) {
            boolean bl5 = false;
            if (!bl) {
                RECT rECT2 = new RECT();
                while (l6 != 0L && !OS.TreeView_GetItemRect(l4, l6, rECT2, false)) {
                    l6 = OS.SendMessage(l4, 4362, 3L, l6);
                    bl5 = true;
                }
            }
            n = 1;
            if (l6 != 0L) {
                OS.SendMessage(l4, 4363, 5L, l6);
                int n4 = n = l6 != OS.SendMessage(l4, 4362, 5L, 0L) ? 1 : 0;
            }
            if (!bl5 && n == 0 && sCROLLINFO != null) {
                object = new SCROLLINFO();
                ((SCROLLINFO)object).cbSize = SCROLLINFO.sizeof;
                ((SCROLLINFO)object).fMask = 23;
                if (OS.GetScrollInfo(l4, 0, (SCROLLINFO)object) && sCROLLINFO.nPos != ((SCROLLINFO)object).nPos) {
                    l3 = OS.MAKELPARAM(4, sCROLLINFO.nPos);
                    OS.SendMessage(l4, 276, l3, 0L);
                }
            }
            if (bl2) {
                boolean bl6 = false;
                if (!bl5 && n == 0) {
                    RECT rECT3 = new RECT();
                    OS.GetClientRect(l4, rECT3);
                    if (OS.EqualRect(rECT, rECT3)) {
                        RECT rECT4;
                        int n5;
                        long l9 = l6;
                        for (n5 = 0; l9 != 0L && n5 < n3 && (!OS.TreeView_GetItemRect(l4, l9, rECT4 = new RECT(), true) || OS.EqualRect(rECT4, rECTArray[n5])); ++n5) {
                            l9 = OS.SendMessage(l4, 4362, 6L, l9);
                        }
                        bl6 = n5 == n3 && l9 == l5;
                    }
                }
                long l10 = this.parent.topHandle();
                OS.DefWindowProc(l10, 11, 1L, 0L);
                if (l4 != l10) {
                    OS.DefWindowProc(l4, 11, 1L, 0L);
                }
                if (bl6) {
                    RECT rECT5;
                    this.parent.updateScrollBar();
                    SCROLLINFO sCROLLINFO2 = new SCROLLINFO();
                    sCROLLINFO2.cbSize = SCROLLINFO.sizeof;
                    sCROLLINFO2.fMask = 23;
                    if (OS.GetScrollInfo(l4, 1, sCROLLINFO2)) {
                        OS.SetScrollInfo(l4, 1, sCROLLINFO2, true);
                    }
                    if (this.handle == l5 && OS.TreeView_GetItemRect(l4, l5, rECT5 = new RECT(), false)) {
                        OS.InvalidateRect(l4, rECT5, true);
                    }
                } else {
                    int n6 = 1157;
                    OS.RedrawWindow(l10, null, 0L, 1157);
                }
            }
        }
        if ((l2 = OS.SendMessage(l4, 4362, 9L, 0L)) != l8) {
            Event event = new Event();
            if (l2 != 0L) {
                event.item = this.parent._getItem(l2);
                this.parent.hAnchor = l2;
            }
            this.parent.sendSelectionEvent(13, event, true);
        }
    }

    public void setFont(Font font) {
        Font font2;
        this.checkWidget();
        if (font != null && font.isDisposed()) {
            this.error(5);
        }
        if ((font2 = this.font) == font) {
            return;
        }
        this.font = font;
        if (font2 != null && font2.equals(font)) {
            return;
        }
        if (font != null) {
            this.parent.customDraw = true;
        }
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        if ((this.parent.style & 0x10000000) == 0 && !this.cached && !this.parent.painted) {
            return;
        }
        long l2 = this.parent.handle;
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 17;
        tVITEM.hItem = this.handle;
        tVITEM.pszText = -1L;
        OS.SendMessage(l2, 4415, 0L, tVITEM);
    }

    public void setFont(int n, Font font) {
        Font font2;
        this.checkWidget();
        if (font != null && font.isDisposed()) {
            this.error(5);
        }
        int n2 = Math.max(1, this.parent.getColumnCount());
        if (0 > n || n > n2 - 1) {
            return;
        }
        if (this.cellFont == null) {
            if (font == null) {
                return;
            }
            this.cellFont = new Font[n2];
        }
        if ((font2 = this.cellFont[n]) == font) {
            return;
        }
        this.cellFont[n] = font;
        if (font2 != null && font2.equals(font)) {
            return;
        }
        if (font != null) {
            this.parent.customDraw = true;
        }
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        if (n == 0) {
            if ((this.parent.style & 0x10000000) == 0 && !this.cached && !this.parent.painted) {
                return;
            }
            long l2 = this.parent.handle;
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 17;
            tVITEM.hItem = this.handle;
            tVITEM.pszText = -1L;
            OS.SendMessage(l2, 4415, 0L, tVITEM);
        } else {
            this.redraw(n, true, false);
        }
    }

    public void setForeground(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            this.error(5);
        }
        int n = -1;
        if (color != null) {
            this.parent.customDraw = true;
            n = color.handle;
        }
        if (this.foreground == n) {
            return;
        }
        this.foreground = n;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        this.redraw();
    }

    public void setForeground(int n, Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            this.error(5);
        }
        int n2 = Math.max(1, this.parent.getColumnCount());
        if (0 > n || n > n2 - 1) {
            return;
        }
        int n3 = -1;
        if (color != null) {
            this.parent.customDraw = true;
            n3 = color.handle;
        }
        if (this.cellForeground == null) {
            this.cellForeground = new int[n2];
            for (int i = 0; i < n2; ++i) {
                this.cellForeground[i] = -1;
            }
        }
        if (this.cellForeground[n] == n3) {
            return;
        }
        this.cellForeground[n] = n3;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        this.redraw(n, true, false);
    }

    public void setGrayed(boolean bl) {
        RECT rECT;
        this.checkWidget();
        if ((this.parent.style & 0x20) == 0) {
            return;
        }
        long l2 = this.parent.handle;
        TVITEM tVITEM = new TVITEM();
        tVITEM.mask = 24;
        tVITEM.stateMask = 61440;
        tVITEM.hItem = this.handle;
        OS.SendMessage(l2, 4414, 0L, tVITEM);
        int n = tVITEM.state >> 12;
        if (bl) {
            if (n <= 2) {
                n += 2;
            }
        } else if (n > 2) {
            n -= 2;
        }
        if (tVITEM.state == (n <<= 12)) {
            return;
        }
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        tVITEM.state = n;
        OS.SendMessage(l2, 4415, 0L, tVITEM);
        if ((this.parent.style & 0x10000000) != 0 && this.parent.currentItem == this && OS.IsWindowVisible(l2) && OS.TreeView_GetItemRect(l2, this.handle, rECT = new RECT(), false)) {
            OS.InvalidateRect(l2, rECT, true);
        }
    }

    public void setImage(Image[] imageArray) {
        this.checkWidget();
        if (imageArray == null) {
            this.error(4);
        }
        for (int i = 0; i < imageArray.length; ++i) {
            this.setImage(i, imageArray[i]);
        }
    }

    public void setImage(int n, Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        Image image2 = null;
        if (n == 0) {
            if (image != null && image.type == 1 && image.equals(this.image)) {
                return;
            }
            image2 = this.image;
            super.setImage(image);
        }
        int n2 = Math.max(1, this.parent.getColumnCount());
        if (0 > n || n > n2 - 1) {
            return;
        }
        if (this.images == null && n != 0) {
            Image[] imageArray = new Image[n2];
            this.images = imageArray;
            imageArray[0] = image;
        }
        if (this.images != null) {
            if (image != null && image.type == 1 && image.equals(this.images[n])) {
                return;
            }
            image2 = this.images[n];
            this.images[n] = image;
        }
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        this.parent.imageIndex(image, n);
        if (n == 0) {
            if ((this.parent.style & 0x10000000) == 0 && !this.cached && !this.parent.painted) {
                return;
            }
            long l2 = this.parent.handle;
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 50;
            tVITEM.hItem = this.handle;
            TVITEM tVITEM2 = tVITEM;
            TVITEM tVITEM3 = tVITEM;
            int n3 = -1;
            tVITEM3.iSelectedImage = -1;
            tVITEM2.iImage = -1;
            TVITEM tVITEM4 = tVITEM;
            tVITEM4.mask |= 1;
            tVITEM.pszText = -1L;
            OS.SendMessage(l2, 4415, 0L, tVITEM);
        } else {
            boolean bl = image == null && image2 != null || image != null && image2 == null;
            this.redraw(n, bl, true);
        }
    }

    @Override
    public void setImage(Image image) {
        this.checkWidget();
        this.setImage(0, image);
    }

    public void setItemCount(int n) {
        this.checkWidget();
        n = Math.max(0, n);
        this.parent.setItemCount(n, this.handle);
    }

    public void setText(String[] stringArray) {
        this.checkWidget();
        if (stringArray == null) {
            this.error(4);
        }
        for (int i = 0; i < stringArray.length; ++i) {
            String string = stringArray[i];
            if (string == null) continue;
            this.setText(i, string);
        }
    }

    public void setText(int n, String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        if (n == 0) {
            if (string.equals(this.text)) {
                return;
            }
            super.setText(string);
        }
        int n2 = Math.max(1, this.parent.getColumnCount());
        if (0 > n || n > n2 - 1) {
            return;
        }
        if (this.strings == null && n != 0) {
            String[] stringArray = new String[n2];
            this.strings = stringArray;
            stringArray[0] = this.text;
        }
        if (this.strings != null) {
            if (string.equals(this.strings[n])) {
                return;
            }
            this.strings[n] = string;
        }
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        if (n == 0) {
            if ((this.parent.style & 0x10000000) == 0 && !this.cached && !this.parent.painted) {
                return;
            }
            long l2 = this.parent.handle;
            TVITEM tVITEM = new TVITEM();
            tVITEM.mask = 17;
            tVITEM.hItem = this.handle;
            tVITEM.pszText = -1L;
            OS.SendMessage(l2, 4415, 0L, tVITEM);
        } else {
            this.redraw(n, true, false);
        }
    }

    @Override
    public void setText(String string) {
        this.checkWidget();
        this.setText(0, string);
    }

    void sort() {
        this.checkWidget();
        if ((this.parent.style & 0x10000000) != 0) {
            return;
        }
        this.parent.sort(this.handle, false);
    }

    @Override
    String getNameText() {
        if ((this.parent.style & 0x10000000) != 0 && !this.cached) {
            return "*virtual*";
        }
        return super.getNameText();
    }
}

