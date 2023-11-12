/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

public final class FillLayout
extends Layout {
    public int type = 256;
    public int marginWidth = 0;
    public int marginHeight = 0;
    public int spacing = 0;

    public FillLayout() {
    }

    public FillLayout(int n) {
        this.type = n;
    }

    @Override
    protected Point computeSize(Composite composite, int n, int n2, boolean bl) {
        Control[] controlArray = composite.getChildren();
        int n3 = controlArray.length;
        int n4 = 0;
        int n5 = 0;
        for (Control control : controlArray) {
            int n6 = n;
            int n7 = n2;
            if (n3 > 0) {
                if (this.type == 256 && n != -1) {
                    n6 = Math.max(0, (n - (n3 - 1) * this.spacing) / n3);
                }
                if (this.type == 512 && n2 != -1) {
                    n7 = Math.max(0, (n2 - (n3 - 1) * this.spacing) / n3);
                }
            }
            Point point = this.computeChildSize(control, n6, n7, bl);
            n4 = Math.max(n4, point.x);
            n5 = Math.max(n5, point.y);
        }
        int n8 = 0;
        int n9 = 0;
        if (this.type == 256) {
            n8 = n3 * n4;
            if (n3 != 0) {
                n8 += (n3 - 1) * this.spacing;
            }
            n9 = n5;
        } else {
            n8 = n4;
            n9 = n3 * n5;
            if (n3 != 0) {
                n9 += (n3 - 1) * this.spacing;
            }
        }
        n8 += this.marginWidth * 2;
        n9 += this.marginHeight * 2;
        if (n != -1) {
            n8 = n;
        }
        if (n2 != -1) {
            n9 = n2;
        }
        return new Point(n8, n9);
    }

    Point computeChildSize(Control control, int n, int n2, boolean bl) {
        FillData fillData;
        Object object = control.getLayoutData();
        if (object instanceof FillData) {
            fillData = (FillData)object;
        } else {
            fillData = new FillData();
            if (object == null) {
                control.setLayoutData(fillData);
            }
        }
        Point point = null;
        if (n == -1 && n2 == -1) {
            point = fillData.computeSize(control, n, n2, bl);
        } else {
            int n3;
            int n4;
            if (control instanceof Scrollable) {
                Rectangle rectangle = ((Scrollable)control).computeTrim(0, 0, 0, 0);
                n4 = rectangle.width;
                n3 = rectangle.height;
            } else {
                n3 = n4 = control.getBorderWidth() * 2;
            }
            int n5 = n == -1 ? n : Math.max(0, n - n4);
            int n6 = n2 == -1 ? n2 : Math.max(0, n2 - n3);
            point = fillData.computeSize(control, n5, n6, bl);
        }
        return point;
    }

    @Override
    protected boolean flushCache(Control control) {
        Object object = control.getLayoutData();
        if (object instanceof FillData) {
            ((FillData)object).flushCache();
            return true;
        }
        return false;
    }

    String getName() {
        String string = this.getClass().getName();
        int n = string.lastIndexOf(46);
        if (n == -1) {
            return string;
        }
        return string.substring(n + 1, string.length());
    }

    @Override
    protected void layout(Composite composite, boolean bl) {
        Rectangle rectangle = composite.getClientArea();
        Control[] controlArray = composite.getChildren();
        int n = controlArray.length;
        if (n == 0) {
            return;
        }
        int n2 = rectangle.width - this.marginWidth * 2;
        int n3 = rectangle.height - this.marginHeight * 2;
        if (this.type == 256) {
            int n4 = rectangle.x + this.marginWidth;
            int n5 = (n2 -= (n - 1) * this.spacing) % n;
            int n6 = rectangle.y + this.marginHeight;
            int n7 = n2 / n;
            for (int i = 0; i < n; ++i) {
                Control control = controlArray[i];
                int n8 = n7;
                if (i == 0) {
                    n8 += n5 / 2;
                } else if (i == n - 1) {
                    n8 += (n5 + 1) / 2;
                }
                control.setBounds(n4, n6, n8, n3);
                n4 += n8 + this.spacing;
            }
        } else {
            int n9 = rectangle.x + this.marginWidth;
            int n10 = (n3 -= (n - 1) * this.spacing) / n;
            int n11 = rectangle.y + this.marginHeight;
            int n12 = n3 % n;
            for (int i = 0; i < n; ++i) {
                Control control = controlArray[i];
                int n13 = n10;
                if (i == 0) {
                    n13 += n12 / 2;
                } else if (i == n - 1) {
                    n13 += (n12 + 1) / 2;
                }
                control.setBounds(n9, n11, n2, n13);
                n11 += n13 + this.spacing;
            }
        }
    }

    public String toString() {
        String string = this.getName() + " {";
        string = string + "type=" + (this.type == 512 ? "SWT.VERTICAL" : "SWT.HORIZONTAL");
        if (this.marginWidth != 0) {
            string = string + "marginWidth=" + this.marginWidth;
        }
        if (this.marginHeight != 0) {
            string = string + "marginHeight=" + this.marginHeight;
        }
        if (this.spacing != 0) {
            string = string + "spacing=" + this.spacing;
        }
        string = string.trim();
        return string;
    }
}

