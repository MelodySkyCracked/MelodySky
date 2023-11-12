/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.LVITEM;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Table;

public class TableItem
extends Item {
    Table parent;
    String[] strings;
    Image[] images;
    Font font;
    Font[] cellFont;
    boolean checked;
    boolean grayed;
    boolean cached;
    int imageIndent;
    int background = -1;
    int foreground = -1;
    int[] cellBackground;
    int[] cellForeground;

    public TableItem(Table table, int n) {
        this(table, n, TableItem.checkNull(table).getItemCount(), true);
    }

    public TableItem(Table table, int n, int n2) {
        this(table, n, n2, true);
    }

    TableItem(Table table, int n, int n2, boolean bl) {
        super(table, n);
        this.parent = table;
        if (bl) {
            table.createItem(this, n2);
        }
    }

    static Table checkNull(Table table) {
        if (table == null) {
            SWT.error(4);
        }
        return table;
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
        this.imageIndent = 0;
        boolean bl = false;
        this.grayed = false;
        this.checked = false;
        this.font = null;
        int n = -1;
        this.foreground = -1;
        this.background = -1;
        this.cellFont = null;
        Object var3_3 = null;
        this.cellForeground = var3_3;
        this.cellBackground = var3_3;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = false;
        }
    }

    @Override
    void destroyWidget() {
        this.parent.destroyItem(this);
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
        int n;
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((n = this.parent.indexOf(this)) == -1) {
            return new Rectangle(0, 0, 0, 0);
        }
        RECT rECT = this.getBounds(n, 0, true, false, false);
        int n2 = rECT.right - rECT.left;
        int n3 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n2, n3);
    }

    public Rectangle getBounds(int n) {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getBoundsInPixels(n));
    }

    Rectangle getBoundsInPixels(int n) {
        int n2;
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((n2 = this.parent.indexOf(this)) == -1) {
            return new Rectangle(0, 0, 0, 0);
        }
        RECT rECT = this.getBounds(n2, n, true, true, true);
        int n3 = rECT.right - rECT.left;
        int n4 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n3, n4);
    }

    RECT getBounds(int n, int n2, boolean bl, boolean bl2, boolean bl3) {
        return this.getBounds(n, n2, bl, bl2, bl3, false, 0L);
    }

    RECT getBounds(int n, int n2, boolean bl, boolean bl2, boolean bl3, boolean bl4, long l2) {
        long l3;
        if (!bl && !bl2) {
            return new RECT();
        }
        int n3 = this.parent.getColumnCount();
        if (0 > n2 || n2 >= Math.max(1, n3)) {
            return new RECT();
        }
        if (this.parent.fixScrollWidth) {
            this.parent.setScrollWidth(null, true);
        }
        RECT rECT = new RECT();
        long l4 = this.parent.handle;
        int n4 = (int)OS.SendMessage(l4, 4151, 0L, 0L);
        if (n2 == 0 && (n4 & 0x20) == 0) {
            long l5;
            if (this.parent.explorerTheme) {
                rECT.left = 1;
                this.parent.ignoreCustomDraw = true;
                l5 = OS.SendMessage(l4, 4110, (long)n, rECT);
                this.parent.ignoreCustomDraw = false;
                if (l5 == 0L) {
                    return new RECT();
                }
                if (bl) {
                    Object object;
                    int n5 = 0;
                    long l6 = this.fontHandle(n2);
                    if (l6 == -1L && l2 == 0L) {
                        object = new TCHAR(this.parent.getCodePage(), this.text, true);
                        n5 = (int)OS.SendMessage(l4, 4183, 0L, (TCHAR)object);
                    } else {
                        object = this.text.toCharArray();
                        long l7 = l2 != 0L ? l2 : OS.GetDC(l4);
                        long l8 = -1L;
                        if (l2 == 0L) {
                            if (l6 == -1L) {
                                l6 = OS.SendMessage(l4, 49, 0L, 0L);
                            }
                            l8 = OS.SelectObject(l7, l6);
                        }
                        RECT rECT2 = new RECT();
                        int n6 = 3104;
                        OS.DrawText(l7, object, ((char[])object).length, rECT2, 3104);
                        n5 = rECT2.right - rECT2.left;
                        if (l2 == 0L) {
                            if (l8 != -1L) {
                                OS.SelectObject(l7, l8);
                            }
                            OS.ReleaseDC(l4, l7);
                        }
                    }
                    if (!bl2) {
                        rECT.left = rECT.right;
                    }
                    object = rECT;
                    object.right += n5 + 8;
                }
            } else if (bl) {
                rECT.left = 3;
                this.parent.ignoreCustomDraw = true;
                l5 = OS.SendMessage(l4, 4110, (long)n, rECT);
                this.parent.ignoreCustomDraw = false;
                if (l5 == 0L) {
                    return new RECT();
                }
                if (!bl2) {
                    RECT rECT3 = new RECT();
                    rECT3.left = 1;
                    this.parent.ignoreCustomDraw = true;
                    l5 = OS.SendMessage(l4, 4110, (long)n, rECT3);
                    this.parent.ignoreCustomDraw = false;
                    if (l5 != 0L) {
                        rECT.left = rECT3.right;
                    }
                }
            } else {
                rECT.left = 1;
                this.parent.ignoreCustomDraw = true;
                l5 = OS.SendMessage(l4, 4110, (long)n, rECT);
                this.parent.ignoreCustomDraw = false;
                if (l5 == 0L) {
                    return new RECT();
                }
            }
            if (bl3 || bl4) {
                RECT rECT4 = new RECT();
                l3 = OS.SendMessage(l4, 4127, 0L, 0L);
                OS.SendMessage(l3, 4615, 0L, rECT4);
                OS.MapWindowPoints(l3, l4, rECT4, 2);
                if (bl && bl3) {
                    rECT.right = rECT4.right;
                }
                if (bl2 && bl4) {
                    rECT.left = rECT4.left;
                }
            }
        } else {
            boolean bl5 = n2 == 0 && this.image != null || this.images != null && this.images[n2] != null;
            rECT.top = n2;
            if (bl3 || bl4 || l2 == 0L) {
                RECT rECT5;
                rECT.left = bl ? 2 : 1;
                this.parent.ignoreCustomDraw = true;
                l3 = OS.SendMessage(l4, 4152, (long)n, rECT);
                this.parent.ignoreCustomDraw = false;
                if (l3 == 0L) {
                    return new RECT();
                }
                if (n2 == 0 && bl && bl2) {
                    rECT5 = new RECT();
                    rECT5.left = 1;
                    this.parent.ignoreCustomDraw = true;
                    l3 = OS.SendMessage(l4, 4152, (long)n, rECT5);
                    this.parent.ignoreCustomDraw = false;
                    if (l3 != 0L) {
                        rECT.left = rECT5.left;
                    }
                }
                if (bl5) {
                    if (n2 != 0 && bl && !bl2) {
                        rECT5 = new RECT();
                        rECT5.top = n2;
                        rECT5.left = 1;
                        if (OS.SendMessage(l4, 4152, (long)n, rECT5) != 0L) {
                            rECT.left = rECT5.right + 2;
                        }
                    }
                } else if (bl2 && !bl) {
                    rECT.right = rECT.left;
                }
                if (n2 == 0 && bl4) {
                    rECT5 = new RECT();
                    long l9 = OS.SendMessage(l4, 4127, 0L, 0L);
                    OS.SendMessage(l9, 4615, 0L, rECT5);
                    OS.MapWindowPoints(l9, l4, rECT5, 2);
                    rECT.left = rECT5.left;
                }
            } else {
                rECT.left = 1;
                this.parent.ignoreCustomDraw = true;
                l3 = OS.SendMessage(l4, 4152, (long)n, rECT);
                this.parent.ignoreCustomDraw = false;
                if (l3 == 0L) {
                    return new RECT();
                }
                if (!bl5) {
                    rECT.right = rECT.left;
                }
                if (bl) {
                    String string;
                    String string2 = n2 == 0 ? this.text : (string = this.strings != null ? this.strings[n2] : null);
                    if (string != null) {
                        RECT rECT6 = new RECT();
                        char[] cArray = string.toCharArray();
                        int n7 = 3104;
                        OS.DrawText(l2, cArray, cArray.length, rECT6, 3104);
                        RECT rECT7 = rECT;
                        rECT7.right += rECT6.right - rECT6.left + 12 + 2;
                    }
                }
            }
        }
        int n8 = this.parent.getLinesVisible() ? 1 : 0;
        RECT rECT8 = rECT;
        rECT8.top -= n8;
        if (n2 != 0) {
            RECT rECT9 = rECT;
            rECT9.left += n8;
        }
        rECT.right = Math.max(rECT.right, rECT.left);
        RECT rECT10 = rECT;
        rECT10.top += n8;
        rECT.bottom = Math.max(rECT.bottom - n8, rECT.top);
        return rECT;
    }

    public boolean getChecked() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        return (this.parent.style & 0x20) != 0 && this.checked;
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
        return (this.parent.style & 0x20) != 0 && this.grayed;
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
        int n2;
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((n2 = this.parent.indexOf(this)) == -1) {
            return new Rectangle(0, 0, 0, 0);
        }
        RECT rECT = this.getBounds(n2, n, false, true, false);
        int n3 = rECT.right - rECT.left;
        int n4 = rECT.bottom - rECT.top;
        return new Rectangle(rECT.left, rECT.top, n3, n4);
    }

    public int getImageIndent() {
        this.checkWidget();
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        return this.imageIndent;
    }

    @Override
    String getNameText() {
        if ((this.parent.style & 0x10000000) != 0 && !this.cached) {
            return "*virtual*";
        }
        return super.getNameText();
    }

    public Table getParent() {
        this.checkWidget();
        return this.parent;
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
        RECT rECT;
        int n2;
        if (!this.parent.checkData(this, true)) {
            this.error(24);
        }
        if ((n2 = this.parent.indexOf(this)) == -1) {
            return new Rectangle(0, 0, 0, 0);
        }
        RECT rECT2 = rECT = this.getBounds(n2, n, true, false, true);
        rECT.left += 2;
        if (n != 0) {
            RECT rECT3 = rECT2;
            rECT3.left += 4;
        }
        rECT2.left = Math.min(rECT2.left, rECT2.right);
        rECT2.right -= 4;
        int n3 = Math.max(0, rECT2.right - rECT2.left);
        int n4 = Math.max(0, rECT2.bottom - rECT2.top);
        return new Rectangle(rECT2.left, rECT2.top, n3, n4);
    }

    void redraw() {
        if (this.parent.currentItem == this || !this.parent.getDrawing()) {
            return;
        }
        long l2 = this.parent.handle;
        if (!OS.IsWindowVisible(l2)) {
            return;
        }
        int n = this.parent.indexOf(this);
        if (n == -1) {
            return;
        }
        OS.SendMessage(l2, 4117, (long)n, n);
    }

    void redraw(int n, boolean bl, boolean bl2) {
        if (this.parent.currentItem == this || !this.parent.getDrawing()) {
            return;
        }
        long l2 = this.parent.handle;
        if (!OS.IsWindowVisible(l2)) {
            return;
        }
        int n2 = this.parent.indexOf(this);
        if (n2 == -1) {
            return;
        }
        RECT rECT = this.getBounds(n2, n, bl, bl2, true);
        OS.InvalidateRect(l2, rECT, true);
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.strings = null;
        this.images = null;
        this.cellFont = null;
        Object var1_1 = null;
        this.cellForeground = var1_1;
        this.cellBackground = var1_1;
    }

    public void setBackground(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            this.error(5);
        }
        int n = -1;
        if (color != null) {
            this.parent.setCustomDraw(true);
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
            this.parent.setCustomDraw(true);
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
        this.checkWidget();
        if ((this.parent.style & 0x20) == 0) {
            return;
        }
        if (this.checked == bl) {
            return;
        }
        this.setChecked(bl, false);
    }

    void setChecked(boolean bl, boolean bl2) {
        this.checked = bl;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        if (bl2) {
            Event event = new Event();
            event.item = this;
            event.detail = 32;
            this.parent.sendSelectionEvent(13, event, false);
        }
        this.redraw();
    }

    public void setFont(Font font) {
        int n;
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
            this.parent.setCustomDraw(true);
        }
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        if ((this.parent.style & 0x10000000) == 0 && this.cached && (n = this.parent.indexOf(this)) != -1) {
            long l2 = this.parent.handle;
            LVITEM lVITEM = new LVITEM();
            lVITEM.mask = 1;
            lVITEM.iItem = n;
            lVITEM.pszText = -1L;
            OS.SendMessage(l2, 4172, 0L, lVITEM);
            this.cached = false;
        }
        this.parent.setScrollWidth(this, false);
        this.redraw();
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
            this.parent.setCustomDraw(true);
        }
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        if (n == 0) {
            int n3;
            if ((this.parent.style & 0x10000000) == 0 && this.cached && (n3 = this.parent.indexOf(this)) != -1) {
                long l2 = this.parent.handle;
                LVITEM lVITEM = new LVITEM();
                lVITEM.mask = 1;
                lVITEM.iItem = n3;
                lVITEM.pszText = -1L;
                OS.SendMessage(l2, 4172, 0L, lVITEM);
                this.cached = false;
            }
            this.parent.setScrollWidth(this, false);
        }
        this.redraw(n, true, false);
    }

    public void setForeground(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            this.error(5);
        }
        int n = -1;
        if (color != null) {
            this.parent.setCustomDraw(true);
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
            this.parent.setCustomDraw(true);
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
        this.checkWidget();
        if ((this.parent.style & 0x20) == 0) {
            return;
        }
        if (this.grayed == bl) {
            return;
        }
        this.grayed = bl;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        }
        this.redraw();
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
            this.parent.setScrollWidth(this, false);
        }
        boolean bl = image == null && image2 != null || image != null && image2 == null;
        this.redraw(n, bl, true);
    }

    @Override
    public void setImage(Image image) {
        this.checkWidget();
        this.setImage(0, image);
    }

    @Deprecated
    public void setImageIndent(int n) {
        this.checkWidget();
        if (n < 0) {
            return;
        }
        if (this.imageIndent == n) {
            return;
        }
        this.imageIndent = n;
        if ((this.parent.style & 0x10000000) != 0) {
            this.cached = true;
        } else {
            int n2 = this.parent.indexOf(this);
            if (n2 != -1) {
                long l2 = this.parent.handle;
                LVITEM lVITEM = new LVITEM();
                lVITEM.mask = 16;
                lVITEM.iItem = n2;
                lVITEM.iIndent = n;
                OS.SendMessage(l2, 4172, 0L, lVITEM);
            }
        }
        this.parent.setScrollWidth(this, false);
        this.redraw();
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
            int n3;
            if ((this.parent.style & 0x10000000) == 0 && this.cached && (n3 = this.parent.indexOf(this)) != -1) {
                long l2 = this.parent.handle;
                LVITEM lVITEM = new LVITEM();
                lVITEM.mask = 1;
                lVITEM.iItem = n3;
                lVITEM.pszText = -1L;
                OS.SendMessage(l2, 4172, 0L, lVITEM);
                this.cached = false;
            }
            this.parent.setScrollWidth(this, false);
        }
        this.redraw(n, true, false);
    }

    @Override
    public void setText(String string) {
        this.checkWidget();
        this.setText(0, string);
    }
}

