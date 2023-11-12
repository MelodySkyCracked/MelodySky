/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.widgets.Widget;

public abstract class Item
extends Widget {
    String text = "";
    Image image;
    static final int TEXT_LIMIT = 8192;
    static final String ELLIPSIS = "...";

    public Item(Widget widget, int n) {
        super(widget, n);
    }

    public Item(Widget widget, int n, int n2) {
        this(widget, n);
    }

    @Override
    protected void checkSubclass() {
    }

    public Image getImage() {
        this.checkWidget();
        return this.image;
    }

    @Override
    String getNameText() {
        return this.getText();
    }

    public String getText() {
        this.checkWidget();
        return this.text;
    }

    @Override
    void releaseWidget() {
        super.releaseWidget();
        this.text = null;
        this.image = null;
    }

    public void setImage(Image image) {
        this.checkWidget();
        if (this.image == image) {
            return;
        }
        if (image != null && image.isDisposed()) {
            this.error(5);
        }
        this.image = image;
    }

    public void setText(String string) {
        this.checkWidget();
        if (string == null) {
            this.error(4);
        }
        this.text = string;
        if ((this.state & 0x400000) != 0) {
            this.updateTextDirection(0x6000000);
        }
    }

    boolean updateTextDirection(int n) {
        if (n == 0x6000000) {
            this.state |= 0x400000;
            n = (this.style ^ BidiUtil.resolveTextDirection(this.text)) == 0 ? 0 : Integer.MIN_VALUE;
        } else {
            this.state &= 0xFFBFFFFF;
        }
        if ((this.style & Integer.MIN_VALUE ^ n) != 0) {
            this.style ^= Integer.MIN_VALUE;
            return true;
        }
        return n == 0x6000000;
    }
}

