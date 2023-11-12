/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashFormData;
import org.eclipse.swt.custom.SashFormLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

public class SashForm
extends Composite {
    public int SASH_WIDTH = 3;
    int sashStyle;
    Sash[] sashes = new Sash[0];
    Color background = null;
    Color foreground = null;
    Control[] controls = new Control[0];
    Control maxControl = null;
    Listener sashListener;
    static final int DRAG_MINIMUM = 20;

    public SashForm(Composite composite, int n) {
        super(composite, SashForm.checkStyle(n));
        super.setLayout(new SashFormLayout());
        int n2 = this.sashStyle = (n & 0x200) != 0 ? 256 : 512;
        if ((n & 0x800) != 0) {
            this.sashStyle |= 0x800;
        }
        if ((n & 0x10000) != 0) {
            this.sashStyle |= 0x10000;
        }
        this.sashListener = this::onDragSash;
    }

    static int checkStyle(int n) {
        int n2 = 0x6000800;
        return n & 0x6000800;
    }

    Sash createSash() {
        Sash sash = new Sash(this, this.sashStyle);
        sash.setBackground(this.background);
        sash.setForeground(this.foreground);
        sash.setToolTipText(this.getToolTipText());
        sash.addListener(13, this.sashListener);
        return sash;
    }

    @Override
    public int getOrientation() {
        return (this.sashStyle & 0x200) != 0 ? 256 : 512;
    }

    public int getSashWidth() {
        this.checkWidget();
        return this.SASH_WIDTH;
    }

    @Override
    public int getStyle() {
        int n = super.getStyle();
        n |= this.getOrientation() == 512 ? 512 : 256;
        if ((this.sashStyle & 0x10000) != 0) {
            n |= 0x10000;
        }
        return n;
    }

    public Control getMaximizedControl() {
        return this.maxControl;
    }

    public int[] getWeights() {
        this.checkWidget();
        Control[] controlArray = this.getControls(false);
        int[] nArray = new int[controlArray.length];
        for (int i = 0; i < controlArray.length; ++i) {
            Object object = controlArray[i].getLayoutData();
            nArray[i] = object instanceof SashFormData ? (int)(((SashFormData)object).weight * 1000L >> 16) : 200;
        }
        return nArray;
    }

    Control[] getControls(boolean bl) {
        Control[] controlArray = new Control[]{};
        for (Control control : this.getChildren()) {
            if (control instanceof Sash || bl && !control.getVisible()) continue;
            Control[] controlArray2 = new Control[controlArray.length + 1];
            System.arraycopy(controlArray, 0, controlArray2, 0, controlArray.length);
            controlArray2[controlArray.length] = control;
            controlArray = controlArray2;
        }
        return controlArray;
    }

    void onDragSash(Event event) {
        Sash sash = (Sash)event.widget;
        int n = -1;
        for (int i = 0; i < this.sashes.length; ++i) {
            if (this.sashes[i] != sash) continue;
            n = i;
            break;
        }
        if (n == -1) {
            return;
        }
        Control control = this.controls[n];
        Control control2 = this.controls[n + 1];
        Rectangle rectangle = control.getBounds();
        Rectangle rectangle2 = control2.getBounds();
        Rectangle rectangle3 = sash.getBounds();
        Rectangle rectangle4 = this.getClientArea();
        boolean bl = false;
        if (this.getOrientation() == 256) {
            Object object;
            Object object2;
            bl = rectangle.width < 20 || rectangle2.width < 20;
            int n2 = rectangle2.x + rectangle2.width - rectangle.x;
            int n3 = event.x - rectangle3.x;
            Rectangle rectangle5 = rectangle;
            rectangle5.width += n3;
            Rectangle rectangle6 = rectangle2;
            rectangle6.x += n3;
            Rectangle rectangle7 = rectangle2;
            rectangle7.width -= n3;
            if (rectangle.width < 20) {
                rectangle.width = 20;
                rectangle2.x = rectangle.x + rectangle.width + rectangle3.width;
                rectangle2.width = n2 - rectangle2.x;
                event.x = rectangle.x + rectangle.width;
                event.doit = false;
            }
            if (rectangle2.width < 20) {
                rectangle.width = n2 - 20 - rectangle3.width;
                rectangle2.x = rectangle.x + rectangle.width + rectangle3.width;
                rectangle2.width = 20;
                event.x = rectangle.x + rectangle.width;
                event.doit = false;
            }
            if ((object2 = control.getLayoutData()) == null || !(object2 instanceof SashFormData)) {
                object2 = new SashFormData();
                control.setLayoutData(object2);
            }
            if ((object = control2.getLayoutData()) == null || !(object instanceof SashFormData)) {
                object = new SashFormData();
                control2.setLayoutData(object);
            }
            ((SashFormData)object2).weight = (((long)rectangle.width << 16) + (long)rectangle4.width - 1L) / (long)rectangle4.width;
            ((SashFormData)object).weight = (((long)rectangle2.width << 16) + (long)rectangle4.width - 1L) / (long)rectangle4.width;
        } else {
            Object object;
            Object object3;
            bl = rectangle.height < 20 || rectangle2.height < 20;
            int n4 = rectangle2.y + rectangle2.height - rectangle.y;
            int n5 = event.y - rectangle3.y;
            Rectangle rectangle8 = rectangle;
            rectangle8.height += n5;
            Rectangle rectangle9 = rectangle2;
            rectangle9.y += n5;
            Rectangle rectangle10 = rectangle2;
            rectangle10.height -= n5;
            if (rectangle.height < 20) {
                rectangle.height = 20;
                rectangle2.y = rectangle.y + rectangle.height + rectangle3.height;
                rectangle2.height = n4 - rectangle2.y;
                event.y = rectangle.y + rectangle.height;
                event.doit = false;
            }
            if (rectangle2.height < 20) {
                rectangle.height = n4 - 20 - rectangle3.height;
                rectangle2.y = rectangle.y + rectangle.height + rectangle3.height;
                rectangle2.height = 20;
                event.y = rectangle.y + rectangle.height;
                event.doit = false;
            }
            if ((object3 = control.getLayoutData()) == null || !(object3 instanceof SashFormData)) {
                object3 = new SashFormData();
                control.setLayoutData(object3);
            }
            if ((object = control2.getLayoutData()) == null || !(object instanceof SashFormData)) {
                object = new SashFormData();
                control2.setLayoutData(object);
            }
            ((SashFormData)object3).weight = (((long)rectangle.height << 16) + (long)rectangle4.height - 1L) / (long)rectangle4.height;
            ((SashFormData)object).weight = (((long)rectangle2.height << 16) + (long)rectangle4.height - 1L) / (long)rectangle4.height;
        }
        if (bl || event.doit && event.detail != 1) {
            control.setBounds(rectangle);
            sash.setBounds(event.x, event.y, event.width, event.height);
            control2.setBounds(rectangle2);
        }
    }

    @Override
    public void setOrientation(int n) {
        this.checkWidget();
        if (n == 0x4000000 || n == 0x2000000) {
            super.setOrientation(n);
            return;
        }
        if (this.getOrientation() == n) {
            return;
        }
        if (n != 256 && n != 512) {
            SWT.error(5);
        }
        this.sashStyle &= 0xFFFFFCFF;
        this.sashStyle |= n == 512 ? 256 : 512;
        for (int i = 0; i < this.sashes.length; ++i) {
            this.sashes[i].dispose();
            this.sashes[i] = this.createSash();
        }
        this.layout(false);
    }

    @Override
    public void setBackground(Color color) {
        super.setBackground(color);
        this.background = color;
        for (Sash sash : this.sashes) {
            sash.setBackground(this.background);
        }
    }

    @Override
    public void setForeground(Color color) {
        super.setForeground(color);
        this.foreground = color;
        for (Sash sash : this.sashes) {
            sash.setForeground(this.foreground);
        }
    }

    @Override
    public void setLayout(Layout layout) {
        this.checkWidget();
    }

    public void setMaximizedControl(Control control) {
        this.checkWidget();
        if (control == null) {
            if (this.maxControl != null) {
                this.maxControl = null;
                this.layout(false);
                for (Sash sash : this.sashes) {
                    sash.setVisible(true);
                }
            }
            return;
        }
        for (Sash sash : this.sashes) {
            sash.setVisible(false);
        }
        this.maxControl = control;
        this.layout(false);
    }

    public void setSashWidth(int n) {
        this.checkWidget();
        if (this.SASH_WIDTH == n) {
            return;
        }
        this.SASH_WIDTH = n;
        this.layout(false);
    }

    @Override
    public void setToolTipText(String string) {
        super.setToolTipText(string);
        for (Sash sash : this.sashes) {
            sash.setToolTipText(string);
        }
    }

    public void setWeights(int ... nArray) {
        this.checkWidget();
        Control[] controlArray = this.getControls(false);
        if (nArray == null || nArray.length != controlArray.length) {
            SWT.error(5);
        }
        int n = 0;
        for (int n2 : nArray) {
            if (n2 < 0) {
                SWT.error(5);
            }
            n += n2;
        }
        if (n == 0) {
            SWT.error(5);
        }
        for (int i = 0; i < controlArray.length; ++i) {
            Object object = controlArray[i].getLayoutData();
            if (object == null || !(object instanceof SashFormData)) {
                object = new SashFormData();
                controlArray[i].setLayoutData(object);
            }
            ((SashFormData)object).weight = (((long)nArray[i] << 16) + (long)n - 1L) / (long)n;
        }
        this.layout(false);
    }
}

