/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;

public class CTabItem
extends Item {
    CTabFolder parent;
    int x;
    int y;
    int width;
    int height = 0;
    Control control;
    String toolTipText;
    String shortenedText;
    int shortenedTextWidth;
    Font font;
    Color foreground;
    Color selectionForeground;
    Image disabledImage;
    Rectangle closeRect = new Rectangle(0, 0, 0, 0);
    int closeImageState = 8;
    int state = 0;
    boolean showClose = false;
    boolean showing = false;

    public CTabItem(CTabFolder cTabFolder, int n) {
        this(cTabFolder, n, cTabFolder.getItemCount());
    }

    public CTabItem(CTabFolder cTabFolder, int n, int n2) {
        super(cTabFolder, n);
        this.showClose = (n & 0x40) != 0;
        cTabFolder.createItem(this, n2);
    }

    @Override
    public void dispose() {
        if (this.isDisposed()) {
            return;
        }
        this.parent.destroyItem(this);
        super.dispose();
        this.parent = null;
        this.control = null;
        this.toolTipText = null;
        this.shortenedText = null;
        this.font = null;
    }

    public Rectangle getBounds() {
        this.parent.runUpdate();
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public Control getControl() {
        this.checkWidget();
        return this.control;
    }

    @Deprecated
    public Image getDisabledImage() {
        this.checkWidget();
        return this.disabledImage;
    }

    public Color getForeground() {
        this.checkWidget();
        if (this.foreground != null) {
            return this.foreground;
        }
        return this.parent.getForeground();
    }

    public Color getSelectionForeground() {
        this.checkWidget();
        if (this.selectionForeground != null) {
            return this.selectionForeground;
        }
        return this.parent.getSelectionForeground();
    }

    public Font getFont() {
        this.checkWidget();
        if (this.font != null) {
            return this.font;
        }
        return this.parent.getFont();
    }

    public CTabFolder getParent() {
        return this.parent;
    }

    public boolean getShowClose() {
        this.checkWidget();
        return this.showClose;
    }

    public String getToolTipText() {
        String string;
        this.checkWidget();
        if (this.toolTipText == null && this.shortenedText != null && !this.shortenedText.equals(string = this.getText())) {
            return string;
        }
        return this.toolTipText;
    }

    public boolean isShowing() {
        this.checkWidget();
        return this.showing;
    }

    public void setControl(Control control) {
        this.checkWidget();
        if (control != null) {
            if (control.isDisposed()) {
                SWT.error(5);
            }
            if (control.getParent() != this.parent) {
                SWT.error(32);
            }
        }
        if (this.control != null && !this.control.isDisposed()) {
            this.control.setVisible(false);
        }
        this.control = control;
        if (this.control != null) {
            int n = this.parent.indexOf(this);
            if (n == this.parent.getSelectionIndex()) {
                this.control.setBounds(this.parent.getClientArea());
                this.control.setVisible(true);
            } else {
                int n2 = this.parent.getSelectionIndex();
                Control control2 = null;
                if (n2 != -1) {
                    control2 = this.parent.getItem((int)n2).control;
                }
                if (this.control != control2) {
                    this.control.setVisible(false);
                }
            }
        }
    }

    @Deprecated
    public void setDisabledImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            SWT.error(5);
        }
        this.disabledImage = image;
    }

    boolean setFocus() {
        return this.control != null && !this.control.isDisposed() && this.control.setFocus();
    }

    public void setFont(Font font) {
        this.checkWidget();
        if (font != null && font.isDisposed()) {
            SWT.error(5);
        }
        if (font == null && this.font == null) {
            return;
        }
        if (font != null && font.equals(this.font)) {
            return;
        }
        this.font = font;
        this.parent.updateFolder(12);
    }

    public void setForeground(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            SWT.error(5);
        }
        if (color == this.foreground) {
            return;
        }
        this.foreground = color;
        this.parent.updateFolder(4);
    }

    public void setSelectionForeground(Color color) {
        this.checkWidget();
        if (color != null && color.isDisposed()) {
            SWT.error(5);
        }
        if (color == this.selectionForeground) {
            return;
        }
        this.selectionForeground = color;
        this.parent.updateFolder(4);
    }

    @Override
    public void setImage(Image image) {
        this.checkWidget();
        if (image != null && image.isDisposed()) {
            SWT.error(5);
        }
        Image image2 = this.getImage();
        if (image == null && image2 == null) {
            return;
        }
        if (image != null && image.equals(image2)) {
            return;
        }
        super.setImage(image);
        this.parent.updateFolder(12);
    }

    public void setShowClose(boolean bl) {
        this.checkWidget();
        if (this.showClose == bl) {
            return;
        }
        this.showClose = bl;
        this.parent.updateFolder(4);
    }

    @Override
    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            SWT.error(4);
        }
        if (string.equals(this.getText())) {
            return;
        }
        super.setText(string);
        this.shortenedText = null;
        this.shortenedTextWidth = 0;
        this.parent.updateFolder(12);
    }

    public void setToolTipText(String string) {
        this.checkWidget();
        this.toolTipText = string;
    }
}

