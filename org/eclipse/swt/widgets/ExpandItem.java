/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.DPIUtil;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.Item;

public class ExpandItem
extends Item {
    ExpandBar parent;
    Control control;
    boolean expanded;
    boolean hover;
    int x;
    int y;
    int width;
    int height;
    int imageHeight;
    int imageWidth;
    static final int TEXT_INSET = 6;
    static final int BORDER = 1;
    static final int CHEVRON_SIZE = 24;

    public ExpandItem(ExpandBar expandBar, int n) {
        this(expandBar, n, ExpandItem.checkNull(expandBar).getItemCount());
    }

    public ExpandItem(ExpandBar expandBar, int n, int n2) {
        super(expandBar, n);
        this.parent = expandBar;
        this.parent.createItem(this, n, n2);
    }

    static ExpandBar checkNull(ExpandBar expandBar) {
        if (expandBar == null) {
            SWT.error(4);
        }
        return expandBar;
    }

    private void drawChevron(long l2, RECT rECT) {
        int[] nArray;
        int[] nArray2;
        int n;
        long l3 = OS.SelectObject(l2, OS.GetSysColorBrush(15));
        OS.PatBlt(l2, rECT.left, rECT.top, rECT.right - rECT.left, rECT.bottom - rECT.top, 15728673);
        OS.SelectObject(l2, l3);
        rECT.left += 4;
        rECT.top += 4;
        rECT.right -= 4;
        rECT.bottom -= 4;
        long l4 = OS.CreatePen(0, 1, this.parent.getForegroundPixel());
        long l5 = OS.SelectObject(l2, l4);
        if (this.expanded) {
            n = rECT.left + 5;
            int n2 = rECT.top + 7;
            nArray2 = new int[]{n, n2, n + 1, n2, n + 1, n2 - 1, n + 2, n2 - 1, n + 2, n2 - 2, n + 3, n2 - 2, n + 3, n2 - 3, n + 3, n2 - 2, n + 4, n2 - 2, n + 4, n2 - 1, n + 5, n2 - 1, n + 5, n2, n + 7, n2};
            nArray = new int[]{n, n2 += 4, n + 1, n2, n + 1, n2 - 1, n + 2, n2 - 1, n + 2, n2 - 2, n + 3, n2 - 2, n + 3, n2 - 3, n + 3, n2 - 2, n + 4, n2 - 2, n + 4, n2 - 1, n + 5, n2 - 1, n + 5, n2, n + 7, n2};
        } else {
            n = rECT.left + 5;
            int n3 = rECT.top + 4;
            nArray2 = new int[]{n, n3, n + 1, n3, n + 1, n3 + 1, n + 2, n3 + 1, n + 2, n3 + 2, n + 3, n3 + 2, n + 3, n3 + 3, n + 3, n3 + 2, n + 4, n3 + 2, n + 4, n3 + 1, n + 5, n3 + 1, n + 5, n3, n + 7, n3};
            nArray = new int[]{n, n3 += 4, n + 1, n3, n + 1, n3 + 1, n + 2, n3 + 1, n + 2, n3 + 2, n + 3, n3 + 2, n + 3, n3 + 3, n + 3, n3 + 2, n + 4, n3 + 2, n + 4, n3 + 1, n + 5, n3 + 1, n + 5, n3, n + 7, n3};
        }
        OS.Polyline(l2, nArray2, nArray2.length / 2);
        OS.Polyline(l2, nArray, nArray.length / 2);
        if (this.hover) {
            long l6 = OS.CreatePen(0, 1, OS.GetSysColor(20));
            long l7 = OS.CreatePen(0, 1, OS.GetSysColor(16));
            OS.SelectObject(l2, l6);
            int[] nArray3 = new int[]{rECT.left, rECT.bottom, rECT.left, rECT.top, rECT.right, rECT.top};
            OS.Polyline(l2, nArray3, nArray3.length / 2);
            OS.SelectObject(l2, l7);
            int[] nArray4 = new int[]{rECT.right, rECT.top, rECT.right, rECT.bottom, rECT.left, rECT.bottom};
            OS.Polyline(l2, nArray4, nArray4.length / 2);
            OS.SelectObject(l2, l5);
            OS.DeleteObject(l6);
            OS.DeleteObject(l7);
        } else {
            OS.SelectObject(l2, l5);
        }
        OS.DeleteObject(l4);
    }

    void drawItem(GC gC, long l2, RECT rECT, boolean bl) {
        int n;
        Object object;
        long l3 = gC.handle;
        int n2 = this.parent.getBandHeight();
        RECT rECT2 = new RECT();
        OS.SetRect(rECT2, this.x, this.y, this.x + this.width, this.y + n2);
        if (l2 != 0L) {
            OS.DrawThemeBackground(l2, l3, 8, 0, rECT2, rECT);
        } else {
            long l4 = OS.SelectObject(l3, OS.GetSysColorBrush(15));
            OS.PatBlt(l3, rECT2.left, rECT2.top, rECT2.right - rECT2.left, rECT2.bottom - rECT2.top, 15728673);
            OS.SelectObject(l3, l4);
        }
        if (this.image != null) {
            RECT rECT3 = rECT2;
            rECT3.left += 6;
            if (this.imageHeight > n2) {
                gC.drawImage(this.image, DPIUtil.autoScaleDown(rECT2.left), DPIUtil.autoScaleDown(rECT2.top + n2 - this.imageHeight));
            } else {
                gC.drawImage(this.image, DPIUtil.autoScaleDown(rECT2.left), DPIUtil.autoScaleDown(rECT2.top + (n2 - this.imageHeight) / 2));
            }
            object = rECT2;
            object.left += this.imageWidth;
        }
        if (this.text.length() > 0) {
            RECT rECT4 = rECT2;
            rECT4.left += 6;
            object = (this.style & Integer.MIN_VALUE) != 0 ? (((n = OS.GetWindowLong(this.parent.handle, -20)) & 0x400000) != 0 ? ("\u202a" + this.text).toCharArray() : ("\u202b" + this.text).toCharArray()) : this.text.toCharArray();
            if (l2 != 0L) {
                OS.DrawThemeText(l2, l3, 8, 0, object, ((char[])object).length, 36, 0, rECT2);
            } else {
                n = OS.SetBkMode(l3, 1);
                OS.DrawText(l3, object, ((char[])object).length, rECT2, 36);
                OS.SetBkMode(l3, n);
            }
        }
        int n3 = 24;
        rECT2.left = rECT2.right - 24;
        rECT2.top = this.y + (n2 - 24) / 2;
        rECT2.bottom = rECT2.top + 24;
        if (l2 != 0L) {
            int n4 = this.expanded ? 6 : 7;
            n = this.hover ? 2 : 1;
            OS.DrawThemeBackground(l2, l3, n4, n, rECT2, rECT);
        } else {
            this.drawChevron(l3, rECT2);
        }
        if (bl) {
            OS.SetRect(rECT2, this.x + 1, this.y + 1, this.x + this.width - 2, this.y + n2 - 2);
            OS.DrawFocusRect(l3, rECT2);
        }
        if (this.expanded && !this.parent.isAppThemed()) {
            long l5 = OS.CreatePen(0, 1, OS.GetSysColor(15));
            long l6 = OS.SelectObject(l3, l5);
            int[] nArray = new int[]{this.x, this.y + n2, this.x, this.y + n2 + this.height, this.x + this.width - 1, this.y + n2 + this.height, this.x + this.width - 1, this.y + n2 - 1};
            OS.Polyline(l3, nArray, nArray.length / 2);
            OS.SelectObject(l3, l6);
            OS.DeleteObject(l5);
        }
    }

    @Override
    void destroyWidget() {
        this.parent.destroyItem(this);
        this.releaseHandle();
    }

    public Control getControl() {
        this.checkWidget();
        return this.control;
    }

    public boolean getExpanded() {
        this.checkWidget();
        return this.expanded;
    }

    public int getHeaderHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getHeaderHeightInPixels());
    }

    int getHeaderHeightInPixels() {
        return Math.max(this.parent.getBandHeight(), this.imageHeight);
    }

    public int getHeight() {
        this.checkWidget();
        return DPIUtil.autoScaleDown(this.getHeightInPixels());
    }

    int getHeightInPixels() {
        return this.height;
    }

    public ExpandBar getParent() {
        this.checkWidget();
        return this.parent;
    }

    int getPreferredWidth(long l2, long l3) {
        int n = 36;
        if (this.image != null) {
            n += 6 + this.imageWidth;
        }
        if (this.text.length() > 0) {
            RECT rECT = new RECT();
            char[] cArray = this.text.toCharArray();
            if (l2 != 0L) {
                OS.GetThemeTextExtent(l2, l3, 8, 0, cArray, cArray.length, 32, null, rECT);
            } else {
                OS.DrawText(l3, cArray, cArray.length, rECT, 1024);
            }
            n += rECT.right - rECT.left;
        }
        return n;
    }

    boolean isHover(int n, int n2) {
        int n3 = this.parent.getBandHeight();
        return this.x < n && n < this.x + this.width && this.y < n2 && n2 < this.y + n3;
    }

    void redraw(boolean bl) {
        long l2 = this.parent.handle;
        int n = this.parent.getBandHeight();
        RECT rECT = new RECT();
        int n2 = bl ? this.x : this.x + this.width - n;
        OS.SetRect(rECT, n2, this.y, this.x + this.width, this.y + n);
        OS.InvalidateRect(l2, rECT, true);
        if (this.imageHeight > n) {
            OS.SetRect(rECT, this.x + 6, this.y + n - this.imageHeight, this.x + 6 + this.imageWidth, this.y);
            OS.InvalidateRect(l2, rECT, true);
        }
        if (!this.parent.isAppThemed()) {
            OS.SetRect(rECT, this.x, this.y + n, this.x + this.width, this.y + n + this.height + 1);
            OS.InvalidateRect(l2, rECT, true);
        }
    }

    @Override
    void releaseHandle() {
        super.releaseHandle();
        this.parent = null;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.control = null;
    }

    void setBoundsInPixels(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        this.redraw(true);
        int n5 = this.parent.getBandHeight();
        if (bl) {
            if (this.imageHeight > n5) {
                n2 += this.imageHeight - n5;
            }
            this.x = n;
            this.y = n2;
            this.redraw(true);
        }
        if (bl2) {
            this.width = n3;
            this.height = n4;
            this.redraw(true);
        }
        if (this.control != null && !this.control.isDisposed()) {
            if (!this.parent.isAppThemed()) {
                ++n;
                n3 = Math.max(0, n3 - 2);
                n4 = Math.max(0, n4 - 1);
            }
            if (bl && bl2) {
                this.control.setBoundsInPixels(n, n2 + n5, n3, n4);
            }
            if (bl && !bl2) {
                this.control.setLocationInPixels(n, n2 + n5);
            }
            if (!bl && bl2) {
                this.control.setSizeInPixels(n3, n4);
            }
        }
    }

    public void setControl(Control control) {
        this.checkWidget();
        if (control != null) {
            if (control.isDisposed()) {
                this.error(5);
            }
            if (control.parent != this.parent) {
                this.error(32);
            }
        }
        if ((this.control = control) != null) {
            int n = this.parent.getBandHeight();
            control.setVisible(this.expanded);
            if (!this.parent.isAppThemed()) {
                int n2 = Math.max(0, this.width - 2);
                int n3 = Math.max(0, this.height - 1);
                control.setBoundsInPixels(this.x + 1, this.y + n, n2, n3);
            } else {
                control.setBoundsInPixels(this.x, this.y + n, this.width, this.height);
            }
        }
    }

    public void setExpanded(boolean bl) {
        this.checkWidget();
        this.expanded = bl;
        this.parent.showItem(this);
    }

    public void setHeight(int n) {
        this.checkWidget();
        this.setHeightInPixels(DPIUtil.autoScaleUp(n));
    }

    void setHeightInPixels(int n) {
        if (n < 0) {
            return;
        }
        this.setBoundsInPixels(0, 0, this.width, n, false, true);
        if (this.expanded) {
            this.parent.layoutItems(this.parent.indexOf(this) + 1, true);
        }
    }

    @Override
    public void setImage(Image image) {
        super.setImage(image);
        int n = this.imageHeight;
        if (image != null) {
            Rectangle rectangle = image.getBoundsInPixels();
            this.imageHeight = rectangle.height;
            this.imageWidth = rectangle.width;
        } else {
            boolean bl = false;
            this.imageWidth = 0;
            this.imageHeight = 0;
        }
        if (n != this.imageHeight) {
            this.parent.layoutItems(this.parent.indexOf(this), true);
        } else {
            this.redraw(true);
        }
    }

    @Override
    public void setText(String string) {
        super.setText(string);
        if ((this.state & 0x400000) != 0) {
            this.updateTextDirection(0x6000000);
        }
        this.redraw(true);
    }
}

