/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

public final class FormLayout
extends Layout {
    public int marginWidth = 0;
    public int marginHeight = 0;
    public int marginLeft = 0;
    public int marginTop = 0;
    public int marginRight = 0;
    public int marginBottom = 0;
    public int spacing = 0;

    int computeHeight(Control control, FormData formData, boolean bl) {
        FormAttachment formAttachment = formData.getTopAttachment(control, this.spacing, bl);
        FormAttachment formAttachment2 = formData.getBottomAttachment(control, this.spacing, bl);
        FormAttachment formAttachment3 = formAttachment2.minus(formAttachment);
        if (formAttachment3.numerator == 0) {
            if (formAttachment2.numerator == 0) {
                return formAttachment2.offset;
            }
            if (formAttachment2.numerator == formAttachment2.denominator) {
                return -formAttachment.offset;
            }
            if (formAttachment2.offset <= 0) {
                return -formAttachment.offset * formAttachment.denominator / formAttachment2.numerator;
            }
            int n = formAttachment2.denominator - formAttachment2.numerator;
            return formAttachment2.denominator * formAttachment2.offset / n;
        }
        return formAttachment3.solveY(formData.getHeight(control, bl));
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        Point point = this.layout(composite, false, 0, 0, n, n2, bl);
        if (n != -1) {
            point.x = n;
        }
        if (n2 != -1) {
            point.y = n2;
        }
        return point;
    }

    @Override
    protected boolean flushCache(Control control) {
        Object object = control.getLayoutData();
        if (object != null) {
            ((FormData)object).flushCache();
        }
        return true;
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    int computeWidth(Control control, FormData formData, boolean bl) {
        FormAttachment formAttachment = formData.getLeftAttachment(control, this.spacing, bl);
        FormAttachment formAttachment2 = formData.getRightAttachment(control, this.spacing, bl);
        FormAttachment formAttachment3 = formAttachment2.minus(formAttachment);
        if (formAttachment3.numerator == 0) {
            if (formAttachment2.numerator == 0) {
                return formAttachment2.offset;
            }
            if (formAttachment2.numerator == formAttachment2.denominator) {
                return -formAttachment.offset;
            }
            if (formAttachment2.offset <= 0) {
                return -formAttachment.offset * formAttachment.denominator / formAttachment.numerator;
            }
            int n = formAttachment2.denominator - formAttachment2.numerator;
            return formAttachment2.denominator * formAttachment2.offset / n;
        }
        return formAttachment3.solveY(formData.getWidth(control, bl));
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        Rectangle rectangle = composite.getClientArea();
        int n = rectangle.x + this.marginLeft + this.marginWidth;
        int n2 = rectangle.y + this.marginTop + this.marginHeight;
        int n3 = Math.max(0, rectangle.width - this.marginLeft - 2 * this.marginWidth - this.marginRight);
        int n4 = Math.max(0, rectangle.height - this.marginTop - 2 * this.marginHeight - this.marginBottom);
        this.layout(composite, true, n, n2, n3, n4, bl);
    }

    /*
     * WARNING - void declaration
     */
    Point layout(Composite composite, boolean bl, int n, int n2, int n3, int n4, boolean bl2) {
        void var12_11;
        FormData formData;
        Control control;
        int n5;
        Object object;
        Control[] controlArray;
        for (Control control2 : controlArray = composite.getChildren()) {
            object = (FormData)control2.getLayoutData();
            if (object == null) {
                object = new FormData();
                control2.setLayoutData(object);
            }
            if (bl2) {
                ((FormData)object).flushCache();
            }
            ((FormData)object).cacheBottom = null;
            ((FormData)object).cacheTop = null;
            ((FormData)object).cacheRight = null;
            ((FormData)object).cacheLeft = null;
        }
        Object var12_10 = null;
        Rectangle[] rectangleArray = null;
        int n6 = 0;
        int n7 = 0;
        for (n5 = 0; n5 < controlArray.length; ++n5) {
            control = controlArray[n5];
            formData = (FormData)control.getLayoutData();
            if (n3 != -1) {
                formData.needed = false;
                object = formData.getLeftAttachment(control, this.spacing, bl2);
                FormAttachment formAttachment = formData.getRightAttachment(control, this.spacing, bl2);
                int n8 = ((FormAttachment)object).solveX(n3);
                int n9 = formAttachment.solveX(n3);
                if (formData.height == -1 && !formData.needed) {
                    int n10 = 0;
                    if (control instanceof Scrollable) {
                        Rectangle rectangle = ((Scrollable)control).computeTrim(0, 0, 0, 0);
                        n10 = rectangle.width;
                    } else {
                        n10 = control.getBorderWidth() * 2;
                    }
                    formData.cacheHeight = -1;
                    formData.cacheWidth = -1;
                    int n11 = Math.max(0, n9 - n8 - n10);
                    formData.computeSize(control, n11, formData.height, bl2);
                    if (var12_11 == null) {
                        boolean[] blArray = new boolean[controlArray.length];
                    }
                    var12_11[n5] = true;
                }
                n6 = Math.max(n9, n6);
                if (!bl) continue;
                if (rectangleArray == null) {
                    rectangleArray = new Rectangle[controlArray.length];
                }
                rectangleArray[n5] = new Rectangle(0, 0, 0, 0);
                rectangleArray[n5].x = n + n8;
                rectangleArray[n5].width = n9 - n8;
                continue;
            }
            n6 = Math.max(this.computeWidth(control, formData, bl2), n6);
        }
        for (n5 = 0; n5 < controlArray.length; ++n5) {
            control = controlArray[n5];
            formData = (FormData)control.getLayoutData();
            if (n4 != -1) {
                int n12 = formData.getTopAttachment(control, this.spacing, bl2).solveX(n4);
                int n13 = formData.getBottomAttachment(control, this.spacing, bl2).solveX(n4);
                n7 = Math.max(n13, n7);
                if (!bl) continue;
                rectangleArray[n5].y = n2 + n12;
                rectangleArray[n5].height = n13 - n12;
                continue;
            }
            n7 = Math.max(this.computeHeight(control, formData, bl2), n7);
        }
        for (n5 = 0; n5 < controlArray.length; ++n5) {
            control = controlArray[n5];
            formData = (FormData)control.getLayoutData();
            if (var12_11 != null && var12_11[n5] != false) {
                formData.cacheHeight = -1;
                formData.cacheWidth = -1;
            }
            formData.cacheBottom = null;
            formData.cacheTop = null;
            formData.cacheRight = null;
            formData.cacheLeft = null;
        }
        if (bl) {
            for (n5 = 0; n5 < controlArray.length; ++n5) {
                controlArray[n5].setBounds((Rectangle)rectangleArray[n5]);
            }
        }
        return new Point(n6 += this.marginLeft + this.marginWidth * 2 + this.marginRight, n7 += this.marginTop + this.marginHeight * 2 + this.marginBottom);
    }

    public String toString() {
        String string = this.getName() + " {";
        if (this.marginWidth != 0) {
            string = string + "marginWidth=" + this.marginWidth + " ";
        }
        if (this.marginHeight != 0) {
            string = string + "marginHeight=" + this.marginHeight + " ";
        }
        if (this.marginLeft != 0) {
            string = string + "marginLeft=" + this.marginLeft + " ";
        }
        if (this.marginRight != 0) {
            string = string + "marginRight=" + this.marginRight + " ";
        }
        if (this.marginTop != 0) {
            string = string + "marginTop=" + this.marginTop + " ";
        }
        if (this.marginBottom != 0) {
            string = string + "marginBottom=" + this.marginBottom + " ";
        }
        if (this.spacing != 0) {
            string = string + "spacing=" + this.spacing + " ";
        }
        string = string.trim();
        string = string + "}";
        return string;
    }
}

